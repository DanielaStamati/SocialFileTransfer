import javax.swing.*;
import java.awt.*;

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

    JTable table;

    public void createLists () {

    }

    public void createTable () {

        String[] columnNames = {"Source", "Destination", "File name", "Progres" ,"Status"};

        Object[][] rowData = {{"_me_", "KPO", "thki", "100%", "Completed"},
                                {"_me_", "KPO", "thki", "100%", "Completed"},
                                {"_me_", "KPO", "thki", "100%", "Completed"}};
        table = new JTable(rowData, columnNames);
    }


    public Main () {
        super("Proiect IDP");

        setSize(600, 400);
        setResizable(true);


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        createUpPanel();
        createDownPanel();
        createRightPanel();

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
        String[] data = {"sdfgd", "dfgdf", "adgas"};
        usersFilesList = new JList(data);
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

        String[] data = {"KPD", "MNE", "IPS"};
        usersList = new JList(data);
        rightPanel.add(usersList);



    }


    public static void main (String[] args) {
        new Main();
    }
}
