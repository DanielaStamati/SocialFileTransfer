package servlets;

import java.util.ArrayList;

public class UsersSingleton {
	
	private static ArrayList<User> usr = new ArrayList<User>();
	private static final UsersSingleton instance = null;
	
	private UsersSingleton () {
		
	}
	
	public static UsersSingleton getInstance () {
		if (instance == null)
			return new UsersSingleton();
		return instance;
	}

	public ArrayList<User> getUsr () {
		return instance.usr;
	}
}

