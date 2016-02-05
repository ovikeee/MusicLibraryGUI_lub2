package NC_Lab1.Util;

import NC_Lab1.Model.Genre;
import NC_Lab1.Model.GenreStorage;
import NC_Lab1.Model.Track;
import NC_Lab1.Model.TrackStorage;
import java.io.*;
import java.util.HashMap;

/**
 * Агрегирующий класс для хранения треков и жанров.<br>
 * Имеет следующие приватные поля: <br>
 * clientsCount - количество клиентов одновременно работающих с конкретным
 * объектом данного класса<br>
 * genreStorage - объект класса genreStorage хранящий в себе жанры<br>
 * trackStorage - объект класса trackStorage хранящий в себе треки<br>
 * fileName - имя файла с которым работают клиенты
 */
public class FileManager implements Serializable {

    private int clientsCount = 0;
    private GenreStorage genreStorage;
    private TrackStorage trackStorage;
    private String fileName;

    /**
     * Конструктор.
     *
     * @param fileName имя файла с которым работают клиенты
     */
    public FileManager(String fileName) {//!!!!!!!!!1относительный путь или абсолютный
        this.fileName = fileName;
        genreStorage = new GenreStorage();
        trackStorage = new TrackStorage(this);
    }

    /**
     * Получить имя файла.
     *
     * @return имя файла
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Увеличить количество клиентов на 1.
     */
    public void inc() {
        ++clientsCount;
    }

    /**
     * Уменьшить количество клиентов на 1.
     */
    public void dec() {
        --clientsCount;
    }

    /**
     * Получить количество клиентов.
     *
     * @return количество клиентов
     */
    public int getClientsCount() {
        return clientsCount;
    }

    /**
     * Загрузка фала в поля trackStorage и genreStorage.
     *
     * @param filename название файла
     */
    public void loadFromFile(String filename) {//!!!!!!!!полный путь?
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));) {
            trackStorage.setStorage((HashMap<Long, Track>) ois.readObject());
            genreStorage.setStorage((HashMap<String, Genre>) ois.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранение полей trackStorage и genreStorage в файл с расширением
     * .muslib<br>
     *
     * @param filename название файла
     */
    public void saveToFile(String filename) { //!!!!!!!!!полный путь?
        System.out.println(filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename + ".muslib")))) {
            oos.writeObject(trackStorage.getStorage());
            oos.writeObject(genreStorage.getStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить поле genreStorage
     *
     * @return поле genreStorage
     */
    public GenreStorage getGenreStorage() {
        return genreStorage;
    }

    /**
     * Получить поле trackStorage
     *
     * @return поле trackStorage
     */
    public TrackStorage getTrackStorage() {
        return trackStorage;
    }

//    public  FileManager getInstance() {
//        return instance;
//    }
    //обеспечивает ленивую инициализацию и синхронизацию
//    private class SingletonHelper {
//        private final FileManager INSTANCE= new FileManager();
//    }
    //обеспечивает правильную десериализацию Синглтона
//    protected Object readResolve() {
//        return getInstance();
//    }
}
