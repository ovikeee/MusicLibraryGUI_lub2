package NC_Lab1.Model;

import NC_Lab1.Util.FileManager;
import NC_Lab1.Util.IdGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by azaz on 26/10/15. Updated by ovikeee on 04/11/15.
 */
public class Track implements Serializable {

    //UUID ГЕН. ID
    private long id;
    private long length;
    private String title;
    private String album;
    private String artist;
    private Genre genre;

    public Track(String title, String artist, String albumName, long length, Genre genre) {
        this.id = IdGenerator.getInstance().GetNextId();
        this.length = length;
        this.title = title;
        this.album = albumName;
        this.artist = artist;
        this.genre = genre;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(title).append(" ").append(artist).append(" ").append(album).append(" ").append(length).append(" ").append(genre);
        return sb.toString();
    }

    public ArrayList<String> toArrayListString() {
        ArrayList<String> track = new ArrayList<String>();
        track.add(id + "");
        track.add(title);
        track.add(artist);
        track.add(album);
        track.add(length + "");
        track.add(genre.getName());
        return track;
    }

    public void setGenre(Genre newGenre) {
        genre = newGenre;
    }

    public Genre getGenre() {
        return genre;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}
//  public void setAllParam(ArrayList<String> al) {
//        if (al.size() == 4) {
//            title = (String) al.get(0);
//            artist = (String) al.get(1);
//            album = (String) al.get(2);
//            length = Long.parseLong((String)al.get(3));
//            setGenre((Genre) al.get(4));
//        } else {
//            title = (String) al.get(1);
//            artist = (String) al.get(2);
//            album = (String) al.get(3);
//            length = Long.parseLong((String)al.get(4));
//            setGenre((Genre) al.get(5));
//        }
//    }