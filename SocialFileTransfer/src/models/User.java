package models;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String name;
    
    public User(){}
    
    public User (String name){
    	this.name = name;
    }
    
    public String toString() {
        return name;
    }
}
