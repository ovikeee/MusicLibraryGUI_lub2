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

    private String name;
    private Set<Track> trackList;

    public Genre(String name) {
        this.name = name;
        trackList = new HashSet<>();

    }

    @Override
    public String toString(){
    return name;
    }
    
    public void addInTrackList(Track track) {
        trackList.add(track);
    }

    public Set<Track> getTrackList() {
        return trackList;
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
