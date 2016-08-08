package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认token管理类
 *
 * @author moonshy
 */
public class DefaultTokenProxy extends AbstractTokenProxy {

    private static Logger logger = LoggerFactory.getLogger(DefaultTokenProxy.class);

    /**
     * 默认token容器
     */
    protected ConcurrentHashMap<String, Map<Object, Object>> tokenMap = new ConcurrentHashMap<>();

    @Override
    protected String saveAccessToken(String appid, String appsecret) {
        String access_token = WxAccessTokenAPI.getAccess_token(appid, appsecret);
        logger.info("请求access_token=" + access_token);
        String key = tokenKey();
        Map<Object, Object> map = tokenMap.get(key);
        if (map == null) {
            map = new HashMap<>();
            tokenMap.put(key, map);
        }
        map.put(ACCESS_TOKEN_PREFIX, access_token);
        map.put("EXPIRE_TIME", System.currentTimeMillis() + EXPIRE_TIME);
        return access_token;
    }

    @Override
    public String saveJsTicket(String accessToken) {
        String jsTicket = WxJsSDKAPI.getJsTicket(accessToken);
        logger.info("请求js_ticket=" + jsTicket);
        String key = jsTicketKey();
        Map<Object, Object> map = tokenMap.get(key);
        if (map == null) {
            map = new HashMap<>();
            tokenMap.put(key, map);
        }
        map.put(JS_TICKET_PREFIX, jsTicket);
        map.put("EXPIRE_TIME", System.currentTimeMillis() + EXPIRE_TIME);
        return jsTicket;
    }

    @Override
    public String getResultValue(String key) {
        Map<Object, Object> map = tokenMap.get(key);
        if (map != null) {
            // 获取token存储时间
            Long expireTime = (Long) map.get("EXPIRE_TIME");
            if (System.currentTimeMillis() <= expireTime) {
                return map.get(key.startsWith(ACCESS_TOKEN_PREFIX) ? ACCESS_TOKEN_PREFIX : JS_TICKET_PREFIX).toString();
            }
        }
        return null;
    }
}
