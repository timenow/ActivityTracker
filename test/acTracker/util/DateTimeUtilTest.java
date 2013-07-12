package acTracker.util;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;


public class DateTimeUtilTest {

    @Test
    public void testBeforeToday() {
        Calendar current = GregorianCalendar.getInstance();
        assertFalse(DateTime.beforeToday(current));
        assertFalse(DateTime.beforeToday(current.getTime()));
        
        current.add(Calendar.DAY_OF_MONTH, -1);
        Calendar yesterday = current;
        assertTrue(DateTime.beforeToday(yesterday));
        assertTrue(DateTime.beforeToday(yesterday.getTime()));
    }

}
