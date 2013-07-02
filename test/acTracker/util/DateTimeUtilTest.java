package acTracker.util;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.Test;


public class DateTimeUtilTest {

    @Test
    public void testBeforeToday() {
        Calendar current = GregorianCalendar.getInstance();
        assertFalse(DateTimeUtil.beforeToday(current));
        assertFalse(DateTimeUtil.beforeToday(current.getTime()));
        
        current.add(Calendar.DAY_OF_MONTH, -1);
        Calendar yesterday = current;
        assertTrue(DateTimeUtil.beforeToday(yesterday));
        assertTrue(DateTimeUtil.beforeToday(yesterday.getTime()));
    }

}
