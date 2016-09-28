package com.zkn.springmvc.web.controller.test;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.zkn.springmvc.domain.entity.User;

/**
 * ��������
 * 
 * ʹ��RestTemplate �� MessageController�ķ������в���.
 * ���ģ�����ڿͻ��˳����п�ʹ�ø������Web�������˵ķ�����֧��REST����URL��
 * RestTemplateĬ��ע���HttpMessageConverter��
 * ByteArraHttpMessageConverter��StringHttpMessageConverter��ResourceHttpMessageConverter
 * SourceHttpMessageConverter��XmlAwareFormHttpMessageConverter
 * 
 * @author Administrator
 *
 */
public class MessageControllerTest {

	@Test
	public void handle1Test(){
		RestTemplate restTemplate = new RestTemplate();
		// MultiValueMap ׼��������Ĳ������
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "Tom");
		form.add("password", "123");
		form.add("realName", "Tom");
		//(URL��ͨ��MultiValueMap׼��������Ĳ������)
		restTemplate.postForLocation("http://localhost/springmvc/message/handle1.html", form);
	}

	@Test
	public void handle2Test() throws IOException{
		RestTemplate restTemplate = new RestTemplate();
		//��url�������������ݣ�����ֵ���ͣ�urlռλ������ֵ��
		byte[] response = restTemplate.postForObject(
				"http://localhost/springmvc/message/handle2/{imageId}.html", 
				null, byte[].class, 123);
		org.springframework.core.io.Resource outFile = new FileSystemResource("e:/image_copy.jpg");
		FileCopyUtils.copy(response, outFile.getFile());
	}
	
	@Test
	public void handle3Test(){
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("userName", "mrsu");
		form.add("password", "123");
		form.add("realName", "Mr.Su");
		
		restTemplate.postForLocation("http://localhost/springmvc/message/handle3.html", form);
	}
	
	@Test
	public void handle4Test() throws IOException{
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(
				"http://localhost/springmvc/message/handle4/{imageId}.html", 
				null, byte[].class, 456);
		org.springframework.core.io.Resource outFile = new FileSystemResource("e:/image_copy_2.jpg");
		FileCopyUtils.copy(responseEntity.getBody(), outFile.getFile());
	}
	
	/**
	 * ����XML �� JSON��ʽ����Ϣת��
	 */
	@Test
	public void handle5Test(){
		RestTemplate restTemplate = buildRestTemplate();
		User user = new User();
		user.setUserName("Tom");
		user.setPassword("789");
		user.setRealName("Tom");
		
		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.valueOf("application/xml;UTR-8"));
		// XML��ʽ�ı���
//		headers.setContentType(MediaType.APPLICATION_XML);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		// JSON��ʽ�ı���
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);
		// ��User����ŵ��������У�ͬʱָ�����󷽷��ͱ���ͷ
		// ʹ��tcpTrace��������Ϣ���ɿ���XML/JSON�����壩���ʶ˿�����Ϊ8899��tcpTrace���Զ�ת�����������˿�
		ResponseEntity<User> responseEntity = restTemplate.exchange(
				"http://localhost:8899/springmvc/message/handle5.html", 
				HttpMethod.POST, requestEntity, User.class);
		
		// ����Ӧ��Ϣת��ΪUser�����ж�ֵ�Ƿ���ȷ
		User responseUser = responseEntity.getBody();
		Assert.assertEquals("1000", responseUser.getUserId().toString());
		Assert.assertEquals("Tom", responseUser.getUserName());
		Assert.assertEquals("111111111", responseUser.getPassword());
		
	}
	
	// װ�䴦��XML �� JSON ���ĵ�ת������RestTemplate
	private RestTemplate buildRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		
		XStreamMarshaller xsm = new XStreamMarshaller();
		xsm.setStreamDriver(new StaxDriver());
		xsm.setAnnotatedClasses(new Class[]{User.class});
		
		MarshallingHttpMessageConverter mhmc = new MarshallingHttpMessageConverter();
		mhmc.setMarshaller(xsm);
		mhmc.setUnmarshaller(xsm);
		
		// װ�䴦��XML���ĵ�ת����
		restTemplate.getMessageConverters().add(mhmc);
		
		MappingJacksonHttpMessageConverter mjhmc = new MappingJacksonHttpMessageConverter();
		// װ�䴦��JSON���ĵ�ת����
		restTemplate.getMessageConverters().add(mjhmc);
		
		return restTemplate;
	}

}