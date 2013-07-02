package acTracker.model;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import acTracker.entry.*;

public class LogTextParserTest {
    
    LogTextParser parser;
    
    @Before
    public void setUp() {
        Date date = new GregorianCalendar(2013, 6, 16).getTime();
        parser = new LogTextParser(date);
    }

    @Test
    public void testParse01() {
        String logText = 
                "Get up\n" + 
        		"07:00\n" + 
        		"Go to bed\n" + 
        		"23:00";
        List<Activity> activities = parser.parse(logText);
        
        List<Activity> expected = new ArrayList<Activity>();
        expected.add(new GetUpActivity("Get up", 
                                       new GregorianCalendar(2013, 6, 16, 7, 0).getTime()));
        expected.add(new GoToBedActivity("Go to bed", 
                                         new GregorianCalendar(2013, 6, 16, 23, 0).getTime()));
        
        assertEquals(expected, activities);
    }
    
    @Test
    public void testParse02() {String logText = 
                "Get up\n" + 
                "07:00\n" + 
                "\n" + 
                "Wash face, Brush teeth\n" + 
                "07:00 -- 07:15\n" + 
                "\n" + 
                "Eat breakfast\n" + 
                "07:20 -- 07:40\n" + 
                "\n" + 
                "Go to bed\n" + 
                "23:00";
        List<Activity> activities = parser.parse(logText);
        
        List<Activity> expected = new ArrayList<Activity>();
        expected.add(new GetUpActivity("Get up", 
                                       new GregorianCalendar(2013, 6, 16, 7, 0).getTime()));
        expected.add(new Activity("Wash face, Brush teeth", 
                                  new GregorianCalendar(2013, 6, 16, 7, 0).getTime(),
                                  new GregorianCalendar(2013, 6, 16, 7, 15).getTime()));
        expected.add(new Activity("Eat breakfast", 
                                  new GregorianCalendar(2013, 6, 16, 7, 20).getTime(),
                                  new GregorianCalendar(2013, 6, 16, 7, 40).getTime()));
        expected.add(new GoToBedActivity("Go to bed", 
                                         new GregorianCalendar(2013, 6, 16, 23, 0).getTime()));
        
        assertEquals(expected, activities);
    }

}
