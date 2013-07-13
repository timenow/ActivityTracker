package acTracker.ui;

import java.util.*;
import javax.swing.*;
import acTracker.control.Controller;
import acTracker.entry.*;
import acTracker.model.*;
import acTracker.util.DateTime;

public class CompleteLogFrameTest {
    
    public static void main(String[] args) {
        CompleteLogFrame completeLogFrame = new CompleteLogFrame();
        completeLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        completeLogFrame.setResizable(true);
        
        TrackerService trackerService = new TrackerServiceImpl();
        
        Controller controller = Controller.getInstance();
        controller.setTrackerService(trackerService);
        // Register frames in order to prevent null exception when test
        controller.setFrames(new MainFrame(), new InputLogFrame(), completeLogFrame);
        
        DayActivityLog dayActivitiesInfo = 
                trackerService.parseActivityLog(getSampleDate(), getSampleLogText());
        List<Project> projects = getSampleProjects();
        List<Domain> domains = getSampleDomains();
        List<TimeType> timeTypes = getSampleTimeTypes();
        
        completeLogFrame.updateView(dayActivitiesInfo, projects, domains, timeTypes);
        completeLogFrame.pack();
        completeLogFrame.setVisible(true);
    }
    
    private static List<Project> getSampleProjects() {
        List<Project> projects = new ArrayList<Project>();
        projects.add(new Project("Improve Thining Ability", ""));
        projects.add(new Project("Develop Activity Tracker Program", ""));
        projects.add(new Project("Habit Making", ""));
        projects.add(new Project("Learn Agile Java", ""));
        return projects;
    }

    private static List<Domain> getSampleDomains() {
        List<Domain> domains = new ArrayList<Domain>();
        domains.add(new Domain("Programming", ""));
        domains.add(new Domain("Thinking", ""));
        return domains;
    }

    private static List<TimeType> getSampleTimeTypes() {
        List<TimeType> timeTypes = new ArrayList<TimeType>();
        timeTypes.add(TimeType.FIXED);
        timeTypes.add(TimeType.INVESTMENT);
        timeTypes.add(TimeType.SLEEP);
        timeTypes.add(TimeType.OTHER);
        return timeTypes;
    }

    private static Date getSampleDate() {
        return DateTime.yesterday();
    }

    private static String getSampleLogText() {
        return "Sleep\n" + 
        		"23:44 -- 05:40, 5 h 57 min\n" + 
        		"\n" + 
        		"Read How to Read a Book\n" + 
        		"06:03 -- 08:03, 2 h 1 min\n" + 
        		"\n" + 
        		"Walk to Tarena classroom, Eat breakfast\n" + 
        		"08:04 -- 08:33, 30 min\n" +
        		"\n" +
        		"Develop Activity Tracker\n" +
        		"08:45 -- 09:29, 45 min\n" +
        		"\n" +
        		"Attend class\n" +
        		"09:30 -- 12:05, 2 h 36 min\n" +
        		"\n" +
        		"Have lunch\n" +
        		"12:06 -- 12:50, 44 min\n" +
        		"\n" +
        		"Develop Activity Tracker\n" +
        		"12:51 -- 13:50, 1 h\n" +
        		"\n" +
        		"Nap\n" +
        		"13:51 -- 14:00, 10 min\n" +
        		"\n" +
        		"Attend class, Do programming exercises\n" +
        		"14:01 -- 18:40, 4 h 40 min\n";
    }

}
