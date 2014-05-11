package webservice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CloseApp {
	
	public static void LeaveAplication (String username) {
		
		URL url = null;
		String urlString = "http://localhost:8080/WebServer/LeaveApplication?user=" + username;
		
		try {
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
