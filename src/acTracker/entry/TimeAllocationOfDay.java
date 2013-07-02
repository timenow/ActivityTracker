package acTracker.entry;

import java.util.*;

public class TimeAllocationOfDay {
    
    private Date date;
    private Map<TimeType, Integer> timeAllocation;
    
    public TimeAllocationOfDay(Date date, Map<TimeType, Integer> timeAllocation) {
        this.date = date;
        this.timeAllocation = timeAllocation;
    }
    
    public Date getDate() {
        return date;
    }
    
    public Map<TimeType, Integer> getTimeAllocation() {
        return new HashMap<TimeType, Integer>(timeAllocation);
    }

}
