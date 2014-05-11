package servlets;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersList {
	
	public ArrayList<User> users = new ArrayList<User>();
	
	public UsersList(@JsonProperty("users") ArrayList<User> users){
		this.users = users;
	}
	
	
	
}
