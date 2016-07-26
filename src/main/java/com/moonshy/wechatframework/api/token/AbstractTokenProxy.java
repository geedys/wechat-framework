package com.moonshy.wechatframework.api.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token管理抽象类，
 *
 * @author moonshy
 */
public class AbstractTokenProxy implements TokenProxy {
    private String appid;
    private String appSecret;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    protected static final String ACCESS_TOKEN_PREFIX = "access_token";
    protected static final String JS_TIKET_PREFIX = "js_tiket";

    /**
     * 默认token过期时间
     */
    protected static final Long EXPIRE_TIME = 6000 * 1000L;

    @Override
    public String accessToken(String appid, String secret) {
        return null;
    }

    @Override
    public String jsTiket(String appid, String secret) {
        return null;
    }

    @Override
    public String accessToken() {
        return accessToken(appid, appSecret);
    }

    @Override
    public String jsTiket() {
        return jsTiket(appid, appSecret);
    }
}
