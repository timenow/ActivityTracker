package acTracker.entry;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class ActivityTest {
    
    private final String name = "Read New York Times";
    private final Date startTime = createTime(10, 15);
    private final Date stopTime = createTime(11, 33);
    // Note that the duration below doesn't equal to the time between stopTime and startTime
    private final int duration = 35;   // 35 minutes
    private Activity activity;
    
    @Before
    public void setUp() {
        activity = new Activity(name, startTime, stopTime, duration);
    }

    @Test
    public void testCreation() {
        assertEquals(name, activity.getName());
        assertEquals(startTime, activity.getStartTime());
        assertEquals(stopTime, activity.getStopTime());
        assertEquals(duration, activity.getDuration());
    }
    
    @Test
    public void testProjectSetup() {
        assertEquals(0, activity.getProjects().size());
        
        Project pa = new Project("Project A", "This is description of project A.");
        Project pb = new Project("Project B", "This is description of project B.");
        activity.addTo(pa);
        activity.addTo(pb);
        
        List<Project> projects = activity.getProjects();
        assertEquals(2, projects.size());
        assertEquals(pa, projects.get(0));
        assertEquals(pb, projects.get(1));
    }
    
    @Test
    public void testDomainSetup() {
        assertEquals(0, activity.getDomains().size());
        
        Domain da = new Domain("Domain A", "Description of domain A.");
        Domain db = new Domain("Domain B", "Description of domain B.");
        activity.addTo(da);
        activity.addTo(db);
        
        List<Domain> domains = activity.getDomains();
        assertEquals(2, domains.size());
        assertEquals(da, domains.get(0));
        assertEquals(db, domains.get(1));
    }
    
    @Test
    public void testTimeTypeSetup() {
        Activity activity1 = new Activity();
        activity1.setTimeType(TimeType.SLEEP);
        
        Activity activity2 = new Activity();
        activity2.setTimeType(TimeType.FIXED);
        
        Activity activity3 = new Activity();
        activity3.setTimeType(TimeType.INVESTMENT);
        
        Activity activity4 = new Activity();
        activity4.setTimeType(TimeType.OTHER);
        
        assertEquals(TimeType.SLEEP, activity1.getTimeType());
        assertEquals(TimeType.FIXED, activity2.getTimeType());
        assertEquals(TimeType.INVESTMENT, activity3.getTimeType());
        assertEquals(TimeType.OTHER, activity4.getTimeType());
    }
    
    @Test
    public void testSubActivity() {
        int durationInMins = calcMinutesOfPeriod(startTime, stopTime);  // 78 minutes
        Activity activity = new Activity("Write diary of 06-21; QQ with Yao",
                                         startTime, stopTime, 
                                         durationInMins);
        assertFalse(activity.hasSubActivities());
        
        List<Activity> subActivities = new ArrayList<Activity>();
        
        // For method setSubActivities(subActivities), if the parameter subActivities contains 
        // only one activity, the single activity will be discarded
        subActivities.add(new Activity("Write diary of 06-21", startTime, stopTime, 45));
        activity.setSubActivities(subActivities);
        assertFalse(activity.hasSubActivities());
        
        subActivities.add(new Activity("QQ with Yao", startTime, stopTime, 33));
        activity.setSubActivities(subActivities);
        assertTrue(activity.hasSubActivities());
        
        assertEquals(subActivities, activity.getSubActivities());
    }
    
    @Test
    public void testSubActivitiesBadWorks() {
        Date startTime = createTime(13, 10);    // 13:10
        Date stopTime = createTime(14, 30);     // 14:40
        int durationInMins = calcMinutesOfPeriod(startTime, stopTime);  // 80 minutes
        
        Activity activity = new Activity("Write diary of 06-21; QQ with Yao",
                                         startTime, stopTime, 
                                         durationInMins);
        
        // StartTime and StopTime of a sub-activity must be same as the parent activity
        
        List<Activity> subActivities = new ArrayList<Activity>();
        subActivities.add(new Activity("Write diary of 06-21", startTime, stopTime, 50));
        subActivities.add(new Activity("QQ with Yao", startTime, createTime(14, 20), 30));
        
        try {
            activity.setSubActivities(subActivities);
            fail("StopTime of a sub-activity must be same as the parent activity");
        }
        catch (RuntimeException success) {
        }
        
        subActivities.remove(1);
        subActivities.add(new Activity("QQ with Yao", createTime(13, 20), stopTime, 30));
        
        try {
            activity.setSubActivities(subActivities);
            fail("StartTime of a sub-activity must be same as the parent activity");
        }
        catch (RuntimeException success) {
        }
        
        
        // Sub-activities should use up all time of the parent activity
        
        subActivities = new ArrayList<Activity>();
        // The two sub-activities only use 70/80 minutes of their parent activity
        subActivities.add(new Activity("Write diary of 06-21", startTime, stopTime, 50));
        subActivities.add(new Activity("QQ with Yao", startTime, stopTime, 20));
        
        try {
            activity.setSubActivities(subActivities);
            fail("Sub-activities should use up all time of the parent activity");
        } catch (RuntimeException success) {
        }
    }

    private Date createTime(int hourOfDay, int minute) {
        return new GregorianCalendar(2013, 5, 20, hourOfDay, minute).getTime();
    }
    
    private int calcMinutesOfPeriod(Date startTime, Date stopTime) {
        return (int)((stopTime.getTime() - startTime.getTime()) / 1000 / 60);
    }
    
}
