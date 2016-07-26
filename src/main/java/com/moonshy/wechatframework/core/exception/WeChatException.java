package com.moonshy.wechatframework.core.exception;

/**
 * 异常处理
 * @author moonshy
 * @version 2015-7-4
 */
public class WeChatException extends Exception {
	private static final long serialVersionUID = 1L;
	public WeChatException(String msg){
		super(msg);
	}
}
