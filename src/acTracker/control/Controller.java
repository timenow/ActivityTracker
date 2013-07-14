package acTracker.control;

import java.text.*;
import java.util.*;
import javax.swing.*;
import acTracker.ui.*;
import acTracker.entry.*;
import acTracker.model.*;

public class Controller {
    
    private static Controller instance;
    
    private MainFrame mainFrame;
    private InputLogFrame inputLogFrame;
    private CompleteLogFrame completeLogFrame;
    private ShowLogFrame showLogFrame;
    private TrackerService trackerService;
        
    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }
    
    private Controller() {
    }
    
    public void goToImportActivityLog() {
        inputLogFrame = new InputLogFrame();
        inputLogFrame.setVisible(true);
    }
    
    public void parseActivityLog() {
        String dateStr = inputLogFrame.getDate();
        String logText = inputLogFrame.getActivityLog();
        
        if (dateStr.isEmpty()) {
            JOptionPane.showMessageDialog(inputLogFrame, "Date cannot be empty");
            return;
        }
        if (logText.isEmpty()) {
            JOptionPane.showMessageDialog(inputLogFrame, "Activity log cannot be empty");
            return;
        }
        
        try {
            Date date = parseDate(dateStr);
            DayActivityLog dayActivitiesInfo = trackerService.parseActivityLog(date, logText);
            // hide inputLogFrame and display completeLogFrame
            inputLogFrame.setVisible(false);
            
            completeLogFrame = new CompleteLogFrame();
            completeLogFrame.updateView(dayActivitiesInfo,
                                        trackerService.getProjects(),
                                        trackerService.getDomains(),
                                        trackerService.getTimeTypes());
            completeLogFrame.pack();
            completeLogFrame.setVisible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(inputLogFrame, e.getMessage());
        }
    }
    
    private Date parseDate(String dateStr) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        }
        catch (ParseException e) {
            throw new RuntimeException("Date format incorrect");
        }
        
        return date;
    }
    
    public void importActivityLog() {
        try {
            DayActivityLog dayActivityLog = completeLogFrame.getDayActivityLogInfo();
            trackerService.saveDayActivityLog(dayActivityLog);
            inputLogFrame.dispose();
            completeLogFrame.dispose();
            mainFrame.setVisible(true);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(completeLogFrame, e.getMessage());
        }
    }
    
    public void goToExploreActivityLog() {
        showLogFrame = new ShowLogFrame();
        showLogFrame.setVisible(true);
    }

    public void showActivityLog() {
        try {
            Date date = showLogFrame.getDate();
            DayActivityLog dayActivityLog = trackerService.getDayActivityLog(date);
            if (dayActivityLog == null) {
                JOptionPane.showMessageDialog(
                        showLogFrame, "Activity log not available for this day");
                return;
            }
            
            showLogFrame.updateView(dayActivityLog);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(showLogFrame, e.getMessage());
        }
    }
    
    public void showTimeAllocationStatistics() {
        StatisticsFrame statisticsFrame =  new StatisticsFrame();
        
        List<TimeAllocationOfDay> timeAllocationOfRecentDays = 
                trackerService.getTimeAllocationOfRecentDays(30);
        statisticsFrame.updateView(timeAllocationOfRecentDays);
        
        statisticsFrame.setVisible(true);
    }

    public void setFrames(JFrame... frames) {
        for (JFrame frame : frames) {
            if (mainFrame == null && frame.getClass() == MainFrame.class)
                mainFrame = (MainFrame)frame;
            else if (inputLogFrame == null && frame.getClass() == InputLogFrame.class)
                inputLogFrame = (InputLogFrame)frame;
            else if (completeLogFrame == null && frame.getClass() == CompleteLogFrame.class)
                completeLogFrame = (CompleteLogFrame)frame;
            else if (showLogFrame == null && frame.getClass() == ShowLogFrame.class)
                showLogFrame = (ShowLogFrame)frame;
        }
    }

    public void setTrackerService(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    public void start() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

}
