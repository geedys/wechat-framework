/**
 * 
 */
package com.moonshy.wechatframework.core.requestMsg;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author moonshy
 * @date   2015年1月6日
 */
public class Item {
	private String PicMd5Sum;

	@XmlElement(name="PicMd5Sum")
	public String getPicMd5Sum() {
		return PicMd5Sum;
	}
	public void setPicMd5Sum(String picMd5Sum) {
		PicMd5Sum = picMd5Sum;
	}
	
}
