package com.moonshy.wechatframework.api.token.impl;

import com.moonshy.wechatframework.api.token.AbstractTokenProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author moonshy Created on 2016-07-26 15:17
 */
public class MongodbTokenProxy extends AbstractTokenProxy {
    @Autowired


    @Override
    public String accessToken(String appid, String secret) {
        return super.accessToken(appid, secret);
    }

    @Override
    public String jsTiket(String appid, String secret) {
        return super.jsTiket(appid, secret);
    }
}
