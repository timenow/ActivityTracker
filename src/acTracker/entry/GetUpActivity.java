package acTracker.entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import acTracker.util.Util;

public class GetUpActivity extends Activity {
    
    public static final String GET_UP = "Get Up";
    
    public GetUpActivity(Date startTime) {
        super(GET_UP, startTime, startTime);
    }
    
    public GetUpActivity(String name, Date startTime) {
        super(name, startTime, startTime);
    }
    
    @Override
    public String getName() {
        String name = super.getName();
        if (name == null)
            name = GET_UP;
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
        if (obj.getClass() != GetUpActivity.class)
            return false;
        
        GetUpActivity that = (GetUpActivity)obj;
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
