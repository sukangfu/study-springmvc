package com.zkn.springmvc.util;

public class Constants {

	public static final String KEY = "zkn.su"; 
	public static final String CIPHER = "DES/CBC/PKCS5Padding"; 
	
	//login
	public static final String LOGIN_COOKIE_NAME = "zkn_user";
	public static final String LOGIN_SESSION_NAME = "zkn_user"; 
	
	//url
	public static final String COPYRIGHT = "http://www.zkn.com"; 
	public static final String REDIRECT_INDEX = "redirect:/index.jsp"; 
	public static final String README_AND_SIGNED = "/declaration/show.htm"; 
	public static final String LOGIN_URL = getValue("zkn.login.url");//登陆地址
	
	//message
	public static final String MESSAGE_UNLOGINED = "您还没有登录，请先登录哦";
	public static final String MESSAGE_UNSIGNED = "您还没签平台使用协议，请先签协议";
	public static final String MESSAGE_UNNORMAL = "用户状态不正常，请联系在线客服";
	
	private static String getValue(String key){
		return PropertiesUtils.getProperty(key);
	}
}
