package acTracker.ui;

import java.util.*;
import javax.swing.*;
import acTracker.entry.*;
import acTracker.model.*;

public class CompleteLogFrameTest {
    
    public static void main(String[] args) {
        CompleteLogFrame completeLogFrame = new CompleteLogFrame();
        completeLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TrackerService trackerService = new TrackerServiceImpl();
        DayActivityLog dayActivitiesInfo = 
                trackerService.parseActivityLog(getSampleDate(), getSampleLogText());
        
        completeLogFrame.updateView(dayActivitiesInfo);
        completeLogFrame.setVisible(true);
    }
    
    private static Date getSampleDate() {
        return new GregorianCalendar(2013, 5, 23).getTime();
    }

    private static String getSampleLogText() {
        return "Get up\n" + 
                "07:00\n" + 
                "\n" + 
                "Wash face, Brush teeth\n" + 
                "07:00 -- 07:15\n" + 
                "\n" + 
                "Eat breakfast\n" + 
                "07:20 -- 07:40\n" + 
                "\n" + 
                "Go to bed\n" + 
                "23:00";
    }

}
