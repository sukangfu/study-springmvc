package com.zkn.springmvc.web.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.service.UserService;

/**
 * 用户处理请求控制器
 * Controller 声明POJO对象为控制器
 * RequestMapping 标注对应的请求URL，此处处理来自于"/user"URI下的请求
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * 如果类定义处没有标注@RequestMapping，
	 * 那处理方法标注指定的URL则相对于Web应用的部署路径
	 * 
	 * RequestMapping 支持 Ant风格：？、* 和 ** 和 占位符 {xxx} URL
	 * ？匹配文件名任一个字符，* 匹配文件名任意多个字符，** 匹配多层路径
	 * @return 方法返回字符串代表逻辑视图名
	 */
	//http://localhost/springmvc/user/register.html
//	@RequestMapping("/register")
	//  匹配 http://localhost/springmvc/user/a/b/AAAAAregisterA.html
	//  不匹配 http://localhost/springmvc/user/AAAAAregisterA.html
	@RequestMapping("/**/*register?")
	public String register(){
		return "user/register";
	}
	
	/**
	 * 处理/user的请求，请求方法必须是post
	 * 将表单值映射到User对象中
	 * @param user
	 * @return ModelAndView
	 */
	//form : http://localhost/springmvc/user.html
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView createUser(User user){
		userService.createUser(user);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/success");

		mav.addObject("user", user);
		
		return mav;
	} 
	
	/**
	 * PathVariable 可将URL中的占位符参数绑定到控制器处理方法的入参中
	 */
   //  匹配 http://localhost/springmvc/user/123.html
	@RequestMapping("/{userId}")
	public ModelAndView show(@PathVariable("userId") Integer userId){
			ModelAndView mav = new ModelAndView();
			mav.addObject("user", userService.getUser(userId));
			mav.setViewName("user/show");
			
			return mav;
	}

	/**
	 * 使用请求方法及请求参数映射请求
	 * 
	 * params表达式说明：“param1”必须含有；"!param2"不能含有；
	 * "param1!=value1"参数值不能等于；“param1=value1”参数值必须等于。
	 */
	//匹配 http://localhost/springmvc/user/search.html?userId=123
//	@RequestMapping(value="/search", params="userId")
	//匹配  http://localhost/springmvc/user/search.html?userId=123&userName=mrsu&realName=Mr.Su
	//不匹配（参数含有password）http://localhost/springmvc/user/search.html?userId=123&userName=mrsu&realName=Mr.Su&password=123
	//不匹配（参数userName=admin）http://localhost/springmvc/user/search.html?userId=123&userName=admin&realName=Mr.Su
	//不匹配（参数不含userId）http://localhost/springmvc/user/search.html?userName=mrsu&realName=Mr.Su
	@RequestMapping(value="/search", params={"userId", "userName!=admin", "!password", "realName=Mr.Su"})
	public String search(@RequestParam("userId") Integer userId){
		return "user/show";
	}
	
	@RequestMapping("/delete")
	public String delete(){
		return "user/delete";
	}

	/////////////////////////////////////////////////////////////////
	//  几种典型的处理方法签名
	/////////////////////////////////////////////////////////////////
	/**
	 * 请求参数按名称匹配的方式绑定到方法入参中
	 * @param userName
	 * @param password
	 * @param realName
	 * @return
	 */
	//http://localhost/springmvc/user/handle1.html?userName=abc&password=123&realName=abc
	@RequestMapping(value="/handle1")
	public String handle1(
				@RequestParam("userName") String userName,
				@RequestParam(value="password", required=false) String password,
				@RequestParam("realName") String realName
			){
		
		//do something...
		System.out.println("userName="+userName);

		return "user/success";
	}
	
	/**
	 * 将Cookie值及报文头属性绑定到入参中
	 * @return
	 */
	@RequestMapping("/handle2")
	// http://localhost/springmvc/user/handle2.html
	public String handle2(
				@CookieValue("JSESSIONID") String sessionid,
				@RequestHeader("Accept-Language") String acceptLanguage
			){

		//do something...
		System.out.println("Accept-Language="+acceptLanguage);
		
		return "user/success";
	}
	
	/**
	 * 请求参数按名称匹配方式绑定到user属性中
	 * 
	 * SpringMVC会按照请求参数名和命令/表单对象属性名匹配的方式，自动为该对象填充属性值。
	 * 支持级联的属性名。如dept.deptId、dept.address.tel
	 * @param user
	 * @return
	 */
//	@RequestMapping(value="/handle3", method=RequestMethod.POST)
	//http://localhost/springmvc/user/handle3.html?userName=abc&password=123&realName=abc
	//...handle3.html?userName=abc&dept.deptId=123&dept.address.tel=48111111
	@RequestMapping(value="/handle3")
	public ModelAndView handle3(User user){
		//do something....
		System.out.println("userName="+user.getUserName());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/show");
		mav.addObject("user", user);
		
		return mav;
	}
	 
	/**
	 * 直接将HTTP请求对象传递给处理方法
	 * @param request
	 * @return
	 */
	//http://localhost/springmvc/user/handle4.html?userId=123
	@RequestMapping("/handle4")
	public String handle4(HttpServletRequest request){

		//do something....
		String userId = request.getParameter("userId");
		System.out.println("userId="+userId);
		
		return "user/success";
	}
	
	/**
	 * 同时使用HttpServletRequest 和 HttpServletResponse
	 * SpringMVC会自动将Web层对应的Servlet对象传递给处理方法的入参。
	 * 
	 * 处理方法使用HttpServletResponse返回响应，则处理方法返回值为void
	 * @param request
	 * @param response
	 */
	//http://localhost/springmvc/user/handle5.html?userName=123
	@RequestMapping("/handle5")
	public void handle5(HttpServletRequest request, HttpServletResponse response){
		String userName = WebUtils.findParameterValue(request, "userName");
		response.addCookie(new Cookie("userName", userName));
	}
	
	/**
	 * 使用HttpSession 作为入参
	 * @param session
	 * @return
	 */
	@RequestMapping("/handle6")
	public String handle6(HttpSession session){
		session.setAttribute("userId", 123);
		return "user/success";
	}
	
	/**
	 * HttpServletRequest 可换成Spring的代理类WebRequest
	 * @param request
	 * @param userName
	 * @return
	 */
	@RequestMapping("/handle7")
	public String handle7(HttpServletRequest request, 
			@RequestParam("userName") String userName){
		
		//....
		return "user/success";
	}
	
	/**
	 * 使用IO对象作为入参
	 * @param os
	 */
	@RequestMapping("/handle8")
	public void handle8(OutputStream os){
		//读取类路径下的图片文件
		org.springframework.core.io.Resource res = new ClassPathResource("/images/1.jpg");
		try {
			//将图片写到输出流中
			FileCopyUtils.copy(res.getInputStream(), os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
