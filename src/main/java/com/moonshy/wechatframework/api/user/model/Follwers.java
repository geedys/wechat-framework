package com.moonshy.wechatframework.api.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.moonshy.wechatframework.core.common.BaseData;

/**
 * 关注者集合
 * @author moonshy
 * @version 2015-7-5
 */
public class Follwers extends BaseData {
	private int total;//	关注该公众账号的总用户数
	private int count;//	拉取的OPENID个数，最大值为10000
	private Data data;//	列表数据，OPENID的列表
	private String nextOpenid;//拉取列表的后一个用户的OPENID
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	@JSONField(name="next_openid")
	public String getNextOpenid() {
		return nextOpenid;
	}
	@JSONField(name="next_openid")
	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}
	
}

