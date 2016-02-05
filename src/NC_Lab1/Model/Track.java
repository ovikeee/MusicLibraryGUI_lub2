package NC_Lab1.Model;

import NC_Lab1.Util.IdGenerator;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс описывающий трек. <br>
 * Имеет приватные поля: <br>
 * long id, String title, String artist, String albumName, long length, Genre
 * genre<br>
 *
 */
public class Track implements Serializable {

    //!!!!!!!!!UUID ГЕН. ID???
    private long id;
    private long length;
    private String title;
    private String album;
    private String artist;
    private Genre genre;

    /**
     * Конструктор. Инициализируются поля данного класа.
     *
     * @param title название трека
     * @param artist название исполнителя
     * @param albumName название альбома
     * @param length длина записи в секундах
     * @param genre объект типа Genre
     */
    public Track(String title, String artist, String albumName, long length, Genre genre) {//!!!!!!! нужно передавать Map, где <Параметр, Значение>
        this.id =System.nanoTime(); //!!!!!!!!!! нужен ли генератор id???? IdGenerator.getInstance().GetNextId();
        this.length = length;
        this.title = title;
        this.album = albumName;
        this.artist = artist;
        this.genre = genre;
    }

    /**
     * Переопределение метода toString
     *
     * @return вся информация о треке через пробел в следующем порядке: id,
     * title, artist, album, length, genre
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(title).append(" ").append(artist).append(" ").append(album).append(" ").append(length).append(" ").append(genre);
        return sb.toString();
    }

    /**
     * Вся информация о треке в виде ArrayList(srting)
     *
     * @return вся информация о треке в ArrayList-е в следующем порядке: id,
     * title, artist, album, length, genre
     */
    public ArrayList<String> toArrayListString() {
        ArrayList<String> track = new ArrayList<String>();
        track.add(String.valueOf(id));
        track.add(title);
        track.add(artist);
        track.add(album);
        track.add(String.valueOf(length));
        track.add(genre.getName());
        return track;
    }

    /**
     * Метод для установки названия жанра
     *
     * @param newGenre объект нового жанра
     */
    public void setGenre(Genre newGenre) {
        genre = newGenre;
    }

    /**
     * Метод для получения жанра
     *
     * @return объект жанра
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Метод для получения id
     *
     * @return id трека
     */
    public long getId() {
        return id;
    }

    /**
     * Метод для установки значения id
     *
     * @param id трека
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Метод для получения длины трека
     *
     * @return длина трека в секундах
     */
    public long getLength() {
        return length;
    }

    /**
     * Метод для установки значения длины трека
     *
     * @param length длина трека в секундах
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * Метод для получения названия трека
     *
     * @return название трека
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод для установки названия трека
     *
     * @param title название трека
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод для получения названия альбома
     *
     * @return название альбома
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Метод для установки названия альбома
     *
     * @param album название альбома
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Метод для получения названия исполнителя
     *
     * @return название исполнителя
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Метод для установки названия исполнителя
     *
     * @param artist название исполнителя
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

}
