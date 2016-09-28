package com.zkn.springmvc.web.controller;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zkn.springmvc.domain.entity.User;

/**
 * 当控制器的处理方法使用到//@RequestBody//@ResponseBody 或 HttpEntity<?>/ResponseEntity<?>时，
 * SpringMVC才使用注册的HttpMessageConverter对请求/相应的消息进行处理 
 *
 * 测试类：MessageControllerTest
 */
@Controller
@RequestMapping("/message")
public class MessageController{

	/**
	 * 将请求报文体转换为字符串绑定到requestBody
	 * @param requestBody
	 * @return
	 */
	@RequestMapping(value="/handle1", method=RequestMethod.POST)
	public String handle1(@RequestBody String requestBody){
		
		System.out.println("***requestBody="+requestBody);
		return "message/success";
	}
	
	/**
	 * 读取一张图片，并将图片数据输出到响应流中，将图片数据流输出到客户端
	 * 
	 * 由于返回值类型byte[]，SpringMVC根据类型匹配的查找规则将使用ByteArrayHttpMessageConverter
	 * 对返回值进行处理。
	 * @param imageId
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/handle2/{imageId}")
	public byte[] handle2(@PathVariable("imageId") Integer imageId) throws IOException{
		
		System.out.println("***load image of "+imageId);
		org.springframework.core.io.Resource res = new ClassPathResource("/images/1.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		
		return fileData;
	}
	
	/**
	 * HttpEntity<?>不仅可以访问请求及响应报文体的数据，而且能够访问请求和响应报文头的数据。
	 * SpringMVC根据HttpEntity的泛型类型选择相应的HttpMessageConverter。
	 * 此时，使用StringHttpMessageConverter，StringHttpMessageConverter将请求报文体以及报文头的信息绑定到Entity中。
	 * 
	 * @param httpEntity
	 * @return
	 */
	@RequestMapping(value="/handle3", method=RequestMethod.POST)
	public String handle3(HttpEntity<String> httpEntity){
		Long contentLen = httpEntity.getHeaders().getContentLength();
		System.out.println(httpEntity.getBody() + "/n ***Content-Length = " + contentLen);
		return "message/success";
	}
	
	/**
	 * HttpEnttiy<?>/ResponseEnttiy<?> 跟 注解RequestBody/ResponseBody 功能相似
	 * @param ImageId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/handle4/{imageId}")
	public ResponseEntity<byte[]> handle4(@PathVariable("imageId") Integer ImageId) throws IOException{
		org.springframework.core.io.Resource res = new ClassPathResource("/images/2.jpg");
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(fileData, HttpStatus.OK);
		
		return responseEntity;
	}
	
	/**
	 * 对于服务端的处理方法而言，除进行方法签名外，不需额外的操作，借由SpringMVC装配的HttpMessageConverter，
	 * 它即拥有了处理XML和JSON格式信息的能力。然后通过请求报文头的Content-Type以及Accept的属性值来确定信息格式。
	 * @param requestEntity
	 * @return
	 */
	@RequestMapping("/handle5")
	public ResponseEntity<User> handle5(HttpEntity<User> requestEntity){
		User user = requestEntity.getBody();
		user.setUserId(1000);
		user.setPassword("111111111");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
