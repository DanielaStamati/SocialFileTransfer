package main;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import models.File;
import models.User;


public class DataStore {

    private static DataStore _instance;

    private DefaultListModel<File> fileListModel;
    private DefaultListModel<User> userListModel;


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

        if(fileListModel == null){
            fileListModel = new DefaultListModel<File>();
        }

        fileListModel.addElement(file);
        //TODO: notify the mediator
    }

    //removes if it exists
    public boolean removeFromFileList(File file){

        if(fileListModel == null){
            return false;
        }

        if(fileListModel.contains(file)){
            fileListModel.removeElement(file);
            //TODO: notify the mediator
            return true;

        }else{
            return false;
        }

    }

    //removes if it exists
    public boolean removeFromFileList(Integer index){

        if(fileListModel == null){
            return false;
        }

        if(fileListModel.size() <= index + 1){
            fileListModel.remove(index);
            return true;
        }else{
            return false;
        }

    }

    public File getFileAt(Integer index){
        return fileListModel.get(index);
    }

    public DefaultListModel<File> getFileListModel(){
        if(fileListModel == null){
            fileListModel = new DefaultListModel<File>();
        }

        return fileListModel;
    }

    public void removeAllFiles () {

        fileListModel.removeAllElements();
    }


    // ============== UserList Utils =============== //

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
            //TODO: notify the mediator
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

    public DefaultListModel<User> getUserListModel(){
        if(userListModel == null){
            userListModel = new DefaultListModel<User>();
        }

        return userListModel;
    }

}