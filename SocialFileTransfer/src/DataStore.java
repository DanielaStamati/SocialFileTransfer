import java.util.ArrayList;
import java.util.List;

import models.File;
import models.User;



public class DataStore {
	
    private static DataStore _instance;
    
    private List<File> fileList;
    private List<User> userList;
    
    
    public static void init() {
        if (_instance == null) {
            _instance = new DataStore();            
        }
    }
    
    public static DataStore getInstance() {
        return _instance;
    }
    
    
    // ============== FileList Utils =============== //
    
    public void addToFileList(File file){
    	
    	if(fileList == null){
    		fileList = new ArrayList<File>();
    	}
    	
    	fileList.add(file);
    	//TODO: notify the mediator    	
    }
    
    //removes if it exists
    public boolean removeFromFileList(File file){
    	
    	if(fileList == null){
    		return false;
    	}
    	
    	if(fileList.contains(file)){
    		fileList.remove(file);
    		//TODO: notify the mediator 
    		return true;
    		
    	}else{
    		return false;
    	}
    	
    }    
    
    //removes if it exists
    public boolean removeFromFileList(Integer index){
    	
    	if(fileList == null){
    		return false;
    	}
    	
    	if(fileList.size()<=index+1){
    		fileList.remove(index);
    		//TODO: notify mediator 
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public File getFile(Integer index){
    	return fileList.get(index);
    }
    
    public List<File> getFileList(){
    	if(fileList == null){
    		fileList = new ArrayList<File>();
    	}
    	
    	return fileList;
    }
    
    
    // ============== UserList Utils =============== //
    
	public void addToUserList(User user){
    	
    	if(userList == null){
    		userList = new ArrayList<User>();
    	}
    	
    	userList.add(user);
    	//TODO: notify the mediator    	
    }
	
	
    public boolean removeFromUserList(User user){
    	
    	if(userList == null){
    		return false;
    	}
    	
    	if(userList.contains(user)){
    		userList.remove(user);
    		//TODO: notify the mediator 
    		return true;
    		
    	}else{
    		return false;
    	}
    	
    }
    
    
    //removes if it exists
    public boolean removeFromUserList(Integer index){
    	
    	if(userList == null){
    		return false;
    	}
    	
    	if(userList.size()<=index+1){
    		userList.remove(index);
    		//TODO: notify the mediator 
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public User getUser(Integer index){
    	
    	if(index<userList.size()){    	
    		return userList.get(index);
    	}
    	
    	return null;
    }
    
    public List<User> getUserList(){
    	if(userList == null){
    		userList = new ArrayList<User>();
    	}
    	
    	return userList;
    }
    
}