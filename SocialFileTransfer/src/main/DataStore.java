package main;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import models.File;
import models.User;


public class DataStore {

    private static DataStore _instance;

    private DefaultListModel<File> fileHistoryListModel;
    private DefaultListModel<User> userListModel;


    public static void init() {
        if (_instance == null) {
            _instance = new DataStore();
        }
    }

    public static DataStore getInstance() {
        return _instance;
    }


    public DefaultListModel<File> getHistoryFileListModel(){
        if(fileHistoryListModel == null){
            fileHistoryListModel = new DefaultListModel<File>();
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