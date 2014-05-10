package workers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingWorker;

import models.FileModel;
import models.CustomTableModel;

public class FileUpdater extends SwingWorker<FileModel, FileModel> {
	
	private FileModel currentFile;
	private CustomTableModel model;
	
	
	public FileUpdater (CustomTableModel model, FileModel currentFile){
		
		this.model = model;
		this.currentFile = currentFile;
		
		addPropertyChangeListener(new PropertyChangeListener() {	
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
                if (evt.getPropertyName().equals("progress")) {
                	FileUpdater.this.model.updateStatus(FileUpdater.this.currentFile, (Integer) evt.getNewValue());
                }
			}
		});
	}

	@Override
	protected FileModel doInBackground() throws Exception {
		
		for(int i=1;i<=100;i++){
			Thread.sleep(100);
			int progress = i;
            setProgress(progress);			
		}
		
		return currentFile;
		
	}

}
