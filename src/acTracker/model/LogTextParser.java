package acTracker.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import acTracker.entry.*;

public class LogTextParser {
    
    private Date date;
    
    public LogTextParser(Date date) {
        this.date = date;
    }

    public List<Activity> parse(String logText) {
        List<Activity> activities = new ArrayList<Activity>();
        RawTextRecords rawTextRecords = new RawTextRecords(logText);
        
        Activity getUpActivity = parseFirstRecord(rawTextRecords.nextRecord());
        activities.add(getUpActivity);
        
        RawTextRecords.Record record;
        boolean lastRecordParsed = false;
        while ((record = rawTextRecords.nextRecord()) != null) {
            Activity activity;
            if (record.isLastRecord()) {
                activity = parseLastRecord(record);
                lastRecordParsed = true;
            }
            else {
                activity = parseNormalRecord(record);
            }
            
            activities.add(activity);
        }
        
        if (!lastRecordParsed) {
            throw new RuntimeException("Last record not found");
        }
        
        return activities;
    }
    
    private Activity parseFirstRecord(RawTextRecords.Record record) {
        return parseFirstOrLastRecord(record, true);
    }
    
    private Activity parseLastRecord(RawTextRecords.Record record) {
        return parseFirstOrLastRecord(record, false);
    }
    
    private Activity parseFirstOrLastRecord(RawTextRecords.Record record, boolean isFirstRecord) {
        String name = record.getNameLine();
        
        Date startTime;
        try {
            startTime = parseDateTime(record.getTimeLine());
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            String message;
            if (isFirstRecord)
                message = "Time format for the first record is incorrect:\n";
            else
                message = "Time format for the last record is incorrect:\n";
                
            throw new RuntimeException(message + "    " + record.getTimeLine(),
                                       e.getCause());
        }
        
        if (isFirstRecord)
            return new GetUpActivity(name, startTime);
        else
            return new GoToBedActivity(name, startTime);
    }
    
    private Activity parseNormalRecord(RawTextRecords.Record record) {
        String name = record.getNameLine();
        String[] timePieces = record.getTimeLine().split("--");
        
        Date startTime, stopTime;
        try {
            startTime = parseDateTime(timePieces[0].trim());
            stopTime = parseDateTime(timePieces[1].trim());
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Time format incorrect:\n"
                                       + "    " + record.getTimeLine(),
                                       e.getCause());
        }
        
        return new Activity(name, startTime, stopTime);
    }
    
    private Date parseDateTime(String hourMinutePiece) {
        String dateTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(date) 
                             + " " + hourMinutePiece;
        Date dateTime;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateTimeStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Time format incorrect:\n"
                                       + "    " + hourMinutePiece,
                                       e);
        }
        
        return dateTime;
    }


    //
    // Inner classes
    // 
    
    private class RawTextRecords {
        
        private SourceText sourceText;
        private Record currentRecord;
        private Record nextRecord;
        
        public RawTextRecords(String logText) {
            sourceText = new SourceText(logText);
        }
        
        public Record nextRecord() {
            if (currentRecord != null && currentRecord.isLastRecord())
                return null;
            
            if (nextRecord == null)
                nextRecord = getNextRecord();
            
            currentRecord = nextRecord;
            nextRecord = getNextRecord();
            currentRecord.setIsLastRecord(nextRecord == null);
            
            return currentRecord;
        }
        
        
        private Record getNextRecord() {
            String nameLine = sourceText.nextNonBlankLine();
            if (nameLine == null)
                return null;
            
            String timeLine = sourceText.nextNonBlankLine();
            if (timeLine == null)
                return null;
            
            Record record = new Record();
            record.setNameLine(nameLine.trim());
            record.setTimeLine(timeLine.trim());
            return record;
        }


        class Record {
            private String nameLine;
            private String timeLine;
            private boolean isLastRecord;

            public String getTimeLine() {
                return timeLine;
            }

            public void setTimeLine(String timeLine) {
                this.timeLine = timeLine;
            }

            public String getNameLine() {
                return nameLine;
            }

            public void setNameLine(String nameLine) {
                this.nameLine = nameLine;
            }
            
            public boolean isLastRecord() {
                return isLastRecord;
            }

            public void setIsLastRecord(boolean isLast) {
                this.isLastRecord = isLast;
            }
        }
        
    }
    
    
    /**
     * This class is used to store the original log text, keeping information about current parsing 
     * position, fetching next line, etc.
     */
    private class SourceText {
        
        private String sourceText;
        private String[] lines;
        private int currentLineIndex = -1;

        public SourceText(String text) {
            sourceText = text;
            lines = sourceText.split(System.getProperty("line.separator"));
        }
        
        public String nextLine() {
            if (currentLineIndex >= lines.length - 1)
                return null;
            
            currentLineIndex++;
            return lines[currentLineIndex];
        }
        
        public String nextNonBlankLine() {
            String line;
            while((line = nextLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                break;
            }
            
            return line;
        }
        
    }

}
