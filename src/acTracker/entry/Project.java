package acTracker.entry;

public class Project {
    
    private String name;
    private String description;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String name() {
        return name;
    }
    
    public String description() {
        return description;
    }
    
}
