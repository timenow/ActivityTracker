package acTracker.entry;

import java.io.Serializable;


public class TimeType implements Serializable {
    
    private static final long serialVersionUID = -6006906402458444027L;
    
    public static final TimeType SLEEP = new TimeType("Sleep");
    public static final TimeType FIXED = new TimeType("Fixed");
    public static final TimeType INVESTMENT = new TimeType("Investment");
    public static final TimeType OTHER = new TimeType("Other");
    public static final TimeType UNLOGGED = new TimeType("Unlogged");
    
    private String name;
    
    private TimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
