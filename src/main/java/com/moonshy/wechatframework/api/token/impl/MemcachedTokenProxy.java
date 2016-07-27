package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author moonshy Created on 2016-07-26 11:38
 */
public class MemcachedTokenProxy extends AbstractTokenProxy {
    private static Logger logger = LoggerFactory.getLogger(MemcachedTokenProxy.class);
    @Autowired
    MemCachedClient memCachedClient;

    @Override
    public String accessToken(String appid, String secret) {
        String key = ACCESS_TOKEN_PREFIX + appid;
        synchronized (this) {
            Object token = memCachedClient.get(key, null, true);
            if (token == null || StringUtils.isEmpty(token)) {
                logger.info("accessToken 不存在");
                String access_token = WxAccessTokenAPI.getAccess_token(appid, secret);
                if (!StringUtils.isEmpty(access_token)) {
                    logger.info("获取到新的AccessToken:" + access_token);
                    memCachedClient.set(key, access_token, new Date(EXPIRE_TIME));
                    return access_token;
                }
            }
            logger.info("返回accessToken:" + token);
            return (String) token;
        }
    }

    @Override
    public String jsTiket(String appid, String secret) {
        String key = JS_TIKET_PREFIX + appid;
        synchronized (this) {
            Object token = memCachedClient.get(key);
            if (token == null || StringUtils.isEmpty(token)) {
                logger.info("jstiket 不存在");
                String access_token = WxJsSDKAPI.getJs_tiket(accessToken(appid, secret));
                if (!StringUtils.isEmpty(access_token)) {
                    logger.info("获取到新的jstiket:" + access_token);
                    memCachedClient.set(key, access_token, new Date(EXPIRE_TIME));
                    return access_token;
                }
            }
            logger.info("返回jstiket:" + token);
            return (String) token;
        }
    }
}
