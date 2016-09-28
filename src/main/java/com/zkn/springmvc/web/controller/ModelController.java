package com.zkn.springmvc.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.service.UserService;


/**
 *ModelAttribute注解
 *如果希望将方法入参对象添加到模型中，仅需在相应入参前使用ModelAttribute注解即可。
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/model")
public class ModelController {

	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * 除了可以在入参中使用ModelAttribute注解，还是在方法定义中使用该注解。
	 * SpringMVC在调用目标处理方法前，会先逐个调用在方法级上标注了@ModelAttribute的方法，
	 * 并将这些方法的返回值添加到模型中。
	 * 
	 * 访问Controller任一个请求处理方法之前，SpringMVC先执行该方法，并将返回值以user为键添加到模型中
	 * @return
	 */
	@ModelAttribute("user")
	public User getUser(Integer userId){

		return userService.getUser(userId);
	}

	/**
	 * ModelAttribute注解将入参对象添加模型中
	 * 
	 * SpringMVC将请求信息绑定到User对象中，然后以“user”为键将User对象放到模型中。
	 * 在准备对视图进行渲染前，SpringMVC进一步将模型中的数据转储到视图上下文中暴露给视图对象。
	 * 对于JSP视图，SpringMVC会将模型数据转储到ServletRequest的属性列表中
	 * （通过ServletRequest#setAttribute(String， Object)）
	 * 
	 * 由于下面有注解了ModelAttribute的方法getUser()，
	 * SpringMVC先执行getUser()，将返回的模型数据先赋给入参user，
	 * 然后根据HTTP请求信息进一步对user进行填充覆盖，最终得到整合版的user对象。
	 * 
	 * 	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	public String update(@ModelAttribute("user") User user){
		user.setUserId(1000);
		user.setRealName("Kangfu");
		return "user/show";
	}
	
	/**
	 * 如果处理方法有Map或者Model类型的入参，SpringMVC就会将隐含模型的引用传递给这些参数。
	 * 因此在方法体中可以通过这个入参对模型中的数据进行读写。
	 * 
	 * 配上getUser()一起使用
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/update2")
	public String update2(ModelMap modelMap){
		modelMap.addAttribute("testAtt", "value1");
		User user = (User) modelMap.get("user");  //getUser()注解的value
		user.setUserName("Tom11");
		return "user/show";
	}
	
}
