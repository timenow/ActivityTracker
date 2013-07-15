package acTracker.entry;

import java.io.Serializable;
import java.util.*;

public class UnLoggedActivity extends Activity
        implements Serializable {
    
    private static final long serialVersionUID = 7801558089254368362L;
    
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
    
}
