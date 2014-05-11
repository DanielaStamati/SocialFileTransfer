package webservice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import models.User;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RegisterNewUser {
	

	public static void register (User user) {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		String urlString;
		URL url;
		
		try {
			jsonString = mapper.writeValueAsString(user);
			System.out.println("\n\n\nINITIAL STRING:\n");
			System.out.println(jsonString);
			urlString = "http://localhost:8080/WebServer/UserRegistrer?json=" + jsonString;
			url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.connect();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			System.out.println(br.readLine());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
