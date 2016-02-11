package NC_Lab1.controller;

import NC_Lab1.Model.Genre;
import NC_Lab1.Model.GenreStorage;
import NC_Lab1.Model.Track;
import NC_Lab1.Model.TrackComparatorById;
import NC_Lab1.Model.TrackStorage;
import NC_Lab1.Util.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by ovikeee on 01.11.2015
 */
public class ServerController {

    // private static ServerController controller;// = SingletonHelper.INSTANCE;
    private FileManager fileManager;

    /**
     * При создании контроллера инициализируются поля:
     * fileManager.getTrackStorage() fileManager.getGenreStorage()
     *
     * @param fileManager
     */
    public ServerController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setFileManager(FileManager newFileManager) {
        fileManager = newFileManager;
    }

//    public static ServerController getInstance() {
//        //TrackStorage.loadFromFile("defaultFile");
//        return controller;
//    }
    //требуется генерация Id
    /**
     * Добавление трека или жанра param i
     *
     * @param str массив с параметрами для добавления: addTrack( String name,
     * String albumName, String artist,long length, String genre)
     * @return 1-если трек добавлен
     * -1- если трек уже существует
     */
    public int addTrack(ArrayList<String> str) {
       return fileManager.getTrackStorage().addTrack(str.get(0), str.get(1), str.get(2), Long.parseLong((String) str.get(3)), str.get(4));
    }

    public void addGenre(String str) {
        fileManager.getGenreStorage().addGenre(str);
    }

    public void removeTrackById(long id) {
        fileManager.getTrackStorage().removeTrackById(id);
    }

    public void removeAllTracks() {

        fileManager.getTrackStorage().removeAll();
    }

    public void removeAllGenres() {

        fileManager.getGenreStorage().removeAll();
    }

    public void removeGenreByTitle(String name) {
        fileManager.getGenreStorage().removeGenreByTitle(name);
    }

//Добавлен функционал поиск треков по жанрам(нужно протестить работу)
    /**
     * @param id номер операции: 1-поиск по номеру трека 2-поиск по названию
     * трека 3-поиск по исполнителю 4-поиск по альбому 5-поиск по жанру 6-поиск
     * всех треков
     *
     * @return возвращаем ArrayList с результатами поиска
     */
    public String findTrackById(Long id) {
        /*поиск по номеру трека*/
        if (fileManager.getTrackStorage().getById(id) == null) {
            return null;
        }
        return fileManager.getTrackStorage().getById(id).toString();
    }

    public ArrayList<String> findTrackByTitle(String name) {
        /*поиск по всех треков с похожим названием*/
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getByTitle(name));
        tracks.sort(new TrackComparatorById());
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByArtist(String name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getByArtist(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByAlbum(String name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getByAlbum(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByLength(Long name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getByLength(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByGenre(String genre) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getByGenre(genre));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findAllTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(fileManager.getTrackStorage().getAllTracks());
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    /**
     * обновляет трек по Id
     *
     * @param str все параметры трека long id, String name, String albumName,
     * String artist,long length, String genre)
     */
    public void updateTrack(ArrayList<String> str) {
        if (str.size() != 6) {
            throw new NoSuchElementException();
        }
        fileManager.getTrackStorage().setAllParam(str);
    }

    public void updateGenre(ArrayList<String> param) {
        if (param.size() != 2) {
            throw new NoSuchElementException();
        }
        fileManager.getGenreStorage().getByTitle(param.get(0)).setName(param.get(1));
    }

    /**
     * Возвращает название трека, если он есть. Иначе null. //!!!!!!! можно
     * возвращать true или false
     *
     * @param name
     * @return возвращается название введеного жанра, если жанр найден иначе
     * возвращается null
     */
    public String getGenreByTitle(String name) {
        return (fileManager.getGenreStorage().isGenre(name)) ? name : null;
    }

    /**
     * Метод, ищет совпадение в названии жанра с введеным шаблоном. Если
     * найдется совпадение с шаблоном, то этот жанр добавляется в массив.
     * 
     * @param regexp шаблон для поиска
     * @return массив жанров, у которые совпали с введеным шаблоном
     */
    public ArrayList<String> findGenreByTitle(String regexp) {
        ArrayList<String> result = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<String, Genre> stringGenreEntry : fileManager.getGenreStorage().getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getKey());
            if (m.find()) {
                result.add(stringGenreEntry.getKey());
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public ArrayList<String> findAllGenre() {
        return fileManager.getGenreStorage().getAllGenre();
    }

    //требуется исправление на FileManeger
    /**
     * Загрузка треков
     *
     * @param str
     */
    public void load(String str) {
        //TrackStorage.loadFromFile(str + ".track");
        //fileManager.getGenreStorage().loadFromFile(str + ".genre");
        fileManager.loadFromFile(str);
    }

    //требуется исправление на FileManeger
    /**
     * Сохранение
     *
     * @param str имя файла
     */
    public void save(String str) {
        //TrackStorage.storeToFile(str + ".track");
        //fileManager.getGenreStorage().storeToFile(str + ".genre");
        fileManager.saveToFile(str);
    }

//    protected Object readResolve() {
//        return controller;
//        //return getInstance();//!!!!!!!!!!!!!!!!!!!!!!!!!
//    }
    public void replaceFileManager(FileManager newFileManager) {
        fileManager = newFileManager;//!!!!! kosyak maybe
    }

    /**
     * Импортирование треков из выбранного файла в текущий файл.<br>
     * Сначала импортируем жанры. Если такой жанр существует, то его не
     * импортируем.<br>
     * Затем импортируем треки. Если трек полностью идентичен(не считая id)
     * одному из уже имеющихся, то мы его не импортируем. (эта проверка заложена
     * в методе addTrack в классе TrackStorage) Иначе создаем новый трек. (При
     * создании трека, если жанр новый, то добавляем этот жанр. Если такой жанр
     * трека уже имеется, то добавляем этот трек в tracklist этого жанра)<br>
     *
     *
     * @param filename название импортируемого файла
     */
    public void importTracks(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));) {
            HashMap<Long, Track> tracks = (HashMap<Long, Track>) ois.readObject();
            HashMap<Long, Genre> genres = (HashMap<Long, Genre>) ois.readObject();
            for (Map.Entry<Long, Genre> entry : genres.entrySet()) {//импортируем жанры
                if (fileManager.getGenreStorage().getByTitle(entry.getValue().getName()) == null) {//если такого жанра нет, то создаем новый
                    fileManager.getGenreStorage().addGenre(entry.getValue());
                }
            }
            for (Map.Entry<Long, Track> entry : tracks.entrySet()) {//импортируем треки
                fileManager.getTrackStorage().addTrack(entry.getValue());//в самом методе производится проверка на абсолютную схожесть и привязка треков к жанрам
            }
            //привязка новых треков к жанрам 

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private static class SingletonHelper {
//
//        private static final ServerController INSTANCE = new ServerController();
//    }
}

//Ошибки, пока не обрабатываются.
