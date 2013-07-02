package acTracker.entry;

import java.util.*;

public class UnLoggedActivity extends Activity {
    
    public static final String UN_LOGGED = "Unlogged";
    
    public UnLoggedActivity(Date startTime, Date stopTime) {
        super(UN_LOGGED, startTime, stopTime);
    }
    
    public UnLoggedActivity(String name, Date startTime, Date stopTime) {
        super(name, startTime, stopTime);
    }
    
    @Override
    public String getName() {
        String name = super.getName();
        if (name == null)
            name = UN_LOGGED;
        return name;
    }
    
    @Override
    public void setDuration(int duration) {
        throw new UnsupportedOperationException("Cannot modify duration of an unlogged activity");
    }
    
}
