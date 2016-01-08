package com.jdc.cinema.entity;

import com.jdc.cinema.dao.Column;
import com.jdc.cinema.dao.Entity;
import com.jdc.cinema.dao.ID;
import com.jdc.cinema.dao.IdParam;
import com.jdc.cinema.dao.Param;

public class Role implements Entity{

	private static final long serialVersionUID = 1L;
	@ID
	private String id;
	@Column("role_name")
	private String roleName;
	
	public enum Roles {Reception, Manager};

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString() {
		return roleName;
	}
	@Override
	public Param getInsertParam() {
		return Param.getInstance("id", id).param("role_name", roleName);
	}
	@Override
	public IdParam getIdParam() {
		return IdParam.getInstance("id", id);
	}

}
