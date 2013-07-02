package acTracker.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
import acTracker.control.Controller;
import acTracker.entry.*;

public class CompleteLogFrame extends JFrame {

    private static final long serialVersionUID = 2553587683415991580L;
    
    private Controller controller = Controller.getInstance();
    private JPanel activitiesPane;
    
    private DayActivityLog dayActivitiesInfo;

    public CompleteLogFrame() {
        init();
    }

    private void init() {
        setTitle("Complete Activity Log");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(createContentPane());
    }

    private Container createContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(20, 20, 20, 20));
        pane.add(createHeaderPane(), BorderLayout.NORTH);
        pane.add(createFooterPane(), BorderLayout.SOUTH);
        
        return pane;
    }

    private Component createHeaderPane() {
        activitiesPane = new JPanel();
        activitiesPane.setLayout(new GridLayout(0, 4));
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Get up"));
        p.add(new JLabel("07:00"));
        activitiesPane.add(p);
        
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Project:"));
        String[] options = new String[] { "", "Learn Markdown", "Read Think in Java", "Complete Activity Tracker" };
        p.add(new JComboBox(options));
        activitiesPane.add(p);
        
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Domain:"));
        options = new String[] { "", "Learn Markdown", "Read Think in Java", "Complete Activity Tracker" };
        p.add(new JComboBox(options));
        activitiesPane.add(p);
        
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Time Type:"));
        options = new String[] { "", "Learn Markdown", "Read Think in Java", "Complete Activity Tracker" };
        p.add(new JComboBox(options));
        activitiesPane.add(p);
        
        return activitiesPane;
    }
    
    private Component createFooterPane() {
        JPanel pane = new JPanel();
        JButton submitButton = new JButton("Submit");
        pane.add(submitButton);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.submitActivityLog();
            }
        });
        
        return pane;
    }
    
    public void updateView(DayActivityLog dayActivitiesInfo) {
        this.dayActivitiesInfo = dayActivitiesInfo;
        
        // Remove original records
        activitiesPane.removeAll();
        
        for (Activity activity : dayActivitiesInfo.getActivities()) {
            addActivityRecord(activity, activitiesPane);
        }
    }

    private void addActivityRecord(Activity activity, Container container) {
        addGeneralInfoColumn(activity, container);
        addProjectInfoPane(activity, container);
        addDomainInfoPane(activity, container);
        addTimeTypeInfoPane(activity, container);
    }

    private void addGeneralInfoColumn(Activity activity, Container container) {
        Panel p = new Panel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel(activity.getName()));
        
        String timeInfo;
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        if (activity.getClass() == GetUpActivity.class 
                || activity.getClass() == GoToBedActivity.class) {
            timeInfo = dateFormat.format(activity.getStartTime());
        }
        else {
            timeInfo = String.format("%s -- %s",
                                     dateFormat.format(activity.getStartTime()),
                                     dateFormat.format(activity.getStopTime()));
        }
        p.add(new JLabel(timeInfo));
        
        container.add(p);
    }

    private void addProjectInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Project"));
        p.add(new JComboBox());
        container.add(p);
    }

    private void addDomainInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Domain"));
        p.add(new JComboBox());
        container.add(p);
    }

    private void addTimeTypeInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(new JLabel("Time Type"));
        TimeType[] timeTypes = 
                new TimeType[] { null, TimeType.SLEEP, TimeType.FIXED, TimeType.INVESTMENT, 
                                 TimeType.OTHER };
        p.add(new JComboBox(timeTypes));
        container.add(p);
    }

    public DayActivityLog getDayActivityLogInfo() {
        return dayActivitiesInfo;
    }
    
}
