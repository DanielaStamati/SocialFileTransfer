package workers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingWorker;

import sun.java2d.loops.CustomComponent;
import models.CustomTableModel;
import models.File;

public class FileUpdater extends SwingWorker<File, File> {
	
	private File currentFile;
	private CustomTableModel model;
	
	
	public FileUpdater (CustomTableModel model, File currentFile){
		
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
	protected File doInBackground() throws Exception {
		
		for(int i=1;i<=100;i++){
			Thread.sleep(100);
			int progress = i;
            setProgress(progress);			
		}
		
		return currentFile;
		
	}

}
