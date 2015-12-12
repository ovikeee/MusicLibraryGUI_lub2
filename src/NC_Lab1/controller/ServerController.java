package NC_Lab1.controller;

import NC_Lab1.Model.Genre;
import NC_Lab1.Model.GenreStorage;
import NC_Lab1.Model.Track;
import NC_Lab1.Model.TrackStorage;
import NC_Lab1.Util.FileManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by ovikeee on 01.11.2015
 */
public class ServerController {

    private static ServerController controller = SingletonHelper.INSTANCE;

    //FileManager fileManager = FileManager.getInstance();
    private ServerController() {
    }

    public static ServerController getInstance() {
        //TrackStorage.loadFromFile("defaultFile");
        return controller;
    }

    //требуется генерация Id
    /**
     * Добавление трека или жанра param i
     *
     * @param str массив с параметрами для добавления: addTrack( String name,
     * String albumName, String artist,long length, String genre)
     */
    public void addTrack(ArrayList<String> str) {
        TrackStorage.addTrack(str.get(0), str.get(1), str.get(2), Long.parseLong(str.get(3)), str.get(4));
    }

    public void addGenre(String str) {
        GenreStorage.addGenre(str);
    }

    public void removeTrackById(long id) {
        TrackStorage.removeTrackById(id);
    }

    public void removeGenreById(long id) {
        GenreStorage.removeGenreById(id);
    }

    public void removeAllTracks() {

        TrackStorage.removeAll();
    }

    public void removeAllGenres() {

        GenreStorage.removeAll();
    }

    public void removeGenreByTitle(String name) {
        GenreStorage.removeGenreByTitle(name);
    }

//Добавлен функционал поиск треков по жанрам(нужно протестить работу)
    /**
     * @param i номер операции: 1-поиск по номеру трека 2-поиск по названию
     * трека 3-поиск по исполнителю 4-поиск по альбому 5-поиск по жанру 6-поиск
     * всех треков
     *
     * @param str дополнительный параметр используется с соответствующим номером
     * операции: 1-номер трека 2-название трека 3-нисполнитель 4-альбом 5-жанр
     * 6-пустая строка
     *
     * @return возвращаем ArrayList с результатами поиска
     */
    public String findTrackById(Long id) {
        /*поиск по номеру трека*/
        return TrackStorage.getById(id).toString();
    }

    public ArrayList<String> findTrackByTitle(String name) {
        /*поиск по всех треков с похожим названием*/
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getByTitle(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByArtist(String name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getByArtist(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByAlbum(String name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getByAlbum(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByLength(Long name) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getByLength(name));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findTrackByGenre(String genre) {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getByGenre(genre));
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    public ArrayList<String> findAllTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        ArrayList<String> answer = new ArrayList<>();
        tracks.addAll(TrackStorage.getAllTracks());
        for (int i = 0; i < tracks.size(); i++) {
            answer.add(tracks.get(i).toString());
        }
        return answer;
    }

    /**
     * обновляет трек по Id
     *
     * @param str все параметры трека String name, String albumName, String
     * artist,long length, String genre)
     * @param id Id трека
     */
    public void updateTrack(ArrayList<String> str) {
        TrackStorage.getById(Long.parseLong(str.get(0))).setAllParam(str);
    }

    public void updateGenre(ArrayList<String> param) {
        GenreStorage.getById(Long.parseLong(param.get(0))).setName(param.get(1));
    }

    public String findGenreById(long id) {
        return GenreStorage.getById(id).toString();
    }

    public String findGenreByTitle(String name) {
        return GenreStorage.getByTitle(name).toString();

    }

    public ArrayList<String> findAllGenre() {
        ArrayList<String> answer = new ArrayList<>();
        ArrayList<Genre> genre = new ArrayList<>();
        genre.addAll(GenreStorage.getAllGenre());
        for (int i = 0; i < genre.size(); i++) {
            answer.add(genre.get(i).toString());
        }
        return answer;
    }

    //требуется исправление на FileManeger
    /**
     * Загрузка треков
     *
     * @param str
     */
    public void load(String str) {
        //TrackStorage.loadFromFile(str + ".track");
        //GenreStorage.loadFromFile(str + ".genre");
        FileManager.loadFromFile(str);
    }

    //требуется исправление на FileManeger
    /**
     * Сохранение
     *
     * @param i выбор пункта: 1-сохранить треки 2-сохранить жанры
     * @param str имя файла
     */
    public void save(String str) {
        //TrackStorage.storeToFile(str + ".track");
        //GenreStorage.storeToFile(str + ".genre");
        FileManager.saveToFile(str);
    }

    protected Object readResolve() {
        return controller;
        //return getInstance();//!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public void importTracks(String filename) {
          try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));) {
            HashMap<Long,Track> tracks = (HashMap<Long, Track>)ois.readObject();
            HashMap<Long, Genre> genres = (HashMap<Long, Genre>)ois.readObject();   
    
              for (Map.Entry<Long, Genre> entry : genres.entrySet()) {
                  if(GenreStorage.getByTitle(entry.getValue().getName())==null){//если  такого жанра нет, то создаем новый
                  
                      GenreStorage.addGenre(entry.getValue()); 
                      //всем трекам назначить 
                  }
              }
//               for (Map.Entry<Long, Track> entry : tracks.entrySet()) {
//                  if(TrackStorage.getById(entry.getKey())==null){//если id такого трека нет, то создаем новый
//                  
//                      TrackStorage.addTrack(entry.getValue());
//                  }
//              }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHelper {

        private static final ServerController INSTANCE = new ServerController();
    }
}

//Ошибки, пока не обрабатываются.
