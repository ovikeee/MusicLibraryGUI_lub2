package NC_Lab1.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, отвеающий за хранение жанров, их обработку и поиск<br>
 * Не является классом-одиночкой(Singleton)<br>
 * В приватном поле storage хранятся все жанры
 */
public class GenreStorage implements Serializable {

    private Map<String, Genre> storage;

    /**
     * Конструктор.<br>
     * Инициализируется поле storage.
     */
    public GenreStorage() {
        storage = new HashMap<>();
    }

    /**
     * Метод, который сохраняет поле storage в файл.<br>
     *
     * @param filename название файла
     */
    public void saveToFile(String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(storage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получает объект жанр по названию жанра
     *
     * @param title название жанра
     * @return объект типа Genre или null, если он не найден
     */
    public Genre getByTitle(String title) {
        return storage.get(title);
    }

    /**
     * Метод для получения поля storage
     *
     * @return ссылка на поле storage
     */
    public HashMap<String, Genre> getStorage() {
        return (HashMap<String, Genre>) storage; //!!!!!!нарушение инкапсуляции?!
    }

    /**
     * Метод, который возвращает названия всех жанров
     *
     * @return названия жанров в виде ArrayList(String)
     */
    public ArrayList<String> getAllGenre() {
        ArrayList<String> set = new ArrayList<>();//!!!!!! не оптимальный поиск всех треков, надо подумать о возвращающем типе Genre или String
        for (Map.Entry<String, Genre> longTrackEntry : storage.entrySet()) {
            set.add(longTrackEntry.getValue().getName());
        }
        return set;
    }

    /**
     * Метод, который устанавливает новый storage
     *
     * @param storage новое хранилище
     */
    public void setStorage(HashMap<String, Genre> storage) {
        this.storage = storage; //!!!!!!!!!!!може нужно очищать и добавлятьчерез метод putAll?
    }

    /**
     * Метод, добавляющий новый жанр в storage <br>
     * Если такого объекта в storage нет, то создаем и добавляем<br>
     * иначе ничего не делаем
     *
     * @param newGenre объект Genre, который добавляем в storage
     */
    public void addGenre(Genre newGenre) {
        if (getByTitle(newGenre.getName()) == null) {
            storage.put(newGenre.getName(), newGenre);
            System.out.println("Жанр: " + newGenre.getName() + " добавлен!");//!!!!!! правильно ли здесь выводить сообщение?
        } else {
            System.out.println("Жанр: " + newGenre.getName() + " уже существует!");
        }
    }

    /**
     * Перегруженый метод, который создает новый жанр потом добавляетв
     * storage<br>
     * Если такого объекта в storage нет, то создаем и добавляем<br>
     * иначе ничего не делаем
     *
     * @param nameGenre название, добавляемого жанра
     */
    public void addGenre(String nameGenre) {
        if (getByTitle(nameGenre) == null) {
            Genre newGenre = new Genre(nameGenre);
            storage.put(nameGenre, newGenre);
            System.out.println("Жанр: " + newGenre.getName() + " добавлен!");
        } else {
            System.out.println("Жанр: " + nameGenre + " уже существует!");
        }
    }

    /**
     * Метод, который удаляет жанр из storage по названию жанра
     *
     * @param genreName название удаляемого жанра
     */
    public void removeGenreByTitle(String genreName) { //!!!!!!!!!!!! необходимо что-то делать с треками у которых фигурирует этот жанр
        storage.remove(genreName);
    }

    /**
     * Метод, который удаляет все жанры из storage
     */
    public void removeAll() {
        storage.clear();
    }

    /**
     * Метод, который возвращает true, если жанр уже есть в storage и false,
     * если нет
     *
     * @param genre название жанра
     * @return true, если жанр уже есть в storage и false, если нет
     */
    public boolean isGenre(String genre) {
        return storage.containsKey(genre);
    }
}
