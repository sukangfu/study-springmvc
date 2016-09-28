package com.zkn.springmvc.service.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.service.UserService;

/**
 * AbstractTransactionalJUnit4SpringContextTests ���������ʱ��Ҫ�̳�
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/applicationContext.xml"})
public class UserServiceImplTest// extends  AbstractTransactionalJUnit4SpringContextTests
{

	@Resource(name="userService")
	private UserService userService;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		
		userService.createUser(user);
		
		//����spring��Դ������
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			org.springframework.core.io.Resource[] resurces = resolver.getResources("classpath*:com/baobaotao/**/*.xml");
			for(org.springframework.core.io.Resource resurce : resurces){
				System.out.println(resurce.getFilename() + ": " + resurce.getURL());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
