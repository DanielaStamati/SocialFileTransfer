package utils;

import models.FileModel;

import javax.swing.*;
import java.util.Iterator;

public class FileListUtils implements Iterable<FileModel> {
	
	DefaultListModel<FileModel> fileListModel;
	
	public FileListUtils(DefaultListModel<FileModel> fileListModel) {
		this.fileListModel = fileListModel;
	}
    
    public void addToFileList(FileModel file){
    	
    	if(fileListModel == null){
    		fileListModel = new DefaultListModel<FileModel>();
    	}
    	
    	fileListModel.addElement(file);
    }
    
    //removes if it exists
    public boolean removeFromFileList(FileModel file){
    	
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
    
    public FileModel getFileAt(Integer index){
    	return fileListModel.get(index);
    }
    
    public void removeAllFiles () {
        fileListModel.removeAllElements();
    }

    @Override
    public Iterator<FileModel> iterator() {
        Iterator<FileModel> it = new Iterator<FileModel>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < fileListModel.size() && fileListModel.get(currentIndex) != null;
            }

            @Override
            public FileModel next() {
                return fileListModel.get(currentIndex++);
            }

            @Override
            public void remove() {

            }
        };
        return it;
    }

}
