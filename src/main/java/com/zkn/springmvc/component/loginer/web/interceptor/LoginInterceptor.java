package com.zkn.springmvc.component.loginer.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zkn.springmvc.component.loginer.annotation.Logined;
import com.zkn.springmvc.component.loginer.enums.LoginResultType;
import com.zkn.springmvc.component.loginer.enums.LoginedType;
import com.zkn.springmvc.component.loginer.web.WebUtils;
import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.domain.enums.UserStatus;
import com.zkn.springmvc.service.UserService;
import com.zkn.springmvc.util.Constants;

/**
 * 登录拦截器
 * @author MR.SU
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		HandlerMethod hm = (HandlerMethod) handler;
		Logined logined = hm.getMethodAnnotation(Logined.class);
		
		if(logined == null){//不需要登录
			return true;
		} else {//需要登录
			String message = "";
			String redirectUrl = "";
			
			LoginedType loginedType = loginHandle(request, response);
			switch(loginedType){
				case SESSION : 
					return super.preHandle(request, response, handler);
				case LOGIN : 
					redirectUrl = WebUtils.getLoginUrl(request);
					message = Constants.MESSAGE_UNLOGINED;
					break;
				case SIGN : 
					redirectUrl = WebUtils.getSignUrl(request);
					message = Constants.MESSAGE_UNSIGNED;
					break;
				case OTHER : 
					redirectUrl = Constants.COPYRIGHT;
					message = Constants.MESSAGE_UNNORMAL;
					break;
				default : break;
			}
			
			if(logined.value().equals(LoginResultType.PAGE)){
				response.sendRedirect(redirectUrl);
			} else if(logined.value().equals(LoginResultType.JSON)){
				WebUtils.buildJsonResult(response, message, redirectUrl);
			}
		} 
		
		return false;
	}
	
	/**
	 * 验证登录信息
	 * @param request
	 * @param response
	 * @return
	 */
	private LoginedType loginHandle(HttpServletRequest request,
			HttpServletResponse response){
		
		HttpSession session = request.getSession();
		User cookieUser = (User) session.getAttribute(Constants.LOGIN_SESSION_NAME);
		
		if(cookieUser == null){
			cookieUser = WebUtils.getCookieValue(request);
			
			if(cookieUser == null){
				return LoginedType.LOGIN;
			} else {
				 session.setAttribute(Constants.LOGIN_SESSION_NAME, cookieUser);
			}
		} 
		
		//登录了用户是否正常使用
		if(verify(cookieUser)){
			User signedUser = null;
			try {
				signedUser = userService.get(cookieUser.getUserId());
				if(signedUser.isSigned()){
					cookieUser.setLastLoginTime(new Date());
					cookieUser.setSigned(true);
					userService.merge(cookieUser);
					return LoginedType.SESSION;
				} else {
					return LoginedType.SIGN;
				}
			} catch (Exception e) {
				//ObjectNotFoundException
				logger.info("用户[%s]不存在...", cookieUser.getUserName());
			}
		} 

		return LoginedType.OTHER;
	}

	/**
	 * 验证登录用户合法
	 * @param user
	 * @return
	 */
	private boolean verify(User user){
		Assert.notNull(user);
		
		return UserStatus.ACTIVED.equals(user.getStatus());
	}
}
