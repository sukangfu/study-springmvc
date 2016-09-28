package com.zkn.springmvc.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.zkn.springmvc.domain.entity.User;

/**
 * 如果希望在多个请求之间共用某个模型属性数据，则可以在控制器类上标注一个SessionAttribute注解，
 * SpringMVC会将模型中对应的属性暂存到HttpSession中。
 * 
 * 1. SpringMVC在调用处理方法前，在请求线程中自动创建一个隐含的模型对象。
 * 2. 调用所有方法级标注了ModelAttribute注解的方法，并将方法返回值添加到隐含模型中。
 * 3. 查看Session中是否存在SessionAttribute("xxx")所指定的xxx属性，如果有，将其添加到隐含模型中。
 *     如果隐含模型中已经有了xxx属性，该步操作会覆盖模型中已有的属性值。
 *
 * 4. 对标注了ModelAttribute注解的处理方法的入参按如下流程处理：
 *  4.1 如果隐含模型拥有名为xxx的属性，将其赋给该入参，再用请求信息填充该入参对象直接返回，否则
 *  4.2 如果xxx是会话属性，即在处理类定义处标注了SessionAttribute("xxx")注解，
 *  	  则尝试从会话中获取该属性，并将其赋给该入参，然后再用请求信息填充该入参对象。
 *		  如果在会话中找不到对应的属性，则抛出HttpSessionRequiredException异常。否则
 *  4.3 如果隐含模型不存在xxx属性，且xxx也不是会话属性，则创建入参的对象实例，再用请求信息填充该入参。
 * 
 *  注解SessionAttributes(value={"user1", "user2"}) 将名为user1及user2的模型属性添加到会话中
 *  注解SessionAttributes(types={User.class, Dept.class}) 将模型中所有类型为User以及Dept的属性添加到会话中
 *  注解SessionAttributes(value={"user1", "user2"}, types={Dept.class}) 将名为user1及user2的模型属性添加到会话中，
 *  同时将所有类型为Dept的模型属性添加到会话中，二者并集。
 */
@Controller
@RequestMapping("/session")
@SessionAttributes("user") //将本处理器中任何处理方法属性名为user的模型属性保存到HttpSession。
public class SessionController {

	/**
	 * 解决HttpSessionRequiredException异常
	 * 让SpringMVC再处理handle1的入参前，先往模型中添加user属性。
	 * 这样4.1会被执行，4.2不被执行，这样就不会抛出上述异常。
	 * @return
	 */
	@ModelAttribute("user")
	public User getUser(){
		User user = new User();
		user.setUserId(1006);
		user.setUserName("添加Model属性");
		
		return user;
	}
	
	@RequestMapping("/handle1")
	public String handle1(@ModelAttribute("user") User user){
		user.setRealName("修改Session的名称");
		
		/**
		 * 指示符：forward: | redirect: ，其后的字符串作为路径
		 * redirect:handle2.html 让浏览器重新发起一个新的请求
		 * forward: 所到的目标地址位于当前的请求中
		 */
		return "redirect:handle2.html";
	}
	
	/**
	 * 读取会话属性
	 * @param modelMap 隐含模型
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping("/handle2")
	public String handle2(ModelMap modelMap, SessionStatus sessionStatus){
		User user = (User) modelMap.get("user"); //读取模型中的数据
		if(user != null){
			user.setPassword("密码修改之后清理会话属性");
			
			//让SpringMVC清除本处理器对应的会话属性，否则这个会话属性会一直存在HttpSession中
			sessionStatus.setComplete(); 
		}
		return "user/show";
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
}
