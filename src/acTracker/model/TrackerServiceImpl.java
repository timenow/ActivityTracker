package acTracker.model;

import static java.util.Calendar.*;
import java.util.*;
import acTracker.util.*;
import acTracker.data.*;
import acTracker.entry.*;

public class TrackerServiceImpl implements TrackerService {

    private DataContext dataContext = new DataContext();

    @Override
    public DayActivityLog parseActivityLog(Date date, String logText) {
        if (!DateTime.beforeToday(date))
            throw new RuntimeException("Can only import activity log of days before today");
        if (logAvailable(date))
            throw new RuntimeException("Activity log of this day already exists");
        
        // parse logText to a sequence of activities
        List<Activity> activities = new LogTextParser(date).parse(logText);
        
        return new DayActivityLog(date, activities);
    }

    private boolean logAvailable(Date date) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void saveDayActivityLog(DayActivityLog dayActivityLog) {
        dataContext.saveDayActivitiesInfo(dayActivityLog);
    }

    @Override
    public DayActivityLog getDayActivityLog(Date date) {
        return dataContext.getDayActivityLog(date);
    }
    
    @Override
    public Map<TimeType, Integer> calcTimeAllocationOfDay(Date date) {
        DayActivityLog dayActivityLog = dataContext.getDayActivityLog(date);
        Map<TimeType, Integer> timeAllocationInfo = initializeTimeAllocation();
        if (dayActivityLog == null)
            return timeAllocationInfo;
        
        for (Activity activity : dayActivityLog.getActivities()) {
            // TODO: Improve handle of GetUp and GoToBed event, considering to add a new type 
            // TODO: of activities: SleepActivity, and two types of events: GetUpEvent, GoToBedEvent
            // TODO: or setting getUpTime and goToBedTime of each day
            TimeType timeType = activity.getTimeType();
            if (timeType == null) {
                timeType = TimeType.OTHER;
            }
            timeAllocationInfo.put(
                    timeType, timeAllocationInfo.get(timeType) + activity.getDuration());
        }
        return timeAllocationInfo;
    }

    private Map<TimeType, Integer> initializeTimeAllocation() {
        Map<TimeType, Integer> timeAllocation = new HashMap<TimeType, Integer>();
        timeAllocation.put(TimeType.SLEEP, 0);
        timeAllocation.put(TimeType.FIXED, 0);
        timeAllocation.put(TimeType.INVESTMENT, 0);
        timeAllocation.put(TimeType.OTHER, 0);
        return timeAllocation;
    }
    
    @Override
    public List<TimeAllocationOfDay> getTimeAllocationOfRecentDays(int days) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(DateTime.today());
        calendar.add(DAY_OF_YEAR, -days);
        
        List<TimeAllocationOfDay> timeAllocationOfRecentDays =
                new ArrayList<TimeAllocationOfDay>();
        
        for (int i = 0; i < days; i++) {
            Date date = calendar.getTime();
            Map<TimeType, Integer> timeAllocation = calcTimeAllocationOfDay(date);
            timeAllocationOfRecentDays.add(new TimeAllocationOfDay(date, timeAllocation));
            calendar.add(DAY_OF_YEAR, 1);
        }
        
        return timeAllocationOfRecentDays;
    }
    
}
