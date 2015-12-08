package NC_Lab1.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * Created by ovikeee on 01.11.2015
 */
public class ClientController {

    private static ObjectOutputStream oout;
    private static ObjectInputStream oin;

    private static ClientController controller = new ClientController();

    public enum NumberOperation {

        addTrack, addGenre,
        findTrackById, findTrackByName, findTrackByGenre, findTrackByArtist, findTrackByAlbum, findTrackByLength,
        findGenreById, findGenreByName,
        findAllTrack, findAllGenre,
        loadTrackAndGenre, saveTrackAndGenre,
        removeTrackById, removeGenreById, removeGenreByName, removeAllTrack, removeAllGenre,
        updateTrack, updateGenre,;
    };

    //FileManager fileManager = FileManager.getInstance();
    private ClientController() {
        int serverPort = 7777;          // порт сервера (его знают все клиенты, для подключения к серверу)
        String address = "127.0.0.1";   // IP Сервера
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            Socket socket = new Socket(ipAddress, serverPort);
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            oin = new ObjectInputStream(sin);
            oout = new ObjectOutputStream(sout);
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static ClientController getInstance() {
        //TrackStorage.loadFromFile("defaultFile");
        return controller;
    }

    public void addTrack(ArrayList<String> str) throws IOException {
        System.out.println("A zdes' ParametrsCli = " + str);
        oout.writeObject(NumberOperation.addTrack);
        oout.writeObject(str);

        oout.flush();

        int i = oin.readInt();
        if (i == -1) {
            System.out.println("Операция не выполнена!");
            throw new IOException();
        }
    }

    public void addGenre(String str) throws IOException {
        oout.writeObject(NumberOperation.addGenre);
        oout.writeUTF(str);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void removeTrackById(long id) throws IOException {
        oout.writeObject(NumberOperation.removeTrackById);
        oout.writeLong(id);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void removeGenreById(long id) throws IOException {
        oout.writeObject(NumberOperation.removeGenreById);
        oout.writeLong(id);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void removeAllTracks() throws IOException {
        oout.writeObject(NumberOperation.removeAllTrack);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void removeAllGenres() throws IOException {
        oout.writeObject(NumberOperation.removeAllGenre);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void removeGenreByName(String name) throws IOException {
        oout.writeObject(NumberOperation.removeGenreByName);
        oout.writeUTF(name);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public ArrayList<String> findTrackById(Long id) throws IOException, ClassNotFoundException, Exception {
        oout.writeObject(NumberOperation.findTrackById);
        oout.writeLong(id);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new Exception();
        } else {
            answerFromServer = (ArrayList<String>) oin.readObject();
        }
        return answerFromServer;
    }

    public ArrayList<String> findTrackByName(String name) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findTrackByName);
        oout.writeUTF(name);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;

    }

    public ArrayList<String> findTrackByArtist(String name) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findTrackByArtist);
        oout.writeUTF(name);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    public ArrayList<String> findTrackByAlbum(String name) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findTrackByAlbum);
        oout.writeUTF(name);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    public ArrayList<String> findTrackByGenre(String genre) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findTrackByGenre);
        oout.writeUTF(genre);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    public ArrayList<String> findAllTracks() throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findAllTrack);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        } else {
            answerFromServer = (ArrayList<String>) oin.readObject();
            System.out.println("KOL_VO EL: " + answerFromServer.size());
        }
        return answerFromServer;
    }

    /**
     * обновляет трек по Id
     *
     * @param str все параметры трека String name, String albumName, String
     * artist,long length, String genre)
     * @param id Id трека
     */
    public void updateTrack(ArrayList<String> str) throws IOException {
        oout.writeObject(NumberOperation.updateTrack);
        oout.writeObject(str);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public void updateGenre(ArrayList<String> param) throws IOException {
        oout.writeObject(NumberOperation.updateGenre);
        oout.writeObject(param);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    public ArrayList<String> findGenreById(long id) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findGenreById);
        oout.writeLong(id);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    public ArrayList<String> findGenreByName(String name) throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findGenreByName);
        oout.writeUTF(name);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    public ArrayList<String> findAllGenre() throws IOException, ClassNotFoundException {
        oout.writeObject(NumberOperation.findAllGenre);
        oout.flush();
        ArrayList<String> answerFromServer;
        if (oin.readInt() == -1) {
            throw new IOException();
        }
        answerFromServer = (ArrayList<String>) oin.readObject();
        return answerFromServer;
    }

    /**
     * Загрузка треков
     *
     * @param str
     * @throws java.io.IOException
     */
    public void load(String str) throws IOException {
        oout.writeObject(NumberOperation.loadTrackAndGenre);
        oout.writeUTF(str);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }

    //требуется исправление на FileManeger
    /**
     * Сохранение
     *
     * @param i выбор пункта: 1-сохранить треки 2-сохранить жанры
     * @param str имя файла
     * @throws java.io.IOException
     */
    public void save(String str) throws IOException {
        oout.writeObject(NumberOperation.saveTrackAndGenre);
        oout.writeUTF(str);
        oout.flush();
        if (oin.readInt() == -1) {
            throw new IOException();
        }
    }
}

//Ошибки, пока не обрабатываются.
