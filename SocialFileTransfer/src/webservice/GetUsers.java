package webservice;

import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.UsersList;

public class GetUsers {

	
	public static UsersList getRegisteredUsers () {
		
		  	String urlString = "http://localhost:8080/WebServer/UserListFetcher";
	        URL url;
	        String jsonString = "";
	        UsersList users = new UsersList(null);
	        
	        
	        try {
				url = new URL(urlString);
				URLConnection conn = url.openConnection();
				conn.connect();
				
				ObjectInputStream o = new ObjectInputStream(conn.getInputStream());
				jsonString = (String) o.readObject();
				ObjectMapper mapper = new ObjectMapper();
				
				users = mapper.readValue(jsonString, UsersList.class);
				System.out.println("Reg" + jsonString);
				
			} catch (Exception e) {
				System.err.println("error");
				e.printStackTrace();
			}
	        
	        return users;
	}
	
}
