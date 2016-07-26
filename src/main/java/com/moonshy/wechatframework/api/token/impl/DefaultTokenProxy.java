package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.jsapi.WxJsSDKAPI;
import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import com.moonshy.wechatframework.api.token.WxAccessTokenAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
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
    public String accessToken(String appid, String secret) {
        // 获取access_token数据集合
        Map<Object, Object> map = tokenMap.get(ACCESS_TOKEN_PREFIX + appid);
        if (map != null) {
            if (!checkExpire(appid, ACCESS_TOKEN_PREFIX)) {
                // token未过期
                logger.info("access_token未过期");
                return getAccessTokenFromMap(appid, ACCESS_TOKEN_PREFIX);
            } else {
                // token过期，清除token
                logger.info("access_token过期，清除token");
                cleanToken(appid, ACCESS_TOKEN_PREFIX);
                // 刷新access_token
                logger.info("刷新access_token");
                return refreshAccessToken(appid, secret);
            }
        } else {
            // 不存在access_token数据集合，刷新access_token
            logger.info("不存在access_token数据集合，刷新access_token");
            return refreshAccessToken(appid, secret);
        }
    }

    @Override
    public String jsTiket(String appid, String secret) {
        // 获取js_tiket数据集合
        Map<Object, Object> map = tokenMap.get(JS_TIKET_PREFIX + appid);
        if (map != null) {
            if (!checkExpire(appid, JS_TIKET_PREFIX)) {
                // js_tiket未过期
                logger.info("js_tiket未过期");
                return getAccessTokenFromMap(appid, JS_TIKET_PREFIX);
            } else {
                // js_tiket过期，清除js_tiket
                logger.info("js_tiket过期，清除js_tiket");
                cleanToken(appid, JS_TIKET_PREFIX);
                // 刷新js_tiket,并返回js_tiket
                logger.info("刷新js_tiket,并返回js_tiket");
                return refreshJsTiket(appid, secret);
            }
        } else {
            // 不存在js_tiket数据集合，刷新js_tiket
            logger.info("不存在js_tiket数据集合，刷新js_tiket");
            return refreshJsTiket(appid, secret);
        }
    }

    /**
     * 从map获取token,该方法给默认token管理类使用
     *
     * @param appid
     * @return
     */
    protected String getAccessTokenFromMap(String appid, String prefix) {
        String token = null;
        // 获取token数据集合
        Map<Object, Object> map = tokenMap.get(prefix + appid);
        if (map != null) {
            // 获取token值
            token = (String) map.get(prefix);
        }
        return token;
    }

    /**
     * 刷新access_token
     *
     * @param appid
     * @param secret
     * @return
     */
    protected String refreshAccessToken(String appid, String secret) {
        Map<Object, Object> map = new HashMap<>();
        String access_token = WxAccessTokenAPI.getAccess_token(appid, secret);
        logger.info("请求access_token=" + access_token);
        return getToken(appid, map, access_token, ACCESS_TOKEN_PREFIX);
    }

    /**
     * 刷新js_tiket
     *
     * @param appid
     * @param secret
     * @return
     */
    protected String refreshJsTiket(String appid, String secret) {
        Map<Object, Object> map = new HashMap<Object, Object>();

        String accessToken = getAccessTokenFromMap(appid, ACCESS_TOKEN_PREFIX);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = refreshAccessToken(appid, secret);
        }
        String js_tiket = WxJsSDKAPI.getJs_tiket(accessToken);
        logger.info("请求js_tiket=" + js_tiket);
        return getToken(appid, map, js_tiket, JS_TIKET_PREFIX);
    }

    private String getToken(String appid, Map<Object, Object> map, String token, String prefix) {
        if (StringUtils.isNotEmpty(token)) {
            map.put(prefix, token);
            map.put(EXPIRE_TIME, new Date().getTime());
            tokenMap.put(prefix + appid, map);
            return getAccessTokenFromMap(appid, prefix);
        }
        return null;
    }

    /**
     * 检测map中token是否过期,该方法给默认token管理类使用
     *
     * @param appid
     * @param prefix
     * @return true过期，false未过期
     */
    protected boolean checkExpire(String appid, String prefix) {
        // 获取token数据集合
        Map<Object, Object> map = tokenMap.get(prefix + appid);
        if (map != null) {
            // 获取token存储时间
            Long expireTime = (Long) map.get(EXPIRE_TIME);
            if ((expireTime + EXPIRE_TIME) < new Date().getTime()) {
                return true;
            }
        }
        return false;
    }

    protected void cleanToken(String appid, String prefix) {
        tokenMap.remove(prefix + appid);
    }


}
