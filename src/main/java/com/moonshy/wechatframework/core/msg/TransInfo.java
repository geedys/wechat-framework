package com.moonshy.wechatframework.core.msg;

import javax.xml.bind.annotation.XmlElement;

/**
 * 指定客服
 * @author moonshy
 * @version 2015-7-7
 */
public class TransInfo {
	private String KfAccount;//指定会话接入的客服账号
	
	public TransInfo() {
		super();
	}

	public TransInfo(String kfAccount) {
		super();
		KfAccount = kfAccount;
	}

	@XmlElement(name="KfAccount")
	public String getKfAccount() {
		return KfAccount;
	}

	public void setKfAccount(String kfAccount) {
		KfAccount = kfAccount;
	}
	
}
