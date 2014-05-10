package models;

import main.DataStore;
import utils.FileListUtils;

import javax.swing.table.AbstractTableModel;



public class CustomTableModel extends AbstractTableModel{
	
	DataStore dataStore;
	FileListUtils historyFileListUtils;
	String[] columnNames = {"Source", "Destination", "FileModel name", "Progres" ,"Status"};
	
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
                name = "FileModel name";
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
		
		FileModel file = historyFileListUtils.getFileAt(rowIndex);
		
		switch (columnIndex) {
	        case 0:
	            return file.getFrom().toString();
	        case 1:
	        	return file.getTo().toString();
	        case 2:
	        	return file.getName();
	        case 3:
	        	return file.getProgress();
	        case 4:
	        	return file.getStatus();
	    }
			
		
		return null;
	}
	
	
	//it takes the data directly from the DataStore
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		FileModel file = historyFileListUtils.getFileAt(rowIndex);
        if (columnIndex == 3 && (aValue instanceof Float)) {
        	file.setProgress((Float) aValue);
        }
        fireTableRowsInserted(rowIndex, columnIndex);
    }
    
    public void updateStatus(FileModel file, float progress) {
    	//TODO: make this shit work properly 
       if (file != null) {
            int row = dataStore.getHistoryFileListModel().indexOf(file);
            setValueAt(progress, row, 3);
            fireTableCellUpdated(row, 3); 
            
            if(progress == 100){
            	
            	if(file.getStatus().equalsIgnoreCase(StringConstants.sending)){
            		file.setStatus(StringConstants.sent);
            	}else if(file.getStatus().equalsIgnoreCase(StringConstants.receiving)){
            		file.setStatus(StringConstants.received);
            	}
            	
            	fireTableCellUpdated(row, 4); 
            }
       }
    }

}
