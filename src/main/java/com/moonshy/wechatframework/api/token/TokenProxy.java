package com.moonshy.wechatframework.api.token;

/**
 * token管理接口
 *
 * @author moonshy
 */
public interface TokenProxy {

    /**
     * 默认token过期时间
     */
    Long EXPIRE_TIME = 100 * 60 * 1000L;//100分钟

    /**
     * 根据APPID，SECRET获取token，支持多公众号的情况
     */
    String accessToken(String appid, String secret);

    /**
     * 根据APPID，SECRET获取token，支持多公众号的情况
     *
     * @param appid
     * @param secret
     * @return
     */
    String jsTicket(String appid, String secret);

    /**
     * 单公众号时获取token的方便形式
     *
     * @return
     */
    String accessToken();

    /**
     * 单公众号时获取token的方便形式
     *
     * @return
     */
    String jsTicket();
}
