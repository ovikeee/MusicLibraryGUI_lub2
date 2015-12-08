package NC_Lab1.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by azaz on 26/10/15. Updated by ovikeee on 04/11/15.
 */
public class TrackStorage implements Serializable {

    private static TrackStorage ourInstance = SingletonHelper.INSTANCE;
    private static HashMap<Long, Track> storage;

    private TrackStorage() {
        setStorage(new HashMap<>());
    }

    public static TrackStorage getInstance() {
        return ourInstance;
    }

    /**
     * @param filename file to load
     */
    public static void storeToFile(String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            oos.writeObject(getStorage());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename file to store
     */
    public static void loadFromFile(String filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
            ourInstance = new TrackStorage();
            setStorage((HashMap<Long, Track>) ois.readObject());
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param regexp regular expression to find in name of album
     * @return finded genre or null if regexp not found
     */
    public static ArrayList<Track> getByTitle(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getTitle());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    public static ArrayList<Track> getByArtist(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getArtist());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    public static ArrayList<Track> getByAlbum(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getAlbum());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

    public static ArrayList<Track> getByGenre(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getGenre());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }
    
    public static ArrayList<Track> getByLength(Long length) {
        ArrayList<Track> tracks = new ArrayList<>();
        for (Map.Entry<Long, Track> entry : storage.entrySet()) {
            if (length == entry.getKey()) {
                tracks.add(entry.getValue());
            }
        }
        return tracks;
    }

    
    /**
     * @param id id to find in id of album
     * @return finded genre or null if id not found
     */
    public static Track getById(long id) {
        return getStorage().get(id);
    }

    public static ArrayList<Track> getAllTracks() {
        ArrayList<Track> set = new ArrayList<>();
        for (Map.Entry<Long, Track> longTrackEntry : TrackStorage.getStorage().entrySet()) {
            set.add(longTrackEntry.getValue());
        }
        return set;
    }

    public static void addTrack(Track newTrack) {
        storage.put(newTrack.getId(), newTrack);
        System.out.println("Трек: " + newTrack.getTitle() + " с Id= " + newTrack.getId() + " добавлен!");
    }

    public static void addTrack(String name, String albumName, String artist, long length, String genre) {
        Track track = new Track(name, albumName, artist, length, genre);
        storage.put(track.getId(), track);
        System.out.println("Трек: " + track.getTitle() + " с Id= " + track.getId() + " добавлен!");
    }

    public static void removeTrackById(long idTrack) {
//        storage.replace(idTrack, getById(idTrack));
        System.out.println("Зашел");
        storage.remove(idTrack);
        System.out.println("Вышел");
    }

    public static void removeAll() {
        storage.clear();
    }

    public static HashMap<Long, Track> getStorage() {
        return storage;
    }

    public static void setStorage(HashMap<Long, Track> storage) {
        TrackStorage.storage = storage;
    }

    protected Object readResolve() {
        TrackStorage.setStorage(this.storage);
        return getInstance();
    }

    private static class SingletonHelper {

        private static final TrackStorage INSTANCE = new TrackStorage();
    }
}
