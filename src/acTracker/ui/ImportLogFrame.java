package acTracker.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import acTracker.control.Controller;

public class ImportLogFrame extends JFrame {

    private static final long serialVersionUID = -2965672055517820096L;
    
    protected Controller controller = Controller.getInstance();
    
    private JSpinner.DateEditor timeEditor;
    private JTextArea logTextArea;

    public ImportLogFrame() {
        init();
    }
    
    private void init() {
        setTitle("Import Activity Log");
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(createContentPane());
    }

    private Container createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(createHeaderPane(), BorderLayout.NORTH);
        pane.add(createCenterPane(), BorderLayout.CENTER);
        pane.add(createFooterPane(), BorderLayout.SOUTH);
        pane.setBorder(new EmptyBorder(20, 20, 20, 20));
        return pane;
    }
    
    private JComponent createHeaderPane() {
        JPanel pane = new JPanel(new FlowLayout());
        pane.add(new JLabel("Date:"));
        
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(timeSpinner, "MM-dd");
        timeSpinner.setEditor(timeEditor);
        
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        timeSpinner.setValue(calendar.getTime());
        
        pane.add(timeSpinner);
        pane.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        return pane;
    }
    
    private JComponent createCenterPane() {
        JScrollPane pane = new JScrollPane();
        logTextArea = new JTextArea();
        logTextArea.setEditable(true);
        logTextArea.setLineWrap(true);
        logTextArea.setText(
                "Get up\n" + 
                "07:00\n" + 
                "\n" + 
                "Wash face, Brush teeth\n" + 
                "07:00 -- 07:15\n" + 
                "\n" + 
                "Eat breakfast\n" + 
                "07:20 -- 07:40\n" + 
                "\n" + 
                "Go to bed\n" + 
                "23:00");
        
        pane.setViewportView(logTextArea);
        
        return pane;
    }
    
    private JComponent createFooterPane() {
        JPanel pane = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.parseActivityLog();
                    }
                });
        pane.add(nextButton);
        return pane;
    }

    public String getDate() {
        return timeEditor.getTextField().getText().trim();
    }

    public String getActivityLog() {
        return logTextArea.getText().trim();
    }
    
}
