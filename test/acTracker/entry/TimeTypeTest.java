package acTracker.entry;

import static org.junit.Assert.*;
import org.junit.Test;


public class TimeTypeTest {
    
    @Test
    public void test() {
        assertEquals("Sleep", TimeType.SLEEP.getName());
        assertEquals("Fixed", TimeType.FIXED.getName());
        assertEquals("Investment", TimeType.INVESTMENT.getName());
        assertEquals("Other", TimeType.OTHER.getName());
    }

}
