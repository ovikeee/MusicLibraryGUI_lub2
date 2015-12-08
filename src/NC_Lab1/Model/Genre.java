package NC_Lab1.Model;

import NC_Lab1.Util.IdGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by azaz on 26/10/15.
 */
public class Genre implements Serializable {

    private long idGenre;
    private String name;
 
 



    public Genre(String name) {
        this.idGenre = IdGenerator.getInstance().GetNextId();
        this.name = name;
    }

    public static Set<Genre> getAll() {
        Set<Genre> set = new HashSet<>();
        for (Map.Entry<Long, Genre> longGenreEntry : GenreStorage.getStorage().entrySet()) {
            set.add(longGenreEntry.getValue());
        }
        return set;

    }

    @Override
    public String toString() {
        return idGenre + " " + name;
    }

    public ArrayList<String> toArrayListString() {
        ArrayList<String> genre = new ArrayList<String>();
        genre.add(idGenre + "");
        genre.add(name);
        return genre;
    }

    public long getId_genre() {

        return idGenre;
    }

    public void setId_genre(long idGenre) {
        this.idGenre = idGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
}
