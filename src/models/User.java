package models;

import javax.swing.DefaultListModel;


public class User {
    private String name;
    private static int PORT;
    private static String IP;

    private DefaultListModel<FileModel> fileListModel;
    
    
    public User(){}
    
    public User (String name, String IP, int PORT){
    	this.name = name;
        this.IP = IP;
        this.PORT = PORT;

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
}
