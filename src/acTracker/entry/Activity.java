package acTracker.entry;

import java.text.*;
import java.util.*;
import acTracker.util.*;


public class Activity {

    private String name;
    private Date startTime;
    private Date stopTime;
    private int duration;   // in minutes
    private List<Project> projects = new ArrayList<Project>();
    private List<Domain> domains = new ArrayList<Domain>();
    private TimeType timeType;
    private List<Activity> subActivities;

    public Activity() {
    }
    
    /**
     * Duration will set to time of period from startTime to stopTime.
     */
    public Activity(String name, Date startTime, Date stopTime) {
        this.name = name;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.duration = (int)((stopTime.getTime() - startTime.getTime()) / 1000 / 60) + 1;
    }
    
    public Activity(String name, Date startTime, Date stopTime, int duration) {
        this.name = name;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public int getDuration() {
        return duration;
    }

    public List<Project> getProjects() {
        return new ArrayList<Project>(projects);
    }
    
    public List<Domain> getDomains() {
        return new ArrayList<Domain>(domains);
    }

    public void addTo(Project project) {
        if (projects == null)
            projects = new ArrayList<Project>();
        projects.add(project);
    }

    public void addTo(Domain domain) {
        if (domains == null)
            domains = new ArrayList<Domain>();
        domains.add(domain);
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public boolean hasSubActivities() {
        if (subActivities == null || subActivities.isEmpty())
            return false;
        return true;
    }

    public void setSubActivities(List<Activity> subActivities) {
        if (subActivities == null || subActivities.isEmpty() || subActivities.size() == 1)
            return;
        
        int totalMinutes = 0;
        for (Activity activity : subActivities) {
            if (activity.getStartTime() != this.getStartTime() || activity.getStopTime() != this.getStopTime())
                throw new RuntimeException(
                        "StartTime and StopTime of a sub-activity must be same as the parent activity");
            
            totalMinutes += activity.getDuration();
        }
        
        if (totalMinutes != this.getDuration())
            throw new RuntimeException(
                    "Sub-activities should use up all time of the parent activity");
        
        this.subActivities = subActivities;
    }

    public List<Activity> getSubActivities() {
        return new ArrayList<Activity>(subActivities);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != Activity.class)
            return false;
        
        Activity that = (Activity)obj;
        if (this.name.equals(that.name)
                && this.startTime.equals(that.startTime)
                && this.stopTime.equals(that.stopTime)
                && this.duration == that.duration)
            return true;
        else
            return false;
        
    }
    
    @Override
    public int hashCode() {
        return Util.generateHashCode(name, startTime, stopTime, duration);
    }
    
    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return String.format(
                "[%s%n%s -- %s, %s min]", name, 
                format.format(startTime), format.format(stopTime), duration);
    }

}
