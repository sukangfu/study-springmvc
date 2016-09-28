package com.zkn.springmvc.component.loginer.enums;

/**
 * 登录跳转状态
 * @author mr.su
 *
 */
public enum LoginedType {
	/** 登录信息在Session中 */
	SESSION,
	/** 登录信息在Cookie中 */
	COOKIE,
	/** 未登录 */
	LOGIN,
	/** 未签协议 */
	SIGN,
	/** 其他 */
	OTHER
}
