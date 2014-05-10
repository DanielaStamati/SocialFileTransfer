package models;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User  {

	private String name;
    private static int PORT;
    private static String IP;

    private DefaultListModel<FileModel> fileListModel;
    private ArrayList<String> filesNames = new ArrayList<String>();
    
    
    public User(){}
    
    public User (@JsonProperty("name") String name, @JsonProperty("ip") String IP, @JsonProperty("port") int PORT, 
    		@JsonProperty("files") ArrayList<String> filesNames){
    	
    	this.name = name;
        this.IP = IP;
        this.PORT = PORT;
        this.filesNames = filesNames;

    }
    
    public String toString() {
        return name;
    }

    public String getName () {
        return this.name;
    }

    public boolean equals(User u) {    	
    	return u.toString().equalsIgnoreCase(name);   	
    }
    
    public DefaultListModel<FileModel> getHistoryFileListModel(){
        if(fileListModel == null){
        	fileListModel = new DefaultListModel<FileModel>();
        }

        return fileListModel;
    }

    public static int getPORT() {
        return PORT;
    }

    public static String getIP() {
        return IP;
    }
    
    public ArrayList<String> getFilesNames () {
    	return this.filesNames;
    }
}
