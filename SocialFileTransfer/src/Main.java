import javax.swing.*;
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

    private DefaultListModel listModel;
    private JList usersList;
    private JList usersFilesList;
    
    //TODO: set current user!
    
    private DataStore dataStore;

    JTable table;

    public void createLists () {

    }

    public void createTable () {

        String[] columnNames = {"Source", "Destination", "File name", "Progres" ,"Status"};

        Object[][] rowData = {{"_me_", "KPO", "file1", "100%", "Completed"},
                                {"_me_", "KPO", "file2", "100%", "Completed"},
                                {"_me_", "KPO", "file3", "100%", "Completed"}};
        table = new JTable(rowData, columnNames);
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
        
        dataStore.addToFileList(new File("file1", dataStore.getUser(0), dataStore.getUser(1)));
        dataStore.addToFileList(new File("file2", dataStore.getUser(1), dataStore.getUser(0)));
        dataStore.addToFileList(new File("file3", dataStore.getUser(0), dataStore.getUser(2)));
        
        usersFilesList = new JList(dataStore.getFileList().toArray());
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
        
        dataStore.addToUserList(new User("User1"));
        dataStore.addToUserList(new User("User2"));
        dataStore.addToUserList(new User("User3"));
        
        usersList = new JList(dataStore.getUserList().toArray());
        rightPanel.add(usersList);
    }


    public static void main (String[] args) {
        new Main();
    }
}
