package acTracker.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import acTracker.control.*;

public class MainFrame extends JFrame {
    
    private static final long serialVersionUID = -6388397254055047883L;
    
    private Controller controller = Controller.getInstance();

    public MainFrame() {
        init();
    }

    private void init() {
        setTitle("Activity Tracker");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(createContentPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Container createContentPane() {
        JPanel pane = new JPanel(new GridLayout(0, 2));
        JButton importButton = new JButton("Import Activity Log");
        pane.add(importButton);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.goToImportActivityLog();
            }
        });
        
        JButton exploreLogButton = new JButton("Explore Activity Log");
        pane.add(exploreLogButton);
        exploreLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.goToExploreActivityLog();
            }
        });
        
        JButton showStatisticsButton = new JButton("Explore Statistics");
        pane.add(showStatisticsButton);
        showStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showTimeAllocationStatistics();
            }
        });
        
        JButton setupButton = new JButton("Set Up");
        pane.add(setupButton);
        
        return pane;
    }
    
}
