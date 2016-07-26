package com.moonshy.wechatframework.web.service;

import com.moonshy.wechatframework.core.common.BaseParams;
import com.moonshy.wechatframework.core.common.WechatParam;
import com.moonshy.wechatframework.core.support.WechatSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractWechatService implements WechatWebService {
    protected WechatSupport wechatService;

    /**
     * 微信消息处理器,用户可以自己实现WechatSupport，并在实现类加上注解@Service("wechatService")即可自定义消息处理
     */
    public WechatSupport getWechatService() {
        return wechatService;
    }

    public void setWechatService(WechatSupport wechatService) {
        this.wechatService = wechatService;
    }

    @Override
    public abstract boolean check(WechatParam params, List<BaseParams> baseParamList);

    @Override
    public abstract String getResponse(HttpServletRequest request, WechatParam params, List<BaseParams> baseParamList) throws Exception;

}
