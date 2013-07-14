package acTracker;

import acTracker.control.*;
import acTracker.model.*;

public class AcTracker {
    
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.setTrackerService(new TrackerServiceImpl());
        controller.start();
    }

}
