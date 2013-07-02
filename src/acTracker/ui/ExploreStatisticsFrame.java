package acTracker.ui;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import acTracker.entry.*;

public class ExploreStatisticsFrame extends JFrame {

    private static final long serialVersionUID = 922588192755966335L;
    private JTextArea statisticsTextArea;

    public ExploreStatisticsFrame() {
        init();
    }
    
    private void init() {
        setTitle("Explore Statistics");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(createContentPane());
    }

    private Container createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(15, 20, 15, 20));
        pane.add(createHeaderPane(), BorderLayout.NORTH);
        pane.add(createCenterPane(), BorderLayout.CENTER);
        pane.add(createFooterPane(), BorderLayout.SOUTH);
        return pane;
    }

    private Component createHeaderPane() {
        JPanel pane = new JPanel();
        pane.setBorder(new EmptyBorder(0, 0, 10, 0));
        pane.add(new JLabel("Recent 1-month Statistics"));
        return pane;
    }

    private Component createCenterPane() {
        JScrollPane pane = new JScrollPane();
        statisticsTextArea = new JTextArea();
        statisticsTextArea.setEditable(false);
        statisticsTextArea.setText("");
        statisticsTextArea.setLineWrap(true);
        pane.setViewportView(statisticsTextArea);
        return pane;
    }

    private Component createFooterPane() {
        JPanel panel = new JPanel();
        JButton closeButton = new JButton("Close");
        panel.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExploreStatisticsFrame.this.dispose();
            }
        });
        return panel;
    }

    public void updateView(List<TimeAllocationOfDay> timeAllocationOfRecentDays) {        
        StringBuilder statisticTextBuilder = new StringBuilder();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        
        for (TimeAllocationOfDay timeAllocationOfDay : timeAllocationOfRecentDays) {
            statisticTextBuilder.append(dateFormat.format(timeAllocationOfDay.getDate()));
            statisticTextBuilder.append(" (");
            
            Map<TimeType, Integer> timeAllocation = timeAllocationOfDay.getTimeAllocation();
            for (TimeType timeType : timeAllocation.keySet()) {
                statisticTextBuilder.append(
                        timeType + ": " + timeAllocation.get(timeType) + " min, ");
            }
            
            statisticTextBuilder.append(")");
            statisticTextBuilder.append("\n\n");
        }
        
        statisticsTextArea.setText(statisticTextBuilder.toString());
    }
    
}
