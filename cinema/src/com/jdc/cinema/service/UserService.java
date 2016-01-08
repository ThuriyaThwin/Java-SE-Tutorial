package com.jdc.cinema.service;

import java.util.List;

import com.jdc.cinema.entity.Role;
import com.jdc.cinema.entity.User;
import com.jdc.cinema.service.imp.SecurityServiceImp;

public interface UserService extends SecurityService{
	
	boolean create(User user);
	List<User> search(String name, Role role);
	
	public static UserService getInstance() {
		return SecurityServiceImp.getUserService();
	}

}
