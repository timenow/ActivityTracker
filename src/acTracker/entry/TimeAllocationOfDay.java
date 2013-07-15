package acTracker.entry;

import java.io.Serializable;
import java.util.*;

public class TimeAllocationOfDay implements Serializable {

    private static final long serialVersionUID = -1233758325939332694L;
    
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
