package com.jdc.diceGame.user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserDataManagerImp implements UserDataManager {

	private static final String USER_FILE = "userdata.obj";

	private static UserDataManager MANAGER;

	private Map<String, UserData> dataMap;

	@SuppressWarnings("unchecked")
	private UserDataManagerImp() {
		try (ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(USER_FILE))) {
			
			// read file to object
			dataMap = (Map<String, UserData>) in.readObject();

		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}

		// if null -> initialize map
		if(null == dataMap) {
			dataMap = new HashMap<>();
		}
	}

	public static UserDataManager getManager() {

		if (null == MANAGER)
			MANAGER = new UserDataManagerImp();

		return MANAGER;
	}

	@Override
	public void create(UserData userdata) {
		
		if(dataMap.keySet().contains(userdata.getUser().getName())) {
			throw new RuntimeException("Please select other name!");
		}
		
		// put data to map
		dataMap.put(userdata.getUser().getName(), userdata);

		// save to file
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(USER_FILE))) {
			out.writeObject(dataMap);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public UserData find(String userName) {
		return dataMap.get(userName);
	}

}
