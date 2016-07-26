package com.moonshy.wechatframework.web.service;

import com.moonshy.wechatframework.core.common.BaseParams;
import com.moonshy.wechatframework.core.common.WechatParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WechatWebService {

    /**
     * 批量验证微信公众号URL接入
     *
     * @param params        接收到的微信服务器的请求参数
     * @param baseParamList 微信公众号列表
     * @return 验证通过为true
     */
    boolean check(WechatParam params, List<BaseParams> baseParamList);

    /**
     * 批量处理微信公众号的消息，得到返回结果
     *
     * @param request       web 请求对象
     * @param params        接收到的微信服务器的请求参数
     * @param baseParamList 微信公众号列表
     * @return
     */
    String getResponse(HttpServletRequest request, WechatParam params, List<BaseParams> baseParamList) throws Exception;

}
