package Utils;

import javax.swing.DefaultListModel;

import main.DataStore;
import models.File;

public class FileListUtils {
	
	DefaultListModel<File> fileListModel = DataStore.getInstance().getFileListModel();
    
    public void addToFileList(File file){
    	
    	if(fileListModel == null){
    		fileListModel = new DefaultListModel<File>();
    	}
    	
    	fileListModel.addElement(file);
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
    	
    	if(fileListModel.size()<=index+1){
    		fileListModel.remove(index);
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public File getFileAt(Integer index){
    	return fileListModel.get(index);
    }

}
