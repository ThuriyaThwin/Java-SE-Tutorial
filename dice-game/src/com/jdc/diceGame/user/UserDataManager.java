package com.jdc.diceGame.user;

public interface UserDataManager {

	void create(UserData userdata);
	UserData find(String userName);
}
