package NC_Lab1.Model;

import NC_Lab1.Util.FileManager;
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

//    private  TrackStorage ourInstance = SingletonHelper.INSTANCE;
    private FileManager fileManager;
    private HashMap<Long, Track> storage;

    public TrackStorage(FileManager fileManager) {
        this.fileManager = fileManager;
        setStorage(new HashMap<>());
    }

    /**
     * @param regexp regular expression to find in name of album
     * @return finded genre or null if regexp not found
     */
    public ArrayList<Track> getByTitle(String regexp) {
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

    public ArrayList<Track> getByArtist(String regexp) {
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

    public ArrayList<Track> getByAlbum(String regexp) {
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

    public ArrayList<Track> getByGenre(String regexp) {
        ArrayList<Track> tracks = new ArrayList<>();
        Pattern pat = Pattern.compile(regexp);
        for (Map.Entry<Long, Track> stringGenreEntry : getStorage().entrySet()) {
            Matcher m = pat.matcher(stringGenreEntry.getValue().getGenre().getName());
            if (m.find()) {
                tracks.add(stringGenreEntry.getValue());
            }
        }
        return tracks;
    }

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
     * @param id id to find in id of album
     * @return finded genre or null if id not found
     */
    public Track getById(long id) {
        return getStorage().get(id);
    }

    public ArrayList<Track> getAllTracks() {
        ArrayList<Track> set = new ArrayList<>();
        for (Map.Entry<Long, Track> longTrackEntry : getStorage().entrySet()) {
            set.add(longTrackEntry.getValue());
        }
        return set;
    }

    public void addTrack(Track newTrack) {
        if (!fileManager.getGenreStorage().isGenre(newTrack.getGenre().getName())) {//если такого жанра нет, то создаем жанр
            fileManager.getGenreStorage().addGenre(newTrack.getGenre());
        }
        if (getById(newTrack.getId()) == null) {//проверка на целостность
            storage.put(newTrack.getId(), newTrack);
            fileManager.getGenreStorage().getByTitle(newTrack.getGenre().getName()).addInTrackList(newTrack); //добавили в треклист
            System.out.println("Трек: " + newTrack.getTitle() + " с Id= " + newTrack.getId() + " добавлен!");
        } else {
            System.out.println("Трек: " + newTrack.getTitle() + " с Id= " + newTrack.getId() + " уже существует!!!");
        }
    }

    //!!!!!!!! проверка на абсолютную схожесть
    public void addTrack(String name, String albumName, String artist, long length, String genre) {
        if (!fileManager.getGenreStorage().isGenre(genre)) {//если такого жанра нет, то создаем жанр
            fileManager.getGenreStorage().addGenre(genre);
        }
        Track track = new Track(name, albumName, artist, length, fileManager.getGenreStorage().getByTitle(genre));
        storage.put(track.getId(), track);
        fileManager.getGenreStorage().getByTitle(genre).addInTrackList(track); //добавили в треклист
        System.out.println("Трек: " + track.getTitle() + " с Id= " + track.getId() + " добавлен!");
    }

    public void setAllParam(ArrayList<String> al) {
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

    public void removeTrackById(long idTrack) {
//        storage.replace(idTrack, getById(idTrack));
        storage.remove(idTrack);
    }

    public void removeAll() {
        storage.clear();
    }

    public HashMap<Long, Track> getStorage() {
        return storage;
    }

    public void setStorage(HashMap<Long, Track> storage) {
        this.storage = storage;
    }

}

//    public  TrackStorage getInstance() {
//        return ourInstance;
//    }
/**
 * @param filename file to load
 */
//    public  void storeToFile(String filename) {
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
//            oos.writeObject(getStorage());
//            oos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
/**
 * @param filename file to store
 */
//    public  void loadFromFile(String filename) {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filename)));
//            ourInstance = new TrackStorage();
//            setStorage((HashMap<Long, Track>) ois.readObject());
//            ois.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    protected Object readResolve() {
//        TrackStorage.setStorage(this.storage);
//        return getInstance();
//    }
//    private class SingletonHelper {
//
//        private  final TrackStorage INSTANCE = new TrackStorage();
//    }
