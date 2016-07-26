package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public String accessToken(String appid, String secret) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String token = operations.get(ACCESS_TOKEN_PREFIX + appid);
        if (StringUtils.isNotEmpty(token)) {
            logger.info("accessToken redis存在,accessToken=" + token);
            return token;
        } else {
            String access_token = WxAccessTokenAPI.getAccess_token(appid, secret);
            logger.info("accessToken redis未获取到,刷新accessToken=" + access_token);
            if (StringUtils.isNotEmpty(access_token)) {
                operations.set(ACCESS_TOKEN_PREFIX + appid, access_token, EXPIRE_TIME, TimeUnit.MILLISECONDS);
                logger.info("accessToken 存入redis");
                return access_token;
            }
        }
        return null;
    }

    @Override
    public String jsTiket(String appid, String secret) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String token = operations.get(JS_TIKET_PREFIX + appid);
        if (StringUtils.isNotEmpty(token)) {
            logger.info("js_tiket redis存在,js_tiket=" + token);
            return token;
        } else {
            String jsTiket = WxJsSDKAPI.getJs_tiket(accessToken(appid, secret));
            logger.info("jsTiket redis未获取到,刷新js_tiket=" + jsTiket);
            if (StringUtils.isNotEmpty(jsTiket)) {
                operations.set(JS_TIKET_PREFIX + appid, jsTiket, EXPIRE_TIME, TimeUnit.MILLISECONDS);
                logger.info("jsTiket 存入redis");
                return jsTiket;
            }
        }
        return null;
    }
}
