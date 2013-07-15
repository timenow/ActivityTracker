package acTracker.entry;

import java.io.Serializable;
import java.util.*;

public class DayActivityLog implements Serializable {
    
    private static final long serialVersionUID = -6108204433714708949L;
    
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
