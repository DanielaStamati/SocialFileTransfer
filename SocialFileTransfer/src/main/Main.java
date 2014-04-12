package main;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.TabExpander;

import workers.FileUpdater;

import java.awt.*;

import models.CustomTableModel;
import models.File;
import models.ProgressCellRenderer;
import models.User;


public class Main extends JFrame{

    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel rightPanel;
    JPanel statusPanel;

    private JSplitPane splitPanelUpDown;
    private JSplitPane splitPanelRightLeft;
    
    private JList usersList;
    private JList usersFilesList;
    
    CustomTableModel tableModel;
    
    //TODO: set current user!
    User currentUser;
    
    private DataStore dataStore;

    JTable table;

    public void createLists () {

    }

    public void createTable () {
    	tableModel = new CustomTableModel(); 
    	table = new JTable(tableModel);
    	addFiles();
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
        createStatusBar();
        setVisible(true);
        

    }
    
    
    //TODO: delete
    private void addFiles(){
    	
    	File f = new File("file1", dataStore.getUserAt(0), dataStore.getUserAt(1));
    	dataStore.addToFileList(f);    	
        FileUpdater worker = new FileUpdater(tableModel,f);
        worker.execute();
    	
    	File f1 = new File("file2", dataStore.getUserAt(1), dataStore.getUserAt(0));
        dataStore.addToFileList(f1);
        FileUpdater worker1 = new FileUpdater(tableModel,f1);
        worker1.execute();
        
        File f2 = new File("file3", dataStore.getUserAt(0), dataStore.getUserAt(2));
        dataStore.addToFileList(f2);
        FileUpdater worker2 = new FileUpdater(tableModel,f2);
        worker2.execute();

    }


    private void createUpPanel () {

        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
        
        usersFilesList = new JList(dataStore.getFileListModel());
        upPanel.add(usersFilesList);

    }

    private void createDownPanel (){

        downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        createTable();
        
        table.getColumn("Progress").setCellRenderer(new ProgressCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        downPanel.add(scrollPane, BorderLayout.CENTER);


    }

    private void createRightPanel () {
    	
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        
        usersList = new JList(dataStore.getUserListModel());
        
        //TODO: delete this:
        addUsers();
        
        rightPanel.add(usersList);
    }
    
    public void updateStatusBarLabel(String status){
    	
    	JLabel statusLabel = new JLabel(status);
    	statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	statusPanel.add(statusLabel);
    	
    }
    
    private void createStatusBar(){
    	
    	statusPanel = new JPanel();
    	statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    	this.add(statusPanel, BorderLayout.SOUTH);
    	
    	updateStatusBarLabel("status bar created");
    	
    	statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
    	statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
    	
    	statusPanel.setVisible(true);
    	this.setVisible(true);
    }
    
    private void addUsers(){
    	currentUser = new User("User1"); 
    	dataStore.addToUserList(currentUser);
    	dataStore.addToUserList(new User("User2"));
    	dataStore.addToUserList(new User("User3"));
    }


    public static void main (String[] args) {
        new Main();
    }
}
