package com.zkn.springmvc.web.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ModelControllerTest {

	@Test
	public void testUpdate() {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userId", "123");
		form.add("userName", "newName");
		
		restTemplate.postForLocation(
				restTemplate.postForLocation("http://localhost/springmvc/model/update.html", form)
				, null);
		
	}

	@Test
	public void testUpdate2() {
		fail("Not yet implemented");
	}

}
