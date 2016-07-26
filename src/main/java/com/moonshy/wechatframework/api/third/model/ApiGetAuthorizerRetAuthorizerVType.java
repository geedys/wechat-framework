package com.moonshy.wechatframework.api.third.model;

import com.moonshy.wechatframework.core.common.BaseData;

/**
 * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
 */
public class ApiGetAuthorizerRetAuthorizerVType extends BaseData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3771155739921645546L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
