package acTracker.ui;

import javax.swing.JFrame;
import acTracker.control.Controller;
import acTracker.model.TrackerServiceImpl;
import acTracker.ui.ShowLogFrame;

public class ShowLogFrameTest {
    
    public static void main(String[] args) {
        ShowLogFrame showLogFrame = new ShowLogFrame();
        showLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Controller controller = Controller.getInstance();
        controller.setFrames(showLogFrame);
        controller.setTrackerService(new TrackerServiceImpl());
        
        showLogFrame.setVisible(true);
    }

}
