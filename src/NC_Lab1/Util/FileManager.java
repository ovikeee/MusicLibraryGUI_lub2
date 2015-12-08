package NC_Lab1.Util;

import NC_Lab1.Model.GenreStorage;
import NC_Lab1.Model.TrackStorage;

import java.io.*;

/**
 * Created by azaz on 28/10/15.
 */
public class FileManager implements Serializable {

    private static FileManager instance = SingletonHelper.INSTANCE;
    private GenreStorage genreStorage;
    private TrackStorage trackStorage;
    private IdGenerator idGenerator;

    private FileManager() {
        genreStorage = GenreStorage.getInstance();
        trackStorage = TrackStorage.getInstance();
        idGenerator = IdGenerator.getInstance();
    }

    public static FileManager getInstance() {
        return instance;
    }

    public static void loadFromFile(String filename) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));) {
            instance = (FileManager) ois.readObject();
           // GenreStorage.loadFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)))) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //обеспечивает ленивую инициализацию и синхронизацию
    private static class SingletonHelper {

        private static final FileManager INSTANCE= new FileManager();
    }

    //обеспечивает правильную десериализацию Синглтона
    protected Object readResolve() {
        return getInstance();
    }

}
