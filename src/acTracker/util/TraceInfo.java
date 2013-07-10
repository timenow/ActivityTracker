package acTracker.util;

import java.util.*;

/**
 * This is a convenience class to represent trace info.
 * @author roger
 *
 */
public class TraceInfo {

    private Set<Integer> indicatorLineNumbers = new HashSet<Integer>();
    private List<String> lines = new ArrayList<String>();

    public TraceInfo() {
    }

    public void setLines(String... lines) {
        this.lines = Arrays.asList(lines);
    }
    
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public void addLines(List<String> lines) {
        this.lines.addAll(lines);
    }
    
    public void addLines(String... lines) {
        this.lines.addAll(Arrays.asList(lines));
    }

    public void setIndicatorAtLines(Integer... lineNumbers) {
        indicatorLineNumbers.clear();
        for (Integer lineNumber : lineNumbers)
            indicatorLineNumbers.add(lineNumber);
    }
    
    public void addIndicatorAtLine(int lineNumber) {
        indicatorLineNumbers.add(lineNumber);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int lineNumber = 1;
        
        for (String line : lines) {
            String prefix = (indicatorLineNumbers.contains(lineNumber)) ? "> " : "  "; 
            sb.append(prefix + line);
            
            if (lineNumber != lines.size())
                sb.append("\n");
            lineNumber++;
        }
        
        return sb.toString();
    }

}
