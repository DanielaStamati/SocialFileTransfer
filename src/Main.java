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
        upPanel.add(new JButton("click up"));

    }

    public void createDownPanel (){

        downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        downPanel.add(new JButton("click down"));

    }

    public void createRightPanel () {

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(new JButton("click right"));

    }


    public static void main (String[] args) {
        new Main();
    }
}
