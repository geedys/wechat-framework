package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 默认token管理类
 *
 * @author moonshy
 */
public class RedisTokenProxy extends AbstractTokenProxy {
    private static Logger logger = LoggerFactory.getLogger(RedisTokenProxy.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    ValueOperations<String, String> operations = redisTemplate.opsForValue();

    @Override
    public String saveAccessToken(String appid, String appsecret) {
        String accessToken = WxAccessTokenAPI.getAccess_token(appid, appid);
        operations.set(tokenKey(), accessToken, EXPIRE_TIME, TimeUnit.MILLISECONDS);
        logger.info("成功保存accessToken:" + accessToken);
        return accessToken;
    }

    @Override
    public String saveJsTicket(String accessToken) {
        String jsTicket = WxJsSDKAPI.getJsTicket(accessToken);
        operations.set(jsTicketKey(), jsTicket, EXPIRE_TIME, TimeUnit.MILLISECONDS);
        logger.info("成功保存jsTiket:" + jsTicket);
        return jsTicket;
    }

    @Override
    public String getResultValue(String key) {
        return operations.get(key);
    }
}
