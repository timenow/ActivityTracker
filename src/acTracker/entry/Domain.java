package acTracker.entry;

public class Domain {
    
    private String name;
    private String description;
    
    public Domain(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String name() {
        return name;
    }
    
    public String description() {
        return description;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
