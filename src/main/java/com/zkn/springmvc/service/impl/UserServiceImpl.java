package com.zkn.springmvc.service.impl;

import org.springframework.stereotype.Service;

import com.zkn.springmvc.domain.entity.User;
import com.zkn.springmvc.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	/* (non-Javadoc)
	 * @see com.zkn.service.impl.UserService#createUser(com.zkn.domain.User)
	 */
	public void createUser(User user){
		System.out.println("调用UserService#createUser() 方法...保存用户...");
	}
	
	public User getUser(Integer userId) {
		
		System.out.println("查看用户明细....userId="+userId);
		
		User user = new User();
		user.setUserId(userId);
		user.setUserName("mrsu");
		user.setPassword("123");
		user.setRealName("Mr.Su");
		
		return user;
	}
	
	public User get(Integer userId) {
		
		System.out.println("查看用户明细....userId="+userId);
		
		User user = new User();
		user.setUserId(userId);
		user.setUserName("mrsu");
		user.setPassword("123");
		user.setRealName("Mr.Su");
		
		return user;
	}

	@Override
	public void merge(User user) {
		System.out.println("更新用户信息");
	}
	
	public void save(User user){
		System.out.println("调用UserService#save() 方法...保存用户...");
	}
}
