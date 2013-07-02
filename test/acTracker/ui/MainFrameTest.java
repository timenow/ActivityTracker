package acTracker.ui;

import javax.swing.*;
import acTracker.control.*;

public class MainFrameTest {
    
    public static void main(String[] args) {        
        MainFrame mainFrame = new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Controller controller = Controller.getInstance();
        controller.setFrames(mainFrame, new ImportLogFrame());
        
        mainFrame.setVisible(true);
    }

}
