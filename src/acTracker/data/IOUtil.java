package acTracker.data;

import java.io.*;


public class IOUtil {

    public static boolean delete(String... filenames) {
        boolean allDeleted = true;
        for (String filename : filenames) {
            if (!new File(filename).delete())
                allDeleted = false;
        }
        
        return allDeleted;
    }

}
