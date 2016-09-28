package com.zkn.springmvc.component.loginer.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zkn.springmvc.util.Constants;

/**
 * 登录器
 * @author MR.SU
 *
 */
@Controller
@RequestMapping
public class LoginController {
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		if(session.getAttribute(Constants.LOGIN_SESSION_NAME) != null){
			session.removeAttribute(Constants.LOGIN_SESSION_NAME);
		}
		
		Cookie cookie = new Cookie(Constants.LOGIN_COOKIE_NAME, null);
		cookie.setMaxAge(0);
		cookie.setDomain(".cn100.com");
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return Constants.REDIRECT_INDEX;
	}
}
