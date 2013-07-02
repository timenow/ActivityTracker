package acTracker.entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import acTracker.util.Util;

public class GoToBedActivity extends Activity {
    
    public static final String GO_TO_BED = "Go to Bed";
    
    public GoToBedActivity(Date startTime) {
        super(GO_TO_BED, startTime, startTime);
    }
    
    public GoToBedActivity(String name, Date startTime) {
        super(name, startTime, startTime);
    }
    
    @Override
    public String getName() {
        String name = super.getName();
        if (name == null)
            name = GO_TO_BED;
        return name;
    }
    
    @Override
    public Date getStopTime() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getDuration() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void setStopTime(Date stopTime) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void setDuration(int duration) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != GoToBedActivity.class)
            return false;
        
        GoToBedActivity that = (GoToBedActivity)obj;
        if (this.getName().equals(that.getName())
                && this.getStartTime().equals(that.getStartTime()))
            return true;
        else
            return false;
    }
    
    @Override
    public int hashCode() {
        return Util.generateHashCode(getName(), getStartTime());
    }
    
    @Override
    public String toString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return String.format(
                "[%s%n%s]", getName(), 
                format.format(getStartTime()));
    }

}
