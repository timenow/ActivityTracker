package acTracker.data;

import java.util.*;
import acTracker.entry.*;

public class DataContext {
    
    private Map<Date, DayActivityLog> dayActivityLogDepot = 
            new HashMap<Date, DayActivityLog>();
    
    public void saveDayActivitiesInfo(DayActivityLog dayActivitiesInfo) {
        dayActivityLogDepot.put(dayActivitiesInfo.getDate(), dayActivitiesInfo);
    }

    public DayActivityLog getDayActivityLog(Date date) {
        return dayActivityLogDepot.get(date);
    }

}
