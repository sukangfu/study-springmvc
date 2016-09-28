package com.zkn.springmvc.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zkn.springmvc.service.PetService;

@Controller
@RequestMapping("/pets/{ownerId}")
public class PetController {
	
	@Resource(name="petService")
	private PetService petService;
	
	/**
	 * URL 中的占位符{xxx} 可通过PathVariable("xxx")绑定到操作方法的入参中。
	 * 类定义处的URL如果使用占位符的参数，也可以绑定到处理方法的入参中。
	 * 最好在PathVariable中显示指定绑定的参数名以避免因编译方式不同造成参数绑定失败的隐患。
	 * @param ownerId
	 * @param petId
	 * @return
	 */
	//http://localhost/springmvc/pets/123/22.html
	@RequestMapping("/{petId}")
	public ModelAndView findPet(@PathVariable("ownerId") Integer ownerId, @PathVariable Integer petId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("pet", petService.findPet(ownerId, petId));
		mav.setViewName("pet/show");
		return mav;
	}
}
