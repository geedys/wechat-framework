package com.moonshy.wechatframework.api.coupon.qrcode.model;

import com.moonshy.wechatframework.core.common.BaseData;

public class GetticketRtn extends BaseData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5459130478589042440L;
	// api_ticket，签名中所需凭证
	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
