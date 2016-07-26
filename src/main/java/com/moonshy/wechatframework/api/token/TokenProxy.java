package com.moonshy.wechatframework.api.token;

/**
 * token管理接口
 *
 * @author moonshy
 */
public interface TokenProxy {

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
    String jsTiket(String appid, String secret);

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
    String jsTiket();
}
