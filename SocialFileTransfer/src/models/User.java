package models;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;


public class User {
    private String name;
    private DefaultListModel<File> fileListModel;
    
    
    public User(){}
    
    public User (String name){
    	this.name = name;
    }
    
    public String toString() {
        return name;
    }
    
    public boolean equals(User u) {    	
    	return u.toString().equalsIgnoreCase(name);   	
    }
    
    public DefaultListModel<File> getHistoryFileListModel(){
        if(fileListModel == null){
        	fileListModel = new DefaultListModel<File>();
        }

        return fileListModel;
    }
}
