package acTracker.data;

import java.io.*;
import java.util.*;


class KeyFile {

    private Map<Object, EntryData> keys;
    private File file;

    public KeyFile(String filename) throws IOException {
        file = new File(filename);
        if (file.exists())
            load();
        else
            keys = new HashMap<Object, EntryData>();
    }
    
    @SuppressWarnings("unchecked")
    private void load() throws IOException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
        
        try {
            keys = (Map<Object, EntryData>)input.readObject();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            input.close();
        }
    }
    
    public void add(Object key, long position, int length) throws IOException {
        keys.put(key, new EntryData(position, length));
        flush();
    }
    
    public int size() {
        return keys.size();
    }
    
    public boolean containsKey(Object key) {
        return keys.containsKey(key);
    }
    
    public long getPosition(Object key) {
        return keys.get(key).getPosition();
    }
    
    public int getLength(Object key) {
        return keys.get(key).getLength();
    }
    
    private void flush() throws IOException {
        close();
    }
    
    public void close() throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(keys);
        output.close();
    }
    
    
    private static class EntryData implements Serializable {

        private static final long serialVersionUID = 1090146899554615129L;
        
        private long position;
        private int length;
        
        public EntryData(long position, int length) {
            this.position = position;
            this.length = length;
        }
        
        public long getPosition() {
            return position;
        }
        
        public int getLength() {
            return length;
        }
        
    }

}
