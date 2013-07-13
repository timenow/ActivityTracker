package acTracker.model;

import static org.junit.Assert.*;
import static acTracker.model.LogParseException.*;
import java.util.*;
import org.junit.*;
import acTracker.entry.Activity;


public class LogTextParserTest {
    
    private LogTextParser parser;
    private final int year = 2013;
    private final int month = 6;
    private final int dayOfMonth = 16;
    
    @Before
    public void setUp() {
        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();
        parser = new LogTextParser(date);
    }
    
    @Test
    public void testIncompletenessOfARecord() {
        String logText = "Eat breakfast\n" + 
                "08:00 -- 08:20, 20 min\n" +
                "\n" + 
                "Develop ActivityTracker\n" +
                "\n";

        try {
            parser.parse(logText);
            fail("Should raise a LogParseException when a record is incomplete.");
        }
        catch (LogParseException e) {
            String expectedMessage = 
                    String.format(RECORD_DATA_INCOMPLETE__MISS_TIMEINFO, 
                                  "  \n" +
                                    "> Develop ActivityTracker");
            assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testBlankLineBetweenNameAndTimeInfoLines() {
        String logText = "Eat breakfast\n" + 
                            "08:00 -- 08:20, 20 min\n" +
                            "\n" + 
                            "Develop ActivityTracker\n" +
                            "  \n" + 
                            "09:00 -- 11:00, 2 h";
        
        try {
            parser.parse(logText);
            fail("LogParseException expected. " +
            		"Need to check the absence of blank line between the two lines of a record.");
        } catch (LogParseException e) {
            String expectedMessage = 
                    String.format(UNEXPECTED_BLANK_LINE_AT_TIME_INFO_LINE, 
                                    "  Develop ActivityTracker\n"
                                  + ">   \n"
                                  + "  09:00 -- 11:00, 2 h");
            assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testMissBlankLineBetweenRecords() {
        String logText = 
                "Eat breakfast\n" + 
        		"08:00 -- 08:20, 20 min\n" + 
        		"Develop ActivityTracker\n" + 
        		"09:00 -- 11:00, 2 h";
        
        try {
            parser.parse(logText);
            fail("Expect to raise a LogParseException when "
                    + "there's no blank line between two records.");
        } catch (LogParseException e) {
            String expectedMessage = 
                    String.format(MISS_BLANK_LINE_BETWEEN_RECORDS, 
                                  "  Eat breakfast\n" + 
                                    "> 08:00 -- 08:20, 20 min\n" + 
                                    "> Develop ActivityTracker\n" + 
                                    "  09:00 -- 11:00, 2 h");
            assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testIncorrectTimeFormat() {
        String[] incorrectTimeFormats = {
                "09:0b -- 11:00",   // letter in time
                "09:12",            // time info incomplete
                "9:10 -- 11:10",    // hour and minute should be always in 2-character width
                "25:10 -- 11:10",   // hour exceeds the allowed range
                "09:10:10 -- 10:10",    // only need to specify hour and minute
                "09:00 11:00",      // miss separator "--" between startTime and stopTime
        };
        
        for (String timeInfo : incorrectTimeFormats) {
            String logText = "Develop ActivityTracker\n"
                              + timeInfo;
            try {
                parser.parse(logText);
                fail("Should raise a LogParseException when time format incorrect.");
            } catch (LogParseException e) {
                String expectedMessage = 
                        String.format(INCORRECT_TIME_FORMAT, 
                                      "  Develop ActivityTracker\n" +
                                        "> " + timeInfo);
                assertEquals(expectedMessage, e.getMessage());
            }
        }
    }
    
    @Test
    /** Besides the first record, stopTime cannot be earlier than startTime. */
    public void testTimeConstraint01() {
        String logText = 
                "Eat breakfast\n" + 
                "08:00 -- 08:20, 20 min\n" +
                "\n" + 
                "Develop ActivityTracker\n" + 
                "11:22 -- 09:00, 2 h";
        try {
            parser.parse(logText);
            fail("For non-first records, need to raise a LogParseException when " +
            		"stopTime is earlier than startTime for non-first records.");
        }
        catch (LogParseException e) {
            String expectedMessage = 
                    String.format(STOPTIME_EARLIER_THAN_STARTTIME, 
                                  "  \n" +
                                    "  Develop ActivityTracker\n" +
                                    "> 11:22 -- 09:00, 2 h");
            assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    /** StartTime of a record should be later than StopTime of the previous record. */
    public void testTimeConstraint02() {
        String logText = 
                "Eat breakfast\n" + 
                "08:00 -- 08:30, 30 min\n" +
                "\n" + 
                "Develop ActivityTracker\n" + 
                "08:20 -- 10:00, 1 h 40 min";
        try {
            parser.parse(logText);
            fail("Should raise a LogParseException when " +
            		"startTime of a record is is not later than stopTime of the previous record.");
        } catch (LogParseException e) {
            String expectedMessage = 
                    String.format(STARTTIME_NOT_LATER_THAN_STOPTIME_OF_PREVIOUS_RECORD,
                                  "  Eat breakfast\n" + 
                            		"> 08:00 -- 08:30, 30 min\n" + 
                            		"  \n" + 
                            		"  Develop ActivityTracker\n" + 
                            		"> 08:20 -- 10:00, 1 h 40 min");
            assertEquals(expectedMessage, e.getMessage());
        }
    }
    
    @Test
    public void testTimeInfoForFirstRecord() {
        String logText = 
                "Sleep\n" + 
                "23:20 -- 07:00, 7 h 40 min\n" +
                "\n" + 
                "Eat breakfast\n" + 
                "08:00 -- 08:30, 30 min";
        List<Activity> activities = parser.parse(logText);
        
        List<Activity> expected = new ArrayList<Activity>();
        expected.add(
                new Activity("Sleep",
                             getDateTime(dayOfMonth - 1, 23, 20), getDateTime(7, 0),
                             461));
        expected.add(
                new Activity("Eat breakfast",
                             getDateTime(8, 0), getDateTime(8, 30),
                             31));
        assertEquals(expected, activities);
    }

    @Test
    public void testSuccessfulParse() {
        String logText = 
                "\n" + 
                "阅读《如何阅读一本书》\n" + 
                "07:00-- 07:50, 51 min\n" + 
                "\n" + 
                "走去达内教室，吃早餐\n" + 
                "07:51 -- 08:12,  22 min\n" + 
                "\n" + 
                "阅读《如何阅读一本书》\n" + 
                "18:22 -- 19:09, 48 min\n" + 
                "\n" + 
                "";
        List<Activity> activities = parser.parse(logText);
        
        List<Activity> expectedActivities = new ArrayList<Activity>();
        expectedActivities.add(
                new Activity("阅读《如何阅读一本书》",
                             getDateTime(7, 0), getDateTime(7, 50), 51));
        expectedActivities.add(
                new Activity("走去达内教室，吃早餐",
                             getDateTime(7, 51), getDateTime(8, 12), 22));
        expectedActivities.add(
                new Activity("阅读《如何阅读一本书》",
                             getDateTime(18, 22), getDateTime(19, 9), 48));
        
        assertEquals(expectedActivities, activities);
    }
    
    private Date getDateTime(int hour, int minute) {
        return new GregorianCalendar(year, month, dayOfMonth, hour, minute).getTime();
    }
    
    private Date getDateTime(int dayOfMonth, int hour, int minute) {
        return new GregorianCalendar(year, month, dayOfMonth, hour, minute).getTime();
    }

}
