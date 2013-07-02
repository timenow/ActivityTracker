package acTracker.entry;

import java.util.*;

public class DayActivityLog {
    
    private Date date;
    private List<Activity> activities;

    public DayActivityLog(Date date, List<Activity> activities) {
        this.date = date;
        this.activities = activities;
    }
    
    public Date getDate() {
        return date;
    }
    
    public List<Activity> getActivities() {
        return new ArrayList<Activity>(activities);
    }
    
    @Override
    public String toString() {
        return String.format("%s%n%s", date, activities);
    }

}
