package com.moonshy.wechatframework.api.wxstore.deliveryMoney.model;

import com.moonshy.wechatframework.core.common.BaseData;

import java.util.List;

public class DeliveryMoney extends BaseData {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9070854698979040754L;
	// 邮费模板名称
	private String Name;
	// 支付方式(0-买家承担运费, 1-卖家承担运费)
	private Integer Assumer;
	// 计费单位(0-按件计费, 1-按重量计费, 2-按体积计费，目前只支持按件计费，默认为0)
	private Integer Valuation;
	// 具体运费计算
	private List<DeliveryMoneyTopFreeInfo> TopFee;
	// 邮费模板ID
	private Integer template_id;

	private DeliveryMoney template_info;
	
	private List<DeliveryMoney> templates_info;

	public List<DeliveryMoney> getTemplates_info() {
		return templates_info;
	}

	public void setTemplates_info(List<DeliveryMoney> templates_info) {
		this.templates_info = templates_info;
	}

	public DeliveryMoney getTemplate_info() {
		return template_info;
	}

	public void setTemplate_info(DeliveryMoney template_info) {
		this.template_info = template_info;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getAssumer() {
		return Assumer;
	}

	public void setAssumer(Integer assumer) {
		Assumer = assumer;
	}

	public Integer getValuation() {
		return Valuation;
	}

	public void setValuation(Integer valuation) {
		Valuation = valuation;
	}

	public List<DeliveryMoneyTopFreeInfo> getTopFee() {
		return TopFee;
	}

	public void setTopFee(List<DeliveryMoneyTopFreeInfo> topFee) {
		TopFee = topFee;
	}

	public Integer getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(Integer template_id) {
		this.template_id = template_id;
	}

}
