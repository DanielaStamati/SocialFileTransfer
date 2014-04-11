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
    	//TODO update
    	this.status = StringConstants.sending;
    	this.progress = 0.f;
    }
    
    public String toString() {
        return name;
    }        
}
