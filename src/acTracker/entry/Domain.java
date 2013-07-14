package acTracker.entry;

public class Domain {
    
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
