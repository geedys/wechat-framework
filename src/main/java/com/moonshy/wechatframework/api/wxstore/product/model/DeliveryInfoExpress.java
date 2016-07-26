package com.moonshy.wechatframework.api.wxstore.product.model;

import com.moonshy.wechatframework.core.common.BaseData;

public class DeliveryInfoExpress extends BaseData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3580418373685021186L;
	// 快递ID
	private Integer id;
	// 运费
	private Integer price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
