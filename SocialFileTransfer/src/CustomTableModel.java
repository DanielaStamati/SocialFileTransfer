import javax.swing.table.AbstractTableModel;

import models.File;


public class CustomTableModel extends AbstractTableModel{
	
	DataStore dataStore;
	String[] columnNames = {"Source", "Destination", "File name", "Progres" ,"Status"};
	
	public CustomTableModel(){
		dataStore = DataStore.getInstance();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return dataStore.getFileListModel().getSize();
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
                name = "Progres";
                break;
            case 4:
                name = "Status";
                break;
        }
        return name;
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		File file = dataStore.getFileAt(rowIndex);
		
		switch (columnIndex) {
	        case 0:
	            return file.from.toString();
	        case 1:
	        	return file.to.toString();
	        case 2:
	        	return file.name;
	        case 3:
	        	return "100%";
	        case 4:
	        	return file.status;
	    }
			
		
		return null;
	}

}