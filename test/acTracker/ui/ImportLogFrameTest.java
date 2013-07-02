package acTracker.ui;

import javax.swing.JFrame;
import acTracker.control.*;
import acTracker.model.*;


public class ImportLogFrameTest {

    public static void main(String[] args) {        
        ImportLogFrame importLogFrame = new ImportLogFrame();
        importLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        importLogFrame.setVisible(true);
        
        CompleteLogFrame completeLogFrame = new CompleteLogFrame();
        completeLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Controller controller = Controller.getInstance();
        controller.setFrames(importLogFrame, completeLogFrame);
        controller.setTrackerService(new TrackerServiceImpl());
    }

}
