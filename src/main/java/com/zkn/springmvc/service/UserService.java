package com.zkn.springmvc.service;

import com.zkn.springmvc.domain.entity.User;

public interface UserService {

	void createUser(User user);
	
	void save(User user);
	
	User getUser(Integer userId);
	
	User get(Integer userId);
	
	void merge(User user);

}