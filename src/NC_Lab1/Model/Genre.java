package NC_Lab1.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс описывающий жанр трека. <br>
 * Имеет два приватных поля name и trackList, в которых<br>
 * хранятся соответственно названрие жанра и список треков<br>
 * с этим жанром.
 *
 */
public class Genre implements Serializable {

    private String name;
    private Set<Track> trackList;

    /**
     * Конструктор. <br>
     * Инициализируются поля данного класа.
     *
     * @param name название жанраа
     */
    public Genre(String name) {
        this.name = name;
        trackList = new HashSet<>();
    }

    /**
     * Переопределение метода toString
     *
     * @return название жанра
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Метод, добавляющи трек в trackList
     *
     * @param track объект класса Track, который добавляется в треклист
     */
    public void addInTrackList(Track track) {
        trackList.add(track);
    }

    /**
     * Метот, который возвращает trackList
     *
     * @return trackList
     */
    public Set<Track> getTrackList() {
        return trackList;
    }

    /**
     * Метод, который возвращает название жанра
     *
     * @return название жанра
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, который изменяет название жанра.
     *
     * @param name новое название жанра
     */
    public void setName(String name) {
        this.name = name;
    }
}
