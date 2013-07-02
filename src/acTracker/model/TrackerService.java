package acTracker.model;

import java.util.*;
import acTracker.entry.*;

public interface TrackerService {

    DayActivityLog parseActivityLog(Date date, String logText);

    void saveDayActivityLog(DayActivityLog dayActivityLog);

    DayActivityLog getDayActivityLog(Date date);
    
    /**
     * Calculates time allocation of different time type in the specified day.
     * @return [TimeType -> n minutes]
     */
    Map<TimeType, Integer> calcTimeAllocationOfDay(Date date);

    List<TimeAllocationOfDay> getTimeAllocationOfRecentDays(int days); 

}
