package acTracker.ui;

import javax.swing.*;
import acTracker.control.*;
import acTracker.entry.Domain;
import acTracker.entry.Project;
import acTracker.model.TrackerService;
import acTracker.model.TrackerServiceImpl;

public class MainFrameTest {
    
    public static void main(String[] args) {        
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TrackerService trackerService = new TrackerServiceImpl();
        fillSampleData((TrackerServiceImpl)trackerService);
        
        Controller controller = Controller.getInstance();
        controller.setTrackerService(trackerService);
        controller.setFrames(mainFrame);
        
        mainFrame.setVisible(true);
    }
    
    /**
     * Fill some sample data into the program.
     */
    private static void fillSampleData(TrackerServiceImpl trackerService) {
        // Create a few projects
        trackerService.addProject(
                new Project("Test--Develop Activity Tracker", 
                            "A program to help track my life."));
        trackerService.addProject(
                new Project("Test--Improve Thinking Ability", 
                            "Make my head more powerful and effective"));
        
        // Create a few domains
        trackerService.addDomain(
                new Domain("Test--Programming", ""));
        trackerService.addDomain(
                new Domain("Test--Thinking", ""));
    }

}
