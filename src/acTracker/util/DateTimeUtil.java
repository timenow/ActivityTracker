package acTracker.util;

import java.util.*;

public class DateTimeUtil {
    
    private static Date today;
    
    public static boolean beforeToday(Calendar dateTime) {
        return beforeToday(dateTime.getTime());
    }
    
    public static boolean beforeToday(Date dateTime) {
        if (today == null) {
            Calendar current = GregorianCalendar.getInstance();
            Calendar calendarToday = new GregorianCalendar(current.get(Calendar.YEAR),
                                          current.get(Calendar.MONDAY),
                                          current.get(Calendar.DAY_OF_MONTH));
            today = calendarToday.getTime();
        }
        
        return dateTime.before(today);
    }

    public static Date today() {
        Calendar current = GregorianCalendar.getInstance();
        Calendar todayCalendar = 
                new GregorianCalendar(current.get(Calendar.YEAR),
                                      current.get(Calendar.MONTH),
                                      current.get(Calendar.DAY_OF_MONTH));
        return todayCalendar.getTime();
    }

}
