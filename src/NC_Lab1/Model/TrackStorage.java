package NC_Lab1.Model;

import NC_Lab1.Util.FileManager;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, отвеающий за хранение треков, их обработку и поиск<br>
 * Не является классом-одиночкой(Singleton) <br>
 * В приватном поле storage хранятся все треки<br>
 * В приватном поле fileManager храниться ссылка на агрегирующий файл, в котором
 * храняться треки и жанры.
 */
public class TrackStorage implements Serializable {

    private FileManager fileManager;
    private Map<Long, Track> storage;

    /**
     * Конструктор. Инициализируется поле storage и fileManeger.
     *
     * @param fileManager ссылка на агрегирующий файл, в котором храняться треки
     * и жанры.
     */
    public TrackStorage(FileManager fileManager) {
        this.fileManager = fileManager;
        storage = new HashMap<>();
    }

    /**
     * Поиск трека по названию. Поиск осуществляется не по абсолютному
     * совпадению названия трека, а<br>
     * по совпадению с частью названия.
     *
     * @param regexp название трека
     * @return результат поиска в виде ArrayList(Track)
     */
    public ArrayList<Track> getByTitle(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : storage.entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getTitle());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    /**
     * Поиск трека по названию исполнителя. Поиск осуществляется не по
     * абсолютному совпадению названия исполнителя, а<br>
     * по совпадению с частью его названия.
     *
     * @param regexp название исполнителя
     * @return результат поиска в виде ArrayList(Track)
     */
    public ArrayList<Track> getByArtist(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : storage.entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getArtist());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    /**
     * Поиск трека по названию альбома. Поиск осуществляется не по абсолютному
     * совпадению названия альбома, а<br>
     * по совпадению с частью его названия.
     *
     * @param regexp название альбома
     * @return результат поиска в виде ArrayList(Track)
     */
    public ArrayList<Track> getByAlbum(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : storage.entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getAlbum());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    /**
     * Поиск трека по названию жанра. Поиск осуществляется не по абсолютному
     * совпадению названия жанра, а<br>
     * по совпадению с частью его названия.
     *
     * @param regexp название жанра
     * @return результат поиска в виде ArrayList(Track)
     */
    public ArrayList<Track> getByGenre(String regexp) {//!!!!!!!
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : storage.entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getGenre().getName());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    /**
     * Поиск трека по дляне трека.
     *
     * @param length длина трека
     * @return результат поиска в виде ArrayList(Track)
     */
    public ArrayList<Track> getByLength(Long length) {
        ArrayList<Track> tracks = new ArrayList<>();
        for (Map.Entry<Long, Track> entry : storage.entrySet()) {
            if (length == entry.getKey()) {
                tracks.add(entry.getValue());
            }
        }
        return tracks;
    }

    /**
     * Поиск трека по id.
     *
     * @param id id трека
     * @return результат поиска в виде ArrayList(Track)
     */
    public Track getById(long id) {
        return storage.get(id);
    }

    /**
     * Поиск всех треков. <br>
     *
     * @return все треки, хранящиеся в storage в виде ArrayList(Track)
     */
    public ArrayList<Track> getAllTracks() {
        ArrayList<Track> set = new ArrayList<>();
        for (Map.Entry<Long, Track> longTrackEntry : storage.entrySet()) { //!!!!!!!!!!!!putAll!!!
            set.add(longTrackEntry.getValue());
        }
        return set;
    }

    /**
     * Метод, добавляющий новый трек в storage <br>
     * Если такого трека в storage нет, то создаем и добавляем<br>
     * иначе ничего не делаем
     *
     * @param newTrack объект GTrack, который добавляем в storage
     */
    public void addTrack(Track newTrack) { //!!!!!!!! проверка на абсолютную схожесть
        if (!fileManager.getGenreStorage().isGenre(newTrack.getGenre().getName())) {//если такого жанра нет, то создаем новый жанр
            fileManager.getGenreStorage().addGenre(newTrack.getGenre());
        }
        if (getById(newTrack.getId()) == null) {//проверка на уникальность id, если id не уникальный, но остальные параметры различаются, то создаем новый трек
            storage.put(newTrack.getId(), newTrack);//такого id нету значит добавляем этот трек
            fileManager.getGenreStorage().getByTitle(newTrack.getGenre().getName()).addInTrackList(newTrack); //добавляем трек в треклист жанра
            System.out.println("Трек: " + newTrack.getTitle() + " с Id= " + newTrack.getId() + " добавлен!");
        } else {
            if (!isConsist(newTrack)) {//если такого трека нету, то 
                storage.put(newTrack.getId(), newTrack);
                fileManager.getGenreStorage().getByTitle(newTrack.getGenre().getName()).addInTrackList(newTrack); //добавили в треклист
                System.out.println("Трек: " + newTrack.getTitle() + " с новым Id= " + newTrack.getId() + " добавлен!");
            } else {
                System.out.println("Трек: " + newTrack.getTitle() + " с Id= " + newTrack.getId() + " уже существует!!!");
            }
        }
    }

