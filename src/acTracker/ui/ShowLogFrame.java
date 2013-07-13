package acTracker.ui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JSpinner.DateEditor;
import javax.swing.border.*;
import acTracker.control.*;
import acTracker.entry.*;

public class ShowLogFrame extends JFrame {

    private static final long serialVersionUID = -6974719513658548155L;
    
    protected Controller controller = Controller.getInstance();

    private DateEditor timeEditor;
    private JTextArea logTextArea;

    public ShowLogFrame() {
        init();
    }

    private void init() {
        setTitle("Show Activity Log");
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

    private Component createHeaderPane() {
        JPanel pane = new JPanel();
        pane.add(new JLabel("Select date:"));
        
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeEditor = new JSpinner.DateEditor(timeSpinner, "yyyy-MM-dd");
        timeSpinner.setEditor(timeEditor);
        
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        timeSpinner.setValue(calendar.getTime());
        
        pane.add(timeSpinner);
        
        JButton showButton = new JButton("Show");
        pane.add(showButton);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showActivityLog();
            }
        });
        
        return pane;
    }

    private Component createCenterPane() {
        JScrollPane pane = new JScrollPane();
        pane.setBorder(new EmptyBorder(10, 0, 10, 0));
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setText("");
        pane.setViewportView(logTextArea);
        
        return pane;
    }

    private Component createFooterPane() {
        JPanel pane = new JPanel();
        JButton closeButton = new JButton("Close");
        pane.add(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowLogFrame.this.setVisible(false);
            }
        });
        
        return pane;
    }

    public Date getDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(timeEditor.getTextField().getText());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void updateView(DayActivityLog dayActivityLog) {
        if (dayActivityLog == null) {
            logTextArea.setText("");
            return;
        }
        
        StringBuilder logTextBuilder = new StringBuilder();
        for (Activity activity : dayActivityLog.getActivities()) {
            logTextBuilder.append(activity.getName() + "\n");
            logTextBuilder.append(activity.getTimeInfo() + "\n");
            logTextBuilder.append("\n");
        }
        logTextArea.setText(logTextBuilder.toString());
    }
    
}
