package acTracker.data;

import java.io.*;


public class DataFile {
    
    public static final String DATA_EXT = ".db";
    public static final String KEY_EXT = ".idx";
    
    private String dataFilename;
    private String keyFilename;
    
    private RandomAccessFile db;
    private KeyFile keys;
    
    public static DataFile create(String filebase) throws IOException {
        return new DataFile(filebase, true);
    }
    
    public static DataFile open(String filebase) throws IOException {
        return new DataFile(filebase, false);
    }
    
    
    private DataFile(String filebase, boolean deleteFiles) throws IOException {
        dataFilename = filebase + DATA_EXT;
        keyFilename = filebase + KEY_EXT;
        
        if (deleteFiles)
            deleteFiles();
        openFiles();
    }
    
    public void deleteFiles() throws IOException {
        IOUtil.delete(dataFilename, keyFilename);
    }
    
    private void openFiles() throws IOException {
        keys = new KeyFile(keyFilename);
        db = new RandomAccessFile(new File(dataFilename), "rw");
    }
    
    public void add(Object key, Object object) throws IOException {
        long position = db.length();
        
        byte[] bytes = getBytes(object);
        db.seek(position);
        db.write(bytes, 0, bytes.length);
        
        keys.add(key, position, bytes.length);
    }
    
    private byte[] getBytes(Object object) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(byteStream);
        output.writeObject(object);
        output.flush();
        
        return byteStream.toByteArray();
    }
    
    public Object findBy(Object key) throws IOException {
        if (!keys.containsKey(key))
            return null;
        
        long position = keys.getPosition(key);
        db.seek(position);
        
        int length = keys.getLength(key);
        return read(length);
    }
    
    private Object read(int length) throws IOException {
        byte[] bytes = new byte[length];
        db.readFully(bytes);
        return readObject(bytes);
    }
    
    private Object readObject(byte[] bytes) throws IOException {
        ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
        try {
            try {
                return input.readObject();
            }
            catch (ClassNotFoundException unlikely) {
                // but write a test for it if you must
                return null;
            }
        }
        finally {
            input.close();
        }
    }
    
    public int size() {
        return keys.size();
    }
    
    public void close() throws IOException {
        keys.close();
        db.close();
    }

}
