package acTracker.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;
import acTracker.control.*;
import acTracker.entry.*;

public class CompleteLogFrame extends JFrame {

    private static final long serialVersionUID = 2553587683415991580L;
    
    private JPanel activityTablePane;
    private JLabel dateLabel;
    
    private Controller controller = Controller.getInstance();
    private DayActivityLog dayActivitiesInfo;
    private List<Project> projects;
    private List<Domain> domains;
    private List<TimeType> timeTypes;
    
    // Each of the project, domain, or time type combobox relates to a Activity object.
    // Following map keeps the relation data.
    private Map<JComboBox, Activity> comboboxActivityMap = new HashMap<JComboBox, Activity>();

    
    public CompleteLogFrame() {
        init();
    }

    private void init() {
        setTitle("Complete Activity Log");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setupContentPane();
    }

    private void setupContentPane() {
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(new EmptyBorder(20, 20, 20, 20));
        pane.add(createHeaderPane(), BorderLayout.NORTH);
        pane.add(createCenterPane(), BorderLayout.CENTER);
        pane.add(createFooterPane(), BorderLayout.SOUTH);
        
        setContentPane(pane);
    }
    
    private Component createHeaderPane() {
        JPanel pane = new JPanel();
        pane.setBorder(new EmptyBorder(0, 0, 10, 0));
        pane.add(new JLabel("Date: "));
        dateLabel = new JLabel("2013-07-10");
        pane.add(dateLabel);
        
        return pane;
    }

    private Component createCenterPane() {
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        
        initActivityTable();
        pane.add(activityTablePane, BorderLayout.NORTH);
        return pane;
    }
    
    private void initActivityTable() {
        if (activityTablePane == null) {
            activityTablePane = new JPanel();
            activityTablePane.setLayout(new GridLayout(0, 4));
        }
        else {
            activityTablePane.removeAll();
            comboboxActivityMap.clear();    // clear the relation data
        }
        
        JLabel label;
        
        label = new JLabel("Activity");
        label.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        label.setPreferredSize(new Dimension(0, 50));
        activityTablePane.add(label);
        
        label = new JLabel("Project");
        label.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        activityTablePane.add(label);
        
        label = new JLabel("Domain");
        label.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        activityTablePane.add(label);
        
        label = new JLabel("Time Type");
        label.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));
        activityTablePane.add(label);
    }
    
    private Component createFooterPane() {
        JPanel pane = new JPanel();
        JButton submitButton = new JButton("Submit");
        pane.add(submitButton);
        
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.importActivityLog();
            }
        });
        
        return pane;
    }
    
    public void updateView(
            DayActivityLog dayActivitiesInfo, 
            List<Project> projects, List<Domain> domains, List<TimeType> timeTypes) {
        this.dayActivitiesInfo = dayActivitiesInfo;
        this.projects = projects;
        this.domains = domains;
        this.timeTypes = timeTypes;
        initActivityTable();
        
        for (Activity activity : dayActivitiesInfo.getActivities()) {
            addActivityRecord(activity, activityTablePane);
        }
    }

    private void addActivityRecord(Activity activity, Container container) {
        addGeneralInfoColumn(activity, container);
        addProjectInfoPane(activity, container);
        addDomainInfoPane(activity, container);
        addTimeTypeInfoPane(activity, container);
    }

    private void addGeneralInfoColumn(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBackground(p.getBackground());
        textArea.setText(activity.getName() + "\n"
                         + activity.getTimeInfo());
        
        p.add(textArea);
        container.add(p);
    }

    private void addProjectInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        
        p.add(Box.createVerticalGlue());
        
        JComboBox projectsComboBox = new JComboBox();
        projectsComboBox.addItem("---");
        for (Project project : projects)
            projectsComboBox.addItem(project);
        
        p.add(projectsComboBox);
        p.add(Box.createVerticalGlue());
        comboboxActivityMap.put(projectsComboBox, activity);
        container.add(p);
    }

    private void addDomainInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        
        p.add(Box.createVerticalGlue());
        
        JComboBox domainComboBox = new JComboBox();
        domainComboBox.addItem("---");
        for (Domain domain : domains)
            domainComboBox.addItem(domain);
        
        p.add(domainComboBox);
        p.add(Box.createVerticalGlue());
        comboboxActivityMap.put(domainComboBox, activity);
        container.add(p);
    }

    private void addTimeTypeInfoPane(Activity activity, Container container) {
        JPanel p = new JPanel();
        p.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        
        p.add(Box.createVerticalGlue());
        
        JComboBox timeTypesComboBox = new JComboBox();
        timeTypesComboBox.addItem("---");
        for (TimeType timeType : timeTypes)
            timeTypesComboBox.addItem(timeType);
        
        p.add(timeTypesComboBox);
        p.add(Box.createVerticalGlue());
        comboboxActivityMap.put(timeTypesComboBox, activity);
        container.add(p);
    }

    public DayActivityLog getDayActivityLogInfo() {
        for (JComboBox comboBox : comboboxActivityMap.keySet()) {
            Activity activity = comboboxActivityMap.get(comboBox);
            Object selectedItem = comboBox.getSelectedItem();
            
            if (selectedItem instanceof Project)
                activity.addTo((Project)selectedItem);
            else if (selectedItem instanceof Domain)
                activity.addTo((Domain)selectedItem);
            else if (selectedItem instanceof TimeType)
                activity.setTimeType((TimeType)selectedItem);
        }
        
        return dayActivitiesInfo;
    }
    
}
