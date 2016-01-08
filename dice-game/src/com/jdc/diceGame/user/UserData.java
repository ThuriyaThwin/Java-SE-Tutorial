package com.jdc.diceGame.user;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Player user;
	private Player system;
	private Date lastUpdate;
	
	public UserData() {
	}
	
	public UserData(Player user, Player system) {
		super();
		this.user = user;
		this.system = system;
		lastUpdate = new Date();
	}

	public Player getUser() {
		return user;
	}
	public void setUser(Player user) {
		this.user = user;
	}
	public Player getSystem() {
		return system;
	}
	public void setSystem(Player system) {
		this.system = system;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
