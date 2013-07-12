package acTracker.ui;

import javax.swing.JFrame;
import acTracker.control.*;
import acTracker.model.*;


public class InputLogFrameTest {

    public static void main(String[] args) {        
        InputLogFrame inputLogFrame = new InputLogFrame();
        inputLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputLogFrame.setVisible(true);
        
        CompleteLogFrame completeLogFrame = new CompleteLogFrame();
        completeLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Controller controller = Controller.getInstance();
        controller.setFrames(inputLogFrame, completeLogFrame);
        controller.setTrackerService(new TrackerServiceImpl());
    }

}
