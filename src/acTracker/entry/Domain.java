package acTracker.entry;

import java.io.Serializable;

public class Domain implements Serializable {
    
    private static final long serialVersionUID = -7296152156784392763L;
    
    private String name;
    private String description;
    
    public Domain(String name, String description) {
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
        if (obj.getClass() != Domain.class)
            return false;
        
        Domain that = (Domain)obj;
        if (this.name.equals(that.name))
            return true;
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
