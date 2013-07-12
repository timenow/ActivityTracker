package acTracker.model;

import java.util.*;
import java.text.*;
import acTracker.entry.*;
import acTracker.util.*;
import static acTracker.model.LogParseException.*;

public class LogTextParser {

    private Date date;
    private RawTextRecord previousRecord;
    
    public LogTextParser(Date date) {
        this.date = date;
    }
    
    public List<Activity> parse(String logText) {
        if (logText == null  || logText.trim().isEmpty())
            return null;
        
        List<Activity> activityList = new ArrayList<Activity>();
        LogTextReader logTextReader = new LogTextReader(logText);
        while (logTextReader.hasNextRecord()) {
            RawTextRecord record = logTextReader.readNextRecord();
            activityList.add(parseRecord(record));
            previousRecord = record;
        }
        
        if (activityList.isEmpty())
            return null;
        return activityList;
    }
    
    private Activity parseRecord(RawTextRecord record) {
        checkCompleteOfARecord(record);
        if (! record.isFirst())
            checkBlankLineBetweenRecords(record);
        checkBlankLineBetweenLinesOfARecord(record);
        checkTimeFormat(record);
        
        return parseActivity(date, record);
    }
    
    /** Check completeness of a record  */
    private void checkCompleteOfARecord(RawTextRecord record) {
        if (!record.isTimeInfoLineSet()) {
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.setLines(record.getLines());
            traceInfo.addIndicatorAtLine(record.getNameLineIndex() + 1);
            
            String message = String.format(RECORD_DATA_INCOMPLETE__MISS_TIMEINFO, traceInfo);
            throw new LogParseException(message);
        }
    }
    
    private void checkBlankLineBetweenLinesOfARecord(RawTextRecord record) {
        int nameLineIndex = record.getNameLineIndex();
        int timeInfoLineIndex = record.getTimeInfoLineIndex();
        if (timeInfoLineIndex - nameLineIndex != 1) {
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.setLines(
                    record.getLines().subList(nameLineIndex, timeInfoLineIndex + 1));
            
            int blankLinesCount = timeInfoLineIndex - nameLineIndex - 1;
            for (int i = 0; i < blankLinesCount; i++) {
                traceInfo.addIndicatorAtLine(2 + i);
            }
            
            throw new LogParseException(
                    String.format(UNEXPECTED_BLANK_LINE_AT_TIME_INFO_LINE, traceInfo));
        }
    }
    
    private void checkBlankLineBetweenRecords(RawTextRecord record) {
        if (record.getNameLineIndex() == 0) {
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.addLines(previousRecord.getLines());
            traceInfo.addLines(record.getLines());
            
            // Add two indicators
            int timeInfoLineIndexOfPrevRecord = previousRecord.getTimeInfoLineIndex();
            traceInfo.addIndicatorAtLine(timeInfoLineIndexOfPrevRecord + 1);
            traceInfo.addIndicatorAtLine(timeInfoLineIndexOfPrevRecord + 2);
            
            String message = String.format(MISS_BLANK_LINE_BETWEEN_RECORDS, traceInfo);
            throw new LogParseException(message);
        }
    }
    
    private void checkTimeFormat(RawTextRecord record) {
        // hh:mm, e.g. 06:32, 23:43
        String regexForHourMinuteTime = "[ \t]*([01][0-9]|2[0-3]):[0-5][0-9][ \t]*";
        String matchPattern = 
                String.format("^(%1$s--%1$s)(,.*)?$", regexForHourMinuteTime);
        if (! record.getTimeInfoLine().matches(matchPattern)) {
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.setLines(record.getLines());
            traceInfo.addIndicatorAtLine(record.getTimeInfoLineIndex() + 1);
            
            String message = String.format(INCORRECT_TIME_FORMAT, traceInfo);
            throw new LogParseException(message);
        }
    }
    
    private Activity parseActivity(Date date, RawTextRecord record) {
        String name = record.getNameLine().trim();
        
        List<Date> dateTimeList = parseDateTimeInfo(record);
        Date startTime = dateTimeList.get(0);
        Date stopTime = dateTimeList.get(1);
        
        if (!record.isFirst() && stopTime.before(startTime)) {
            // StopTime is earlier than StartTime for a non-first record
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.setLines(record.getLines());
            traceInfo.addIndicatorAtLine(record.getTimeInfoLineIndex() + 1);
            
            String message = String.format(STOPTIME_EARLIER_THAN_STARTTIME, traceInfo);
            throw new LogParseException(message);
        }
        else if (! record.isFirst()) {
            RawTextRecord currentRecord = record;
            
            Date stopTimeOfPreviousRecord = parseDateTimeInfo(previousRecord).get(1);
            if (!startTime.after(stopTimeOfPreviousRecord)) {
                // StartTime of a record is is not later than stopTime of the previous record
                TraceInfo traceInfo = new TraceInfo();
                traceInfo.addLines(previousRecord.getLines());
                traceInfo.addLines(currentRecord.getLines());
                traceInfo.addIndicatorAtLine(previousRecord.getTimeInfoLineIndex() + 1);
                traceInfo.addIndicatorAtLine(
                        previousRecord.getTimeInfoLineIndex() + 
                          currentRecord.getTimeInfoLineIndex() + 2);
                
                String message = 
                        String.format(STARTTIME_NOT_LATER_THAN_STOPTIME_OF_PREVIOUS_RECORD, 
                                      traceInfo);
                throw new LogParseException(message);
            }
        }
        else if (record.isFirst() && stopTime.before(startTime)) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startTime);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            startTime = calendar.getTime();
        }
        
        return new Activity(name, startTime, stopTime);
    }
    
    private List<Date> parseDateTimeInfo(RawTextRecord record) {
        String[] timeStringArray = record.getTimeInfoLine().split("--");
        Date startTime, stopTime;
        try {
            startTime = parseDateTime(timeStringArray[0].trim());
            stopTime = parseDateTime(timeStringArray[1].trim());
        }
        catch (ParseException e) {
            TraceInfo traceInfo = new TraceInfo();
            traceInfo.setLines(record.getLines());
            traceInfo.addIndicatorAtLine(record.getTimeInfoLineIndex() + 1);
            
            String message = String.format(INCORRECT_TIME_FORMAT, traceInfo);
            throw new LogParseException(message);
        }
        
        List<Date> timeList = new ArrayList<Date>();
        timeList.add(startTime);
        timeList.add(stopTime);
        return timeList;
    }
    
    private Date parseDateTime(String hourMinutePiece) throws ParseException {
        String dateTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(date) 
                             + " " + hourMinutePiece;
        Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateTimeStr);
        return date;
    }
    
}

