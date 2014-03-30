package models;


public class File {
    public String name;
    public User from;
    public User to;
    public String status;
    
    
    public File(){}
    
    public File(String name, User from, User to){
    	this.name = name;
    	this.from = from;
    	this.to = to;
    	this.status = "Sending...";
    }
    
    public String toString() {
        return name;
    }        
}
