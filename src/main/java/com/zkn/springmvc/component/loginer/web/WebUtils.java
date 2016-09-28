package com.zkn.springmvc.component.loginer.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.util.Constants;
import com.zkn.springmvc.util.EncryptDecryptUtil;
import com.zkn.springmvc.util.JsonBinder;

/**
 * Web工具类
 * @author MR.SU
 *
 */
public class WebUtils {
	
	/**
	 * 获取登录路径
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getLoginUrl(HttpServletRequest request) throws UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder()
		.append(request.getRequestURL().toString())
		.append("?")
		.append(request.getQueryString());

		String returnUrl = URLEncoder.encode(sb.toString() , "UTF-8");
		
		return Constants.LOGIN_URL + "?ReturnURL=" + returnUrl;
	}
	
	/**
	 * 获取登录的用户名
	 * @param request
	 * @return
	 */
	public static String getLoginName(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Constants.LOGIN_SESSION_NAME);
		user = user == null ? getCookieValue(request) : user;
		
		return user == null ? "" : user.getUserName();
	}
	
	/**
	 * 获取协议路径
	 * @param request
	 * @return
	 */
	public static String getSignUrl(HttpServletRequest request){
		return new StringBuilder()
			.append(request.getContextPath())
			.append(Constants.README_AND_SIGNED).toString();
	}
	
	/**
	 * 获取Cookie登录信息
	 * @param request
	 * @return
	 */
	public static User getCookieValue(HttpServletRequest request){
		User cookieUser = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(Constants.LOGIN_COOKIE_NAME.equals(cookie.getName())){
					// 解密 cookie 获取json，把解密数据存放到session
					String json = EncryptDecryptUtil.decrypt(cookie.getValue());
					cookieUser = JsonBinder.buildNormalBinder().fromJson(json, User.class);
				}
			}
		}
		
		return cookieUser;
	}
	
	/**
	 * 构建JSON响应结果
	 * @param response
	 * @param message
	 * @param url
	 * @throws IOException
	 */
	public static void buildJsonResult(HttpServletResponse response, String message, String url) throws IOException{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{");
		out.print("message:");
		out.print(message);
		out.print(", url:");
		out.print(url);
		out.print("}");
		out.flush();
		out.close();
	}
}