/**
 * Responsible for reading log text and populating RawTextRecord.
 */
class LogTextReader {
    
    private SourceText sourceText;
    private boolean hasFirstRecordRead = false;
    
    public LogTextReader(String logText) {
        sourceText = new SourceText(logText);
    }
    
    public boolean hasNextRecord() {
        return sourceText.hasNextNonBlankLine();
    }

    public RawTextRecord readNextRecord() {
        if (! hasNextRecord())
            return null;
        
        RawTextRecord record = new RawTextRecord();
        record.setAsFirst(! hasFirstRecordRead);
        
        while (sourceText.hasNextLine()) {
            String line = sourceText.nextLine();
            record.addLine(line);
            
            if (line.trim().isEmpty())
                continue;
            
            if (! record.isNameLineSet()) {
                record.setNameLine(line);
            }
            else if (! record.isTimeInfoLineSet()) {
                record.setTimeInfoLine(line);
                break;      // read completely for a record, stop reading
            }
        }
        
        if (hasFirstRecordRead == false)
            hasFirstRecordRead = true;
        
        return record;
    }
    
}


class RawTextRecord {
    
    private static final int INITIAL_INDEX = -10;
    
    private String nameLine;
    private String timeInfoLine;
    private int nameLineIndex = INITIAL_INDEX;
    private int timeInfoLineIndex = INITIAL_INDEX;
    
    // Store raw-text lines of the record
    private List<String> lines = new ArrayList<String>();
    private boolean isFirst = false;
    

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String line) {
        nameLine = line;
    }
    
    public boolean isNameLineSet() {
        return nameLine != null;
    }
    
    public String getTimeInfoLine() {
        return timeInfoLine;
    }
    
    public void setTimeInfoLine(String line) {
        timeInfoLine = line;
    }
    
    public boolean isTimeInfoLineSet() {
        return timeInfoLine != null;
    }
    
    public List<String> getLines() {
        return new ArrayList<String>(lines);
    }
    
    public void addLine(String line) {
        lines.add(line);
    }
    
    public int getNameLineIndex() {
        if (! isNameLineSet())
            return -1;
        
        if (nameLineIndex == INITIAL_INDEX) {
            nameLineIndex = lines.indexOf(nameLine);
        }
        return nameLineIndex;
    }
    
    public int getTimeInfoLineIndex() {
        if (! isTimeInfoLineSet())
            return -1;
        
        if (timeInfoLineIndex == INITIAL_INDEX) {
            timeInfoLineIndex = lines.lastIndexOf(timeInfoLine);
        }
        return timeInfoLineIndex;
    }

    public boolean isFirst() {
        return isFirst;
    }
    
    void setAsFirst(boolean value) {
        isFirst = value;
    }
    
}


/**
 * A util class to hold the source log text, providing some convinient way 
 * to access the log text.
 *
 */
class SourceText {
    
    private static final int INITIAL_INDEX = -10;
    private String[] lines;
    private int currentLineIndex = -1;
    private int lastNonBlankLineIndex = INITIAL_INDEX;

    public SourceText(String text) {
        lines = text.split("\n");
    }

    public boolean hasNextLine() {
        return currentLineIndex < lines.length - 1;
    }

    public boolean hasNextNonBlankLine() {
        // Calculate lastNonBlankLineIndex first if it is not set yet
        if (lastNonBlankLineIndex == INITIAL_INDEX) {
            for (int i = lines.length - 1; i >= 0; i--) {
                if (! lines[i].trim().isEmpty()) {
                    lastNonBlankLineIndex = i;
                    break;
                }
            }
            if (lastNonBlankLineIndex == -10)
                lastNonBlankLineIndex = -1;
        }
        
        return currentLineIndex < lastNonBlankLineIndex;
    }

    public String nextLine() {
        if (currentLineIndex >= lines.length - 1)
            return null;
        currentLineIndex++;
        return lines[currentLineIndex];
    }
    
    public String nextNonBlankLine() {
        String line = null;
        while(true) {
            line = nextLine();
            if (line == null || !line.trim().isEmpty())
                break;
        }
        
        return line;
    }
    
}
