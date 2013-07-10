package acTracker.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SourceTextTest {

    @Test
    public void testNextLine() {
        String text = 
                "Sleep\n" + 
        		"23:00 -- 06:00, 7 h\n" + 
        		"\n" + 
        		"Brush teeth, wash face\n" + 
        		"06:00 - 06:20, 20 min";
        SourceText sourceText = new SourceText(text);
        
        String[] lines = text.split("\n");
        for (String line : lines) {
            assertEquals(line, sourceText.nextLine());
        }
    }
    
}
