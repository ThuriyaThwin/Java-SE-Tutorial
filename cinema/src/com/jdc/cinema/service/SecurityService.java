package com.jdc.cinema.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.jdc.cinema.entity.Role;
import com.jdc.cinema.service.imp.SecurityServiceImp;

public interface SecurityService {
	
	public static SecurityService getInstance() {
		return SecurityServiceImp.getSecurityService();
	}
	
	public static String getHash(String string) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte [] data = digest.digest(string.getBytes());
		BigInteger bi = new BigInteger(1, data);
		return bi.toString();
	}
	
	void signUp(String name, String login, String pass);
	boolean isNoUser();
	Role login(String login, String pass);
	void changePass(String login, String oldPass, String newPass);
	void createRole();

	List<Role> getRoles();
}