    /**
     * Проверка на абсолютную схожесть треков. Если все параметры схожи, не
     * считая id, то треки считаются не новым и возвращается false Иначе true.
     */
    private boolean isNew(Track oldTrack, Track newTrack) {
        boolean flag;
        if (oldTrack.getTitle().equals(newTrack.getTitle())
                && oldTrack.getArtist().equals(newTrack.getArtist())
                && oldTrack.getAlbum().equals(newTrack.getAlbum())
                && oldTrack.getLength() == newTrack.getLength()
                && oldTrack.getGenre().getName().equals(newTrack.getGenre().getName())) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * Метод проверяет содержится ли данный трек в текущем TrackStorage. Если
     * трек содержится, то возвращаем true, иначе false.
     *
     * @param checkedTrack проверяемый трек
     * @return Если трек содержится, то возвращаем true, иначе false.
     */
    public boolean isConsist(Track checkedTrack) {
        for (Map.Entry<Long, Track> entry : storage.entrySet()) {//пробегаем по всем трекам и если найдём схожий, то возвращаем true
            if (!isNew(checkedTrack, entry.getValue())) { //если трек уже имеется...
                return true;                              //возвращаем true
            }
        }
        return false;
    }

    /**
     * Перегруженный метод, добавляющий новый трек в storage <br>
     * Если такого трека в storage нет, то создаем и добавляем<br>
     * иначе ничего не делаем.
     *
     * @param name название трека
     * @param albumName название альбома
     * @param artist название исполнителя
     * @param length длина записи
     * @param genre название жанра
     */
    public void addTrack(String name, String albumName, String artist, long length, String genre) {    //!!!!!!!! проверка на абсолютную схожесть
        if (!fileManager.getGenreStorage().isGenre(genre)) {//если такого жанра нет, то создаем жанр
            fileManager.getGenreStorage().addGenre(genre);
        }
        Track track = new Track(name, albumName, artist, length, fileManager.getGenreStorage().getByTitle(genre));
        storage.put(track.getId(), track);
        fileManager.getGenreStorage().getByTitle(genre).addInTrackList(track); //добавили в треклист
        System.out.println("Трек: " + track.getTitle() + " с Id= " + track.getId() + " добавлен!");
    }

    /**
     * Устанавливаем новые значения параметров указанного трека.
     *
     * @param al все новые параметры нового трека
     */
    public void setAllParam(ArrayList<String> al) {//!!!!!!!!!!!! в виде Мар передаем параметр!!!
        Track track = getById(Long.parseLong(al.get(0)));
        track.setTitle(al.get(1));
        track.setArtist(al.get(2));
        track.setAlbum(al.get(3));
        track.setLength(Long.parseLong(al.get(4)));
        if (fileManager.getGenreStorage().getByTitle(al.get(5)) == null) {
            fileManager.getGenreStorage().addGenre(al.get(5));
        }
        track.setGenre(fileManager.getGenreStorage().getByTitle(al.get(5)));
    }

    /**
     * Удаление трека по Id
     *
     * @param idTrack id трека
     */
    public void removeTrackById(long idTrack) {//!!!!!!!!!нужно из tracklist жанра удалить данный трек
        storage.remove(idTrack);
    }

    /**
     * Удаление всех треков из storage.
     */
    public void removeAll() {
        storage.clear();
    }

    /**
     * Метод, возвращающий ссылку на storage.
     *
     * @return ссылка на storage
     */
    public HashMap<Long, Track> getStorage() {
        return (HashMap<Long, Track>) storage;
    }

    /**
     * Метод, который устанавливает новый storage
     *
     * @param storage новое хранилище
     */
    public void setStorage(HashMap<Long, Track> storage) {
        this.storage = storage;
    }
}
