package com.jdc.cinema.service.imp;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdc.cinema.dao.Dao;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.entity.Role;
import com.jdc.cinema.entity.Role.Roles;
import com.jdc.cinema.service.SecurityService;
import com.jdc.cinema.service.UserService;
import com.jdc.cinema.entity.User;
import com.jdc.cinema.utils.ConnectionManager;

public class SecurityServiceImp implements UserService {

	private static SecurityServiceImp SERVICE;
	
	public static UserService getUserService() {
		if(SERVICE == null)
			SERVICE = new SecurityServiceImp();
		
		return SERVICE;
	}
	
	public static SecurityService getSecurityService() {
		if(SERVICE == null)
			SERVICE = new SecurityServiceImp();
		
		return SERVICE;
	}
	
	private Dao<User> userDao;
	private Dao<Role> roleDao;
	
	private SecurityServiceImp() {
		userDao = Dao.getDao(User.class);
		roleDao = Dao.getDao(Role.class);
	}

	@Override
	public boolean isNoUser() {
		return userDao.getAllCount() == 0;
	}

	@Override
	public Role login(String login, String pass) {
		try {
			String encPass = SecurityService.getHash(pass);
			List<User> list = userDao.getWhere("login = ? and password = ?", 
					Arrays.asList(login, encPass));
			
			User u = list.get(0);
			
			return roleDao.findById(IdParam.getInstance("id", u.getRoleId()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void changePass(String login, String oldPass, String newPass) {

	}

	@Override
	public void signUp(String name, String login, String pass) {
		try {
			String encPass = SecurityService.getHash(pass);
			User u = new User();
			u.setLoginId(login);
			u.setName(name);
			u.setPassword(encPass);
			u.setRoleId(Roles.Manager.toString());
			
			userDao.insert(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createRole() {
		
		ConnectionManager.clearTable("role");
		
		for(Roles r : Roles.values()) {
			Role role = new Role();
			role.setId(r.toString());
			role.setRoleName(r.toString());
			
			roleDao.insert(role);
		}
	}

	@Override
	public List<Role> getRoles() {
		return roleDao.getAll();
	}

	@Override
	public boolean create(User user) {
		try {
			String encPass = SecurityService.getHash(user.getPassword());
			user.setPassword(encPass);
			
			user = userDao.insert(user);
			
			return user != null;
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> search(String name, Role role) {
		String where = null;
		boolean firstTime = true;
		List<Object> param = null;
		
		if(null != name && !name.isEmpty()) {
			where = "name like ?";
			param = Arrays.asList(name + "%");
			firstTime = false;
		}
		
		if(null != role) {
			if(!firstTime) {
				where += " and ";
			} else {
				where = "";
				param = new ArrayList<>();
			}
			
			where += "role_id = ?";
			param.add(role.getId());
		}
		return userDao.getWhere(where, param);
	}

}
