package com.moonshy.wechatframework.core.support;

import com.moonshy.wechatframework.core.requestMsg.WechatRequest;

/**
 * Created by moonshy on 2016/7/8.
 */
public interface IDispatcher {

    /**
     * 请求消息体排重处理接口，由子类实现
     * @param wechatRequest
     * @throws Exception
     */
    void excludeRequest(WechatRequest wechatRequest)  throws Exception;
}
