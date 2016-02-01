package NC_Lab1.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by azaz on 26/10/15. Updated by ovikeee on 04/11/15.
 */
public class GenreStorage implements Serializable {

//    private  GenreStorage ourInstance = SingletonHelper.INSTANCE;
    private  HashMap<String, Genre> storage;

    public GenreStorage() {
        setStorage(new HashMap<String, Genre>());
    }

//    public  GenreStorage getInstance() {
//        return ourInstance;
//    }

    /**
     * @param filename file to load
     */
    public  void storeToFile(String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(getStorage());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param regexp regular expression to find in name of genre
     * @return finded genre or null if regexp not found
     */
    public  Genre getByTitle(String regexp) {
        return storage.get(regexp);
    }

    /**
     * @return finded genre or null if id not found
     */

    public  HashMap<String, Genre> getStorage() {
        return storage;
    }

    public  ArrayList<String> getAllGenre() {
        ArrayList<String> set = new ArrayList<>();
        for (Map.Entry<String, Genre> longTrackEntry : storage.entrySet()) {
            set.add(longTrackEntry.getValue().getName());
        }
        return set;
    }

    public  void setStorage(HashMap<String, Genre> storage) {
        this.storage = storage;
    }

    public  void addGenre(Genre newGenre) {
        if (getByTitle(newGenre.getName()) == null) {
            storage.put(newGenre.getName(),newGenre);
            System.out.println("Жанр: " + newGenre.getName() + " добавлен!");
        }
    }

    public  void addGenre(String nameGenre) {
        if (getByTitle(nameGenre) == null) {
            Genre newGenre = new Genre(nameGenre);
            storage.put(nameGenre, newGenre);
            System.out.println("Жанр: " + newGenre.getName() + " добавлен!");
        }
    }

    public  void removeGenreById(long idGenre) {
        //storage.replace(idGenre, getById(idGenre));
        storage.remove(idGenre);
    }

    public  void removeGenreByTitle(String genreName) {
        storage.remove(genreName);
    }

    public  void removeAll() {
        storage.clear();
    }
    
    public boolean isGenre(String genre){
    return storage.containsKey(genre);
    }
    
        /**
     * @param filename file to store
     */
//    public  void loadFromFile(String filename) {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
//            ourInstance = new GenreStorage();
//            setStorage((HashMap<Long, Genre>) ois.readObject());
//            ois.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    protected Object readResolve() {
////        GenreStorage.setStorage(this.storage);
//        return ourInstance;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    }
//
//    private  class SingletonHelper {
//        private  final GenreStorage INSTANCE = new GenreStorage();
//    }

}
