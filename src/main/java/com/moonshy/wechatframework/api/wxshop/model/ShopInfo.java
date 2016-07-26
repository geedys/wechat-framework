package com.moonshy.wechatframework.api.wxshop.model;

import com.moonshy.wechatframework.core.common.BaseData;

public class ShopInfo extends BaseData {
	private static final long serialVersionUID = 352424260995472282L;
	private String category_list;

	public String getCategory_list() {
		return category_list;
	}

	public void setCategory_list(String category_list) {
		this.category_list = category_list;
	}

}
