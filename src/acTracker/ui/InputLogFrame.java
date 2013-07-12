package acTracker.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import acTracker.control.Controller;
import acTracker.util.DateTime;

public class InputLogFrame extends JFrame {

    private static final long serialVersionUID = -2965672055517820096L;
    
    protected Controller controller = Controller.getInstance();
    
    private JSpinner.DateEditor timeEditor;
    private JTextArea logTextArea;

    public InputLogFrame() {
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
        pane.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        pane.add(new JLabel("Date:"));
        
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy-MM-dd");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(DateTime.yesterday());
        pane.add(timeSpinner);
        
        return pane;
    }
    
    private JComponent createCenterPane() {
        JScrollPane pane = new JScrollPane();
        logTextArea = new JTextArea();
        logTextArea.setEditable(true);
        logTextArea.setLineWrap(true);
        logTextArea.setText(
                "Sleep\n" +
                "23:44 -- 05:40, 5 h 57 min\n" +
                "\n" +
                "Read How to Read a Book\n" +
                "06:03 -- 08:03, 2 h 1 min\n" +
                "\n" +
                "Walk to Tarena classroom, Eat breakfast\n" +
                "08:04 -- 08:33, 30 min\n");
        
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
