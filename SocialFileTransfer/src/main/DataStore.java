package main;import models.FileModel;
import models.User;

import javax.swing.*;


public class DataStore {

    private static DataStore _instance;

    private DefaultListModel<FileModel> fileHistoryListModel;
    private DefaultListModel<User> userListModel;


    public static void init() {
        if (_instance == null) {
            _instance = new DataStore();
        }
    }

    public static DataStore getInstance() {
        return _instance;
    }


    public DefaultListModel<FileModel> getHistoryFileListModel(){
        if(fileHistoryListModel == null){
            fileHistoryListModel = new DefaultListModel<FileModel>();
        }

        return fileHistoryListModel;
    }

    
    public DefaultListModel<User> getUserListModel(){
        if(userListModel == null){
            userListModel = new DefaultListModel<User>();
        }

        return userListModel;
    }

}