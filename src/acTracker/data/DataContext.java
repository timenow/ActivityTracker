package acTracker.data;

import java.util.*;
import acTracker.entry.*;

public class DataContext {
    
    private Map<Date, DayActivityLog> dayActivityLogDepot = 
            new HashMap<Date, DayActivityLog>();
    private List<Project> projects = new ArrayList<Project>();
    private List<Domain> domains = new ArrayList<Domain>();
    
    public void saveDayActivitiesInfo(DayActivityLog dayActivitiesInfo) {
        dayActivityLogDepot.put(dayActivitiesInfo.getDate(), dayActivitiesInfo);
    }

    public DayActivityLog getDayActivityLog(Date date) {
        return dayActivityLogDepot.get(date);
    }
    
    public List<Project> getProjects() {
        return new ArrayList<Project>(projects);
    }
    
    public List<Domain> getDomains() {
        return new ArrayList<Domain>(domains);
    }

    public void addProject(Project project) {
        projects.add(project);
    }
    
    public void addDomain(Domain domain) {
        domains.add(domain);
    }

}
