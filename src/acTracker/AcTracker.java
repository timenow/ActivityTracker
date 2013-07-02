package acTracker;

import acTracker.control.*;
import acTracker.model.*;
import acTracker.ui.*;

public class AcTracker {
    
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.setFrames(
                new MainFrame(), new ImportLogFrame(), new CompleteLogFrame(),
                new ShowLogFrame());
        controller.setTrackerService(new TrackerServiceImpl());
        controller.start();
    }

}
