import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import java.awt.*;

import models.File;
import models.User;

/**
 * Created by chelcioi on 3/17/14.
 */
public class Main extends JFrame{

    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel rightPanel;

    private JSplitPane splitPanelUpDown;
    private JSplitPane splitPanelRightLeft;
    
    private JList usersList;
    private JList usersFilesList;
    
    CustomTableModel tableModel;
    
    //TODO: set current user!
    
    private DataStore dataStore;

    JTable table;

    public void createLists () {

    }

    public void createTable () {

    	tableModel = new CustomTableModel(); 
    	table = new JTable(tableModel);
        
    	tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }


    public Main () {
        super("Social File Transfer");

        setSize(600, 400);
        setResizable(true);
        
        dataStore.init();
        dataStore = DataStore.getInstance();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);


        //first, create the user list so that the files have associate owners
        createRightPanel();
        createUpPanel();
        createDownPanel();

        splitPanelRightLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPanelUpDown = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPanelUpDown.setTopComponent(upPanel);
        splitPanelUpDown.setBottomComponent(downPanel);

        splitPanelRightLeft.setRightComponent(rightPanel);
        splitPanelRightLeft.setLeftComponent(splitPanelUpDown);

        topPanel.add(splitPanelRightLeft, BorderLayout.CENTER);


        setVisible(true);
    }


    public void createUpPanel () {

        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
       
        dataStore.addToFileList(new File("file1", dataStore.getUserAt(0), dataStore.getUserAt(1)));
        dataStore.addToFileList(new File("file2", dataStore.getUserAt(1), dataStore.getUserAt(0)));
        dataStore.addToFileList(new File("file3", dataStore.getUserAt(0), dataStore.getUserAt(2)));
       
        usersFilesList = new JList(dataStore.getFileListModel());
        upPanel.add(usersFilesList);

    }

    public void createDownPanel (){

        downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        downPanel.add(scrollPane, BorderLayout.CENTER);


    }

    public void createRightPanel () {
    	
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        
        usersList = new JList(dataStore.getUserListModel());
        
        //TODO: delete this:
        addUsers();
        
        rightPanel.add(usersList);
    }
    
    private void addUsers(){        
    	dataStore.addToUserList(new User("User1"));
    	dataStore.addToUserList(new User("User2"));
    	dataStore.addToUserList(new User("User3"));
    }


    public static void main (String[] args) {
        new Main();
    }
}
