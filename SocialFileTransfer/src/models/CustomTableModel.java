package models;

import javax.swing.table.AbstractTableModel;

import utils.FileListUtils;
import main.DataStore;



public class CustomTableModel extends AbstractTableModel{
	
	DataStore dataStore;
	FileListUtils historyFileListUtils;
	String[] columnNames = {"Source", "Destination", "File name", "Progres" ,"Status"};
	
	public CustomTableModel(){
		dataStore = DataStore.getInstance();
		historyFileListUtils = new FileListUtils(dataStore.getHistoryFileListModel());
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return dataStore.getHistoryFileListModel().getSize();
	}
	
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "Source";
                break;
            case 1:
                name = "Destination";
                break;
            case 2:
                name = "File name";
                break;
            case 3:
                name = "Progress";
                break;
            case 4:
                name = "Status";
                break;
        }
        return name;
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		File file = historyFileListUtils.getFileAt(rowIndex);
		
		switch (columnIndex) {
	        case 0:
	            return file.from.toString();
	        case 1:
	        	return file.to.toString();
	        case 2:
	        	return file.name;
	        case 3:
	        	return file.progress;
	        case 4:
	        	return file.status;
	    }
			
		
		return null;
	}
	
	
	//it takes the data directly from the DataStore
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		File file = historyFileListUtils.getFileAt(rowIndex);
        if (columnIndex == 3 && (aValue instanceof Float)) {
        	file.progress = (Float) aValue;
        }
    }
    
    public void updateStatus(File file, float progress) {       
    	//TODO: make this shit work properly 
       if (file != null) {
            int row = dataStore.getHistoryFileListModel().indexOf(file);
            setValueAt((float)progress, row, 3);
            fireTableCellUpdated(row, 3); 
            
            if(progress==100){
            	
            	if(file.status.equalsIgnoreCase(StringConstants.sending)){
            		file.status = StringConstants.sent;
            	}else if(file.status.equalsIgnoreCase(StringConstants.receiving)){
            		file.status = StringConstants.received;
            	}
            	
            	fireTableCellUpdated(row, 4); 
            }
       }
    }

}
