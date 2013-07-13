package acTracker.ui;

import java.util.*;
import javax.swing.*;
import acTracker.entry.*;

public class StatisticsFrameTest {

    public static void main(String[] args) {
        StatisticsFrame statisticsFrame = new StatisticsFrame();
        statisticsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        List<TimeAllocationOfDay> statisticsData = createSampleStatisticsData(30);
        statisticsFrame.updateView(statisticsData);
        statisticsFrame.setVisible(true);
    }
    
    private static List<TimeAllocationOfDay> createSampleStatisticsData(int days) {
        List<TimeAllocationOfDay> statisticsData = new ArrayList<TimeAllocationOfDay>();
        
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        for (int d = 0; d < days; d++) {
            Date date = calendar.getTime();
            statisticsData.add(new TimeAllocationOfDay(date, createSampleTimeAllocationData()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        return statisticsData;
    }

    private static Map<TimeType, Integer> createSampleTimeAllocationData() {
        Map<TimeType, Integer> timeAllocation = new HashMap<TimeType, Integer>();
        
        Random random = new Random();
        int remainingMinutes = 60 * 24;
        
        TimeType[] timeTypes = { TimeType.SLEEP, TimeType.FIXED, 
                                 TimeType.INVESTMENT, TimeType.OTHER,
                                 TimeType.UNLOGGED };
        for (int i = 0; i < timeTypes.length; i++) {
            if (i == timeTypes.length - 1)
                timeAllocation.put(timeTypes[i], remainingMinutes);
            
            int percentage = random.nextInt(50) + 10;
            int minutes = remainingMinutes * percentage / 100;
            timeAllocation.put(timeTypes[i], minutes);
            remainingMinutes -= minutes;
        }
        
        return timeAllocation;
    }
    
}
