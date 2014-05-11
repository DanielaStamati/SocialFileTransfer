package servlets;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	
	private String name;
	private String IP;
	private int PORT;
	ArrayList<String> files;
	
	public User (@JsonProperty("name") String name, @JsonProperty("IP") String IP, @JsonProperty("PORT") int PORT, 
			@JsonProperty("files") ArrayList<String> files) {
		
		this.name = name;
		this.IP = IP;
		this.PORT = PORT;
		this.files = files;	
	}
	
	public String getName () {
		return name;
	}
	
	public int getPort () {
		return PORT; 
	}
	
	public String getIP () {
		return IP;
	}
	
	public ArrayList<String> getFiles () {
		return files;
	}
	
	public void setName(String lastName) {
		this.name = lastName;
	}	
	
	public void setIP(String IP) {
		this.IP = IP;
	}	
	
	public void setPort(int PORT) {
		this.PORT = PORT;
	}
	
	public void setFiles(ArrayList<String> files) {
		 this.files = files;
	}
	 
	@Override
	public String toString() {
		return name + " " + IP + " " + PORT; 
	}
	
}
