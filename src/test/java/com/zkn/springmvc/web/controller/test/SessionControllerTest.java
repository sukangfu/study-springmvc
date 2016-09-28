package com.zkn.springmvc.web.controller.test;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SessionControllerTest {
	 
	//≤‚ ‘ª·ª∞ Ù–‘
	@Test
	public void handle1Test(){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "SCT");
		form.add("realName", "SessionController");
		form.add("password", "123456");
		
		restTemplate.postForLocation(
				restTemplate.postForLocation("http://localhost/springmvc/session/handle1.html", form)
				, null);
	}
}
