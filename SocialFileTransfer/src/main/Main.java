package main;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import utils.FileListUtils;
import utils.UserListUtils;
import workers.FileUpdater;



import models.CustomTableModel;
import models.File;
import models.ProgressCellRenderer;
import models.User;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Main extends JFrame{

    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel rightPanel;
    JPanel statusPanel;

    private JSplitPane splitPanelUpDown;
    private JSplitPane splitPanelRightLeft;

    private static JList<User> usersList;
    private JList<File> usersFilesList = new JList<File>();
    
    
    FileListUtils historyFileListUtils;
    UserListUtils userListUtils;
    

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
        addHistoryFiles();
        
        addFiles(userListUtils.getUserAt(1));
    }


    public Main () {
        super("Social File Transfer");

        setSize(600, 400);
        setResizable(true);

        dataStore.init();
        dataStore = DataStore.getInstance();
        
        historyFileListUtils = new FileListUtils(dataStore.getHistoryFileListModel());
        userListUtils = new UserListUtils();

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
    private void addHistoryFiles(){
        File f = new File("file1", userListUtils.getUserAt(0), userListUtils.getUserAt(1));
        historyFileListUtils.addToFileList(f);
        FileUpdater worker = new FileUpdater(tableModel,f);
        worker.execute();

        File f1 = new File("file2", userListUtils.getUserAt(1), userListUtils.getUserAt(0));
        historyFileListUtils.addToFileList(f1);
        FileUpdater worker1 = new FileUpdater(tableModel,f1);
        worker1.execute();

        File f2 = new File("file3", userListUtils.getUserAt(0), userListUtils.getUserAt(2));  
        historyFileListUtils.addToFileList(f2);
        FileUpdater worker2 = new FileUpdater(tableModel,f2);
        worker2.execute();
    }
    
    //TODO: delete
    private void addFiles(User user){
    	
    	FileListUtils fileListUtils = new FileListUtils(user.getHistoryFileListModel());
    	
        File f = new File("file1", userListUtils.getUserAt(0), userListUtils.getUserAt(1));
        fileListUtils.addToFileList(f);

        File f1 = new File("file2", userListUtils.getUserAt(1), userListUtils.getUserAt(0));
        fileListUtils.addToFileList(f1);

        File f2 = new File("file3", userListUtils.getUserAt(0), userListUtils.getUserAt(2));  
        fileListUtils.addToFileList(f2);
    }
    

    private void refreshUpPanel (User user) {
        usersFilesList = new JList(user.getHistoryFileListModel());   
        upPanel.removeAll();
        upPanel.add(usersFilesList);
        upPanel.revalidate();
        upPanel.repaint();
       // splitPanelUpDown.setTopComponent(upPanel);
    }
    
    private void createUpPanel () {
        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
        upPanel.setPreferredSize(new Dimension(20, 20));
        upPanel.setMinimumSize(new Dimension(60, 100));
        usersFilesList = new JList(new DefaultListModel<File>());
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
        usersList.addMouseListener(new MouseSelectUsers());

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
        userListUtils.addToUserList(currentUser);
        userListUtils.addToUserList(new User("User2"));
        userListUtils.addToUserList(new User("User3"));
    }


    public static void main (String[] args) {
        new Main();
    }


    class MouseSelectUsers extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            int index = usersList.locationToIndex(event.getPoint());
            System.out.println(usersList.getSelectedValue());            
            refreshUpPanel(usersList.getSelectedValue());
        }

    }


}
