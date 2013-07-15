package acTracker.entry;

import java.io.Serializable;

public class Project implements Serializable {
    
    private static final long serialVersionUID = -3713283335730041843L;
    
    private String name;
    private String description;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != Project.class)
            return false;
        
        Project that = (Project)obj;
        if (this.name.equals(that.name))
            return true;
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
}
