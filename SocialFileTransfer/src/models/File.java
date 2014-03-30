package models;


public class File {
    private String name;
    private User from;
    private User to;
    private String status;
    
    
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
