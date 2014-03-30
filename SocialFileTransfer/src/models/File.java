package models;


public class File {
    public String name;
    public User from;
    public User to;
    public float progress;
    public String status;
    
    
    public File(){}
    
    public File(String name, User from, User to){
    	this.name = name;
    	this.from = from;
    	this.to = to;
    	this.status = "Sending...";
    	this.progress = 0.f;
    }
    
    public String toString() {
        return name;
    }        
}
