package webservice;


import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;

public class RegisterNewUser {
	

	public static void register (User user) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		String urlString;
		URL url;
		
		try {
			jsonString = mapper.writeValueAsString(user);
			System.out.println(jsonString);
			urlString = "http://localhost:8080/WebServer/NewUser?json=" + jsonString;
			url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.connect();
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
	}
	
}
