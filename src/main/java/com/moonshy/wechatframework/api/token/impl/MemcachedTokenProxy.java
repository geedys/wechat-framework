package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author moonshy Created on 2016-07-26 11:38
 */
public class MemcachedTokenProxy extends AbstractTokenProxy {
    private static Logger logger = LoggerFactory.getLogger(MemcachedTokenProxy.class);
    @Autowired
    MemCachedClient memCachedClient;
    @Autowired
    SockIOPool memCachedPool;

    @Override
    public String saveAccessToken(String appid, String appsecret) {
        String accessToken = WxAccessTokenAPI.getAccess_token(appid, appsecret);
        //定时刷新时，一定时间如果没有操作本方法，memCachedClient会断开连接，抛出异常。将连接池重新初始化一下，可解决该问题
        memCachedPool.initialize();
        memCachedClient.set(tokenKey(), accessToken, new Date(EXPIRE_TIME));
        logger.info("成功保存accessToken:" + accessToken);
        return accessToken;
    }

    @Override
    public String saveJsTicket(String accessToken) {
        String jsTicket = WxJsSDKAPI.getJsTicket(accessToken);
        memCachedClient.set(jsTicketKey(), jsTicket, new Date(EXPIRE_TIME));
        logger.info("成功保存jsTiket:" + jsTicket);
        return jsTicket;
    }

    @Override
    public String getResultValue(String key) {
        Object result = memCachedClient.get(key, null, true);
        return result == null ? null : result.toString();
    }
}
