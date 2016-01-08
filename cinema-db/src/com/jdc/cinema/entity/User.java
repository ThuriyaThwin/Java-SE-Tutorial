package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class User implements Entity{

	private static final long serialVersionUID = 1L;
	
	@ID
	@Column("login")
	private String loginId;
	private String name;
	private String password;
	@Column("role_id")
	private String roleId;

	@Override
	public Param getInsertParam() {
		return Param.getInstance("login", loginId)
				.param("name", name)
				.param("password", password)
				.param("role_id", roleId);
	}
	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("login", loginId);
	}

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
