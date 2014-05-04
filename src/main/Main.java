package main;

import models.*;
import utils.FileListUtils;
import utils.UserListUtils;
import workers.Client;
import workers.FileUpdater;
import workers.Server;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


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

       // addHistoryFiles();

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

    //TODO: delete -- keeping this only for example of how to add files ... or update progress bar
    private void addHistoryFiles(){
        FileModel f = new FileModel("file1", userListUtils.getUserAt(0), userListUtils.getUserAt(1));
        historyFileListUtils.addToFileList(f);
        FileUpdater worker = new FileUpdater(tableModel,f);
        worker.execute();

        FileModel f1 = new FileModel("file2", userListUtils.getUserAt(1), userListUtils.getUserAt(0));
        historyFileListUtils.addToFileList(f1);
        FileUpdater worker1 = new FileUpdater(tableModel,f1);
        worker1.execute();

        FileModel f2 = new FileModel("file3", userListUtils.getUserAt(0), userListUtils.getUserAt(2));
        historyFileListUtils.addToFileList(f2);
        FileUpdater worker2 = new FileUpdater(tableModel,f2);
        worker2.execute();

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
                    System.out.println(usersFilesList.getSelectedValue());
                    Client c = new Client(user.getIP(), user.getPORT(), usersFilesList.getSelectedValue(), tableModel);

                    historyFileListUtils.addToFileList(usersFilesList.getSelectedValue());

                    // asta trebuie mutate acolo unde se adauga -> ma mai uit unde se face asta ??
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

    /**
     * Daca crapa uitate aici s-ar putea sa nu existe fisierele
     * decomenteaza prima parte si comenteaza ce e scris
     *
     * asta trebuie sa se cheme init
     *
     */
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
        this.currentUser = new User("deirdre", IP, PORT);

        Server s = new Server(IP, PORT);
        s.execute();

        while (sc.hasNextLine()){

            User usr = new User(sc.next(), sc.next(), sc.nextInt());
            userListUtils.addToUserList(usr);
            int nrOfFiles = sc.nextInt();

            for (int i = 0; i < nrOfFiles; i++) {

            // Say whaaa ????
                FileModel f = new FileModel(sc.next(), usr, currentUser);
                FileListUtils fileList = new FileListUtils(usr.getHistoryFileListModel());
                fileList.addToFileList(f);

            }
        }

    }

    public static void main (String[] args) {

        if (args.length < 1){
            System.out.println("Please specify the user that starts the app");
            return;
        }
        new Main(args[0]);
    }

}
