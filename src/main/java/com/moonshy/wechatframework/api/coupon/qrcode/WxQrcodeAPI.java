package com.moonshy.wechatframework.api.coupon.qrcode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moonshy.wechatframework.api.coupon.qrcode.model.Getticket;
import com.moonshy.wechatframework.api.coupon.qrcode.model.GetticketRtn;
import com.moonshy.wechatframework.api.coupon.qrcode.model.QrcodeRtnInfo;
import com.moonshy.wechatframework.api.coupon.qrcode.model.QrcodeInfo;

/**
 * 微信卡券 - 卡券投放
 * 
 * @author moonshy
 *
 */
public class WxQrcodeAPI {

	/**
	 * 创建二维码
	 */
	public static QrcodeRtnInfo doAddQrcode(String accesstoken, QrcodeInfo qrcodeInfo) {
		if (accesstoken != null) {
			qrcodeInfo.setAccess_token(accesstoken);
			String result = JSONObject.toJSONString(qrcodeInfo);
			QrcodeRtnInfo qrcodeRtnInfo = JSON.parseObject(result, QrcodeRtnInfo.class);
			return qrcodeRtnInfo;
		}
		return null;
	}

	/**
	 * 获取api_ticket
	 */
	public static GetticketRtn doGetticket(String accesstoken) {
		if (accesstoken != null) {
			Getticket gk = new Getticket();
			gk.setAccess_token(accesstoken);
			String result = JSONObject.toJSONString(gk);
			GetticketRtn getticketRtn = JSON.parseObject(result, GetticketRtn.class);
			return getticketRtn;
		}
		return null;
	}

}
