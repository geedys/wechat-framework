package com.moonshy.wechatframework.api.core.req.model.dataCube;

import com.moonshy.wechatframework.core.common.BaseData;

/**
 * 参数类--获取消息发送分布周数据
 * 
 * @author moonshy
 *
 *         2015年1月27日
 */
public class DataCubeStreamMsgDistWeekParam extends BaseData {

	private static final long serialVersionUID = -8226838953043697194L;

	// 开始时间
	private String begin_date = null;

	// 结束时间
	private String end_date = null;

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

}
