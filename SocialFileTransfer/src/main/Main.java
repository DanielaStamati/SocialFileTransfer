package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import models.CustomTableModel;
import models.FileModel;
import models.ProgressCellRenderer;
import models.User;
import models.UsersList;
import utils.FileListUtils;
import utils.UserListUtils;
import webservice.GetUsers;
import webservice.RegisterNewUser;
import workers.Client;
import workers.FileUpdater;
import workers.Server;


public class Main extends JFrame{

    private JPanel upPanel;
    private JPanel downPanel;
    private JPanel rightPanel;
    private JPanel statusPanel;

    private JSplitPane splitPanelUpDown;
    private JSplitPane splitPanelRightLeft;

    private static JList<User> usersList;
    private JList<FileModel> usersFilesList = new JList<FileModel>();
    
    
    private FileListUtils historyFileListUtils;
    private UserListUtils userListUtils;

    private CustomTableModel tableModel;
    static User currentUser;

    private DataStore dataStore;
    private JTable table;
    private String username;

    public void createLists () {

    }

    public void createTable () {

        tableModel = new CustomTableModel();
        table = new JTable(tableModel);

    }


    public Main (String username) {

        super("Social FileModel Transfer");

        setSize(600, 400);
        setResizable(true);

        this.username = username;
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


    private void refreshUpPanel (final User user) {

        usersFilesList = new JList(user.getHistoryFileListModel());   
        upPanel.removeAll();
        upPanel.add(usersFilesList);
        upPanel.revalidate();
        upPanel.repaint();
        usersFilesList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                  
                    Client c = new Client(user.getIP(), user.getPORT(), usersFilesList.getSelectedValue(), tableModel);

                    historyFileListUtils.addToFileList(usersFilesList.getSelectedValue());
                    tableModel.fireTableRowsInserted(table.getRowCount() + 1, table.getColumnCount() + 1);

                    c.execute();
                    if (c.isDone())
                        c.cancel(true);
                }
            }
        });
    }
    
    private void createUpPanel () {
        upPanel = new JPanel();
        upPanel.setLayout(new BorderLayout());
        upPanel.setPreferredSize(new Dimension(20, 20));
        upPanel.setMinimumSize(new Dimension(60, 100));
        usersFilesList = new JList(new DefaultListModel<FileModel>());
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

        loadInitialData(username);

        usersList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               refreshUpPanel(usersList.getSelectedValue());
            }
        });

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

    private void loadInitialData(String username){

        File file = new File(username + ".txt");
        FileInputStream in = null;

        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {

            System.out.println("Vezi ca nu exista fisierul deirdre.txt de unde ia port ip lista de useri si fisiere... ");
            e.printStackTrace();
        }

        Scanner sc = new Scanner(in);
        String IP = sc.next();
        int PORT = sc.nextInt();
        ArrayList<String> currentFiles = new ArrayList<String> ();
        this.currentUser = new User(username, IP, PORT, currentFiles);
        
        Server s = new Server(IP, PORT);
        s.execute();

        UsersList users = GetUsers.getRegisteredUsers();
        for (User usr : users.users) {
        	System.out.println(usr.getIP() + usr.getName());
        	userListUtils.addToUserList(usr);
        	for (String files : usr.getFilesNames()) {
        		FileModel f = new FileModel (files, usr, currentUser);
        		FileListUtils fl = new FileListUtils(usr.getHistoryFileListModel());
        		fl.addToFileList(f);
        	}
        }
    }

    public static void main (String[] args) {

        if (args.length < 1){
            System.out.println("Please specify the user that starts the app");
            return;
        }
        
        new Main(args[0]);
        RegisterNewUser.register(new User("mimi", "127.0.0.0", 30000, new ArrayList<String> ()));
        
    }

}
