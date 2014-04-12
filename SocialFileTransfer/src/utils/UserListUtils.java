package utils;

import javax.swing.DefaultListModel;

import main.DataStore;
import models.File;
import models.User;

public class UserListUtils {
	
	DefaultListModel<User> userListModel;
	
	public UserListUtils() {
		this.userListModel = DataStore.getInstance().getUserListModel();
	}

    public void addToUserList(User user){

        if(userListModel == null){
            userListModel = new DefaultListModel<User>();
        }

        userListModel.addElement(user);
    }

    public boolean removeFromUserList(User user){

        if(userListModel == null){
            return false;
        }

        if(userListModel.contains(user)){
            userListModel.removeElement(user);
            return true;

        }else{
            return false;
        }

    }

    //removes if it exists
    public boolean removeFromUserList(Integer index){

        if(userListModel == null){
            return false;
        }

        if(userListModel.size()<=index+1){
            userListModel.remove(index);
            return true;
        }else{
            return false;
        }

    }

    public User getUserAt(Integer index){

        if(index<userListModel.size()){
            return userListModel.get(index);
        }

        return null;
    }



}
