package com.zkn.springmvc.web.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zkn.spring.web.mvc.annotation.FormModel;
import com.zkn.springmvc.domain.entity.Pet;
import com.zkn.springmvc.domain.entity.User;

/**
 * 数据格式化 转换器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/format")
public class FormatController {

	/**
	 * 测试时间转换器, 使用全局自定义时间转换器
	 * 需要打开：StingToDateConverter
	 * @param user
	 * @param date
	 * @param pet
	 * @param model
	 * @return
	 */
	@RequestMapping("/date")
	public String date(
			@FormModel("user")User user, 
			@RequestParam("date")Date date, 
			@ModelAttribute("pet")Pet pet, 
			Model model){
		
		model.addAttribute("user", user);
		model.addAttribute("date", date);
		model.addAttribute("pet", pet);
		
		return "/format/date";
	}
	
	/**
	 * 使用Joda-Time时间转换器
	 * @param date
	 * @param model
	 * @return
	 */
	@RequestMapping("/date2")
	public String date2(
			@ModelAttribute("user")User user, 
			@DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("date")Date date, 
			@ModelAttribute("pet")Pet pet, 
			Model model){
		
		model.addAttribute("user", user);
		model.addAttribute("date", date);
		model.addAttribute("pet", pet);
		
		return "/format/date";
	}
	
	/**
	 * 注解设置参数数据绑定前缀
	 * @param binder
	 */
    @InitBinder("pet")    
    public void initBinder(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("pet.");    
    } 
    @InitBinder("user")    
    public void initBinder1(WebDataBinder binder) {    
    	binder.setFieldDefaultPrefix("user.");    
    } 
}
