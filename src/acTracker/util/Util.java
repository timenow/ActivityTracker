package acTracker.util;


public class Util {

    public static int generateHashCode(Object... objects) {
        int multiplier = 41;
        int result = 7;
        for (Object obj : objects) {
            result = result * multiplier + obj.hashCode();
        }
        return result;
    }
    
    

}
