package utils;

import main.DataStore;
import models.User;

import javax.swing.*;
import java.util.Iterator;

public class UserListUtils implements Iterable<User>{
	
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

        if(userListModel.size() <= index + 1){
            userListModel.remove(index);
            return true;
        }else{
            return false;
        }

    }

    public User getUserAt(Integer index){

        if(index < userListModel.size()){
            return userListModel.get(index);
        }

        return null;
    }
    
    public void removeAllUsers(){
    	userListModel.removeAllElements();
    }


    @Override
    public Iterator<User> iterator() {
        Iterator<User> it = new Iterator<User>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < userListModel.size() && userListModel.get(currentIndex) != null;
            }

            @Override
            public User next() {
                return userListModel.get(currentIndex++);
            }

            @Override
            public void remove() {

            }
        };
        return it;
    }
}
