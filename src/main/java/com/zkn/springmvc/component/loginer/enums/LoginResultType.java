package com.zkn.springmvc.component.loginer.enums;

/**
 * 登录结果显示方式
 * @author MR.SU
 * 
 */
public enum LoginResultType {
	/** 页面刷新 */
	PAGE,

	/** json数据 */
	JSON,
	
	/** 需要登录，但不需要返回什么值 */
	NONE
}
