package com.moonshy.wechatframework.api.wxstore.product.model;

import com.moonshy.wechatframework.core.common.BaseData;

public class SkuValue extends BaseData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2386426608206861716L;
	// vid
	private String id;
	// vid名称
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
