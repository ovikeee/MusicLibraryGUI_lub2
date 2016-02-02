package NC_Lab1.Util;

import NC_Lab1.Model.Genre;
import NC_Lab1.Model.GenreStorage;
import NC_Lab1.Model.Track;
import NC_Lab1.Model.TrackStorage;

import java.io.*;
import java.util.HashMap;

/**
 * Created by azaz on 28/10/15.
 */
public class FileManager implements Serializable {

    private FileManager instance;// = SingletonHelper.INSTANCE;
    private int clientsCount = 0;
    private GenreStorage genreStorage;
    private TrackStorage trackStorage;
    private String fileName;

    //не синглтон
    public FileManager(String fileName) {//относительный путь
        this.fileName = fileName;
        genreStorage = new GenreStorage();
        trackStorage = new TrackStorage(this);
    }

//    public  FileManager getInstance() {
//        return instance;
//    }
    public String  getFileName(){
    return fileName;
    }
    public void inc() {
        ++clientsCount;
    }

    public void dec() {
        --clientsCount;
    }

    public int getClientsCount() {
        return clientsCount;
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));) {
            trackStorage.setStorage((HashMap<Long, Track>) ois.readObject());
            genreStorage.setStorage((HashMap<String, Genre>) ois.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }//
    }

    public void saveToFile(String filename) {
        System.out.println(filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename + ".muslib")))) {
            oos.writeObject(trackStorage.getStorage());
            oos.writeObject(genreStorage.getStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GenreStorage getGenreStorage() {
        return genreStorage;
    }

    public TrackStorage getTrackStorage() {
        return trackStorage;
    }

    //обеспечивает ленивую инициализацию и синхронизацию
//    private class SingletonHelper {
//        private final FileManager INSTANCE= new FileManager();
//    }
    //обеспечивает правильную десериализацию Синглтона
//    protected Object readResolve() {
//        return getInstance();
//    }
}
