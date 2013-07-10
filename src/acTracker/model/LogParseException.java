package acTracker.model;


public class LogParseException extends RuntimeException {

    private static final long serialVersionUID = -2844868221612997432L;
    
    public static final String FIRST_LINE_CANNOT_BE_BLANK = 
            "First line cannot be blank";
    
    public static final String UNEXPECTED_BLANK_LINE_AT_TIME_INFO_LINE = 
            "Unexpected blank line at time info line:%n" +
            "------------------%n" +
            "%s";
    
    public static final String MISS_BLANK_LINE_BETWEEN_RECORDS = 
            "Miss blank line between records:%n" +
            "------------------%n" +
            "%s";
    
    public static final String INCORRECT_TIME_FORMAT = 
            "Incorrect time format:%n" +
            "------------------%n" +
            "%s";
    
    public static final String STOPTIME_EARLIER_THAN_STARTTIME = 
            "StopTime is earlier than StartTime:\n" +
              "------------------%n" +
              "%s";
    
    public static final String STARTTIME_NOT_LATER_THAN_STOPTIME_OF_PREVIOUS_RECORD =
                    "StartTime is not later than StopTime of previous record:\n" +
                    "------------------%n" +
                    "%s";

    public LogParseException() {
    }

    public LogParseException(String message) {
        super(message);
    }

    public LogParseException(Throwable cause) {
        super(cause);
    }

    public LogParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
