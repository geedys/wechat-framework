package com.moonshy.wechatframework.web.service.impl;

import com.moonshy.wechatframework.core.common.BaseParams;
import com.moonshy.wechatframework.core.common.WechatCommonConst;
import com.moonshy.wechatframework.core.common.WechatParam;
import com.moonshy.wechatframework.core.util.SignatureUtil;
import com.moonshy.wechatframework.core.util.XmlHelper;
import com.moonshy.wechatframework.core.mp.WXBizMsgCrypt;
import com.moonshy.wechatframework.core.requestMsg.WechatRequest;
import com.moonshy.wechatframework.web.service.AbstractWechatService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 默认公众号消息批量处理工具
 *
 * @author moonshy
 */
public class WechatServiceTemplate extends AbstractWechatService {

    @Override
    public boolean check(WechatParam params, List<BaseParams> baseParamList) {
        for (BaseParams baseParams : baseParamList) {
            // 逐一验证公众号URL
            boolean result = SignatureUtil.checkSignature(params, baseParams.getToken());
            // 一旦通过即刻返回验证通过
            if (result) {
                return result;
            }
        }
        return false;
    }

    @Override
    public String getResponse(HttpServletRequest request, WechatParam wechatParam, List<BaseParams> baseParamList) throws Exception {
        String msgXml = XmlHelper.parseXml(request);
        WechatRequest wr = XmlHelper.toObj(msgXml);
        if(StringUtils.isEmpty(wr.getEncrypt())) {
            String result = wechatService.service(wr);
            // 一旦消息处理正确，即刻返回
            if (!WechatCommonConst.SERVICE_ERROR.getValue().equals(result)) {
                return result;
            }
        }else{
            for (BaseParams bp : baseParamList) {
                if (StringUtils.isNotEmpty(bp.getEncodingAESKey())) {
                    WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(bp.getToken(), bp.getEncodingAESKey(), bp.getAppId());
                    String decrypt = wxBizMsgCrypt.decryptMsg(wechatParam.getMsg_signature(), wechatParam.getTimestamp(), wechatParam.getNonce(), msgXml);
                    Document document = DocumentHelper.parseText(decrypt);
                    wr = XmlHelper.toObj(document.asXML());
                    if (wr.getToUserName().equals(bp.getGhId())) {
                        //处理回传消息。
                        String result = wechatService.service(wr);
                        // 一旦消息处理正确，即刻返回
                        if (!WechatCommonConst.SERVICE_ERROR.getValue().equals(result)) {
                            // 加密
                            if (WechatCommonConst.AES.getValue().equals(wechatParam.getEncrypt_type())) {
                                return wxBizMsgCrypt.encryptMsg(result, wechatParam.getTimestamp(), wechatParam.getNonce());
                            }
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
}
