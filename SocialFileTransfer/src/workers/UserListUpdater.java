package workers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import utils.FileListUtils;
import utils.UserListUtils;
import main.DataStore;
import models.FileModel;
import models.User;
import models.UsersList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserListUpdater extends SwingWorker<Object,Object>{
	
	public static int in = 0;
	JPanel userPanel;
	User currentUser;
	public static JList<User> usersList;
	
	public UserListUpdater(JPanel userPanel, User currentUser, JList<User> usersList) {
		// TODO Auto-generated method stub
		this.userPanel = userPanel;
		this.currentUser = currentUser;
		this.usersList = usersList;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		System.out.println("================ Running thread =============");
		String prevString = "";
		
		while(true){
		
			Thread.sleep(10000);
			
			String urlString = "http://localhost:8080/WebServer/UserListFetcher";
	        URL url;
	        String jsonString = "";
	        UsersList users = new UsersList(null);
	        
	        
	        try {
				url = new URL(urlString);
				URLConnection conn = url.openConnection();
				conn.connect();
				
				ObjectInputStream o = new ObjectInputStream(conn.getInputStream());
				jsonString = (String) o.readObject();
				ObjectMapper mapper = new ObjectMapper();
				
				users = mapper.readValue(jsonString, UsersList.class);
				System.out.println("Fetched users" + jsonString);
				
				if(!prevString.equalsIgnoreCase(jsonString)){
					prevString = jsonString;
					
					userPanel.removeAll();
					UserListUtils userListUtils = new UserListUtils();
					
					userListUtils.removeAllUsers();
					
					for (User usr : users.users) {
			        	System.out.println("Main " + usr.getIP() + " " + usr.getName() + " " + usr.getPORT() + " " + usr.getFiles());
			        	userListUtils.addToUserList(usr);
			        	for (String files : usr.getFiles()) {
			        		FileModel f = new FileModel (files, usr, currentUser);
			        		FileListUtils fl = new FileListUtils(usr.getHistoryFileListModel());
			        		fl.addToFileList(f);
			        	}
			        }
					
					userPanel.add(usersList);
					
					userPanel.repaint();
					
					//update user listJPanel
				}
				
			} catch (Exception e) {
				System.err.println("error");
				e.printStackTrace();
			}
		}
	}
	
//	 private void refreshUpPanel (final User user) {
//
//	        usersFilesList = new JList(user.getHistoryFileListModel());   
//	        upPanel.removeAll();
//	        upPanel.add(usersFilesList);
//	        upPanel.revalidate();
//	        upPanel.repaint();
//	        usersFilesList.addMouseListener(new MouseAdapter() {
//
//	            @Override
//	            public void mouseClicked(MouseEvent e) {
//	                if (e.getClickCount() == 2) {
//	                  
//	                    Client c = new Client(user.getIP(), user.getPORT(), usersFilesList.getSelectedValue(), tableModel);
//
//	                    historyFileListUtils.addToFileList(usersFilesList.getSelectedValue());
//	                    tableModel.fireTableRowsInserted(table.getRowCount() + 1, table.getColumnCount() + 1);
//
//	                    c.execute();
//	                    if (c.isDone())
//	                        c.cancel(true);
//	                }
//	            }
//	        });
//	    }

}
