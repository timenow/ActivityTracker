package acTracker.entry;

public class TimeType {
    
    public static final TimeType SLEEP = new TimeType("Sleep");
    public static final TimeType FIXED = new TimeType("Fixed");
    public static final TimeType INVESTMENT = new TimeType("Investment");
    public static final TimeType OTHER = new TimeType("Other");
    
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
