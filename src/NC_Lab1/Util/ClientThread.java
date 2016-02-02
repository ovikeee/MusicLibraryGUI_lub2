/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NC_Lab1.Util;

import NC_Lab1.Util.ConnectorThread;
import NC_Lab1.controller.ClientController;
import NC_Lab1.controller.ServerController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.interrupted;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {

    // private 
    private ServerController ctrl;// = ServerController.getInstance();
    private FileManager fileManager;
    public ObjectOutputStream oout; //!!!!!!!!!!!!!!!!!!!!!!!11
    public ObjectInputStream oin;   //!!!!!!!!!!!!!!!!!!!!!!! не public
    //  private Socket clientSocket;
    private long clientID;
    private ClientController.NumberOperation codeOperation;

    /**
     * Передает ответ от сервера к клиенту в ответе сначала идет ина о ошибке,
     * затем массив строк. В случае, если передаются треки, то вся информация о
     * треке передается в виде строки. В случае, если передается один трек(т.е
     * был поиск по ID), то информация в виде параметров трека, начиная с Id
     * трека.
     *
     * Информация передается только тогда когда err !=-1
     *
     * @param err
     * @param answer
     */
    public void sendAnswer(int err, ArrayList<String> answer) {
        try {
            if (err == -1) {
                System.out.println("Server error");
            }
            //System.out.println("err = " + err);
            oout.writeInt(err);
            if ((err != -1) && (answer != null)) {
                oout.writeObject(answer);
                for (String string : answer) {
                    System.out.println(string);
                }
            }
            oout.flush();
            System.out.println("я отправил смс");
        } catch (IOException ex) {
            Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    ClientThread(ThreadGroup g, ObjectInputStream oi, ObjectOutputStream oos, long clientNum, FileManager fileManager) {
        super(g, "" + clientNum);
        this.fileManager = fileManager;
        ctrl = new ServerController(this.fileManager);
        clientID = clientNum;
        // clientSocket = s;
        oout = oos;
        oin = oi;
        System.out.println("К нам присоединился новый клиент!");
    }

    public void replaceFileManager(FileManager newFileManager) {
        fileManager = newFileManager;
        ctrl.setFileManager(fileManager);
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                System.out.println("");
                ArrayList<String> answer = new ArrayList();
                codeOperation = (ClientController.NumberOperation) oin.readObject();
                System.out.println("Была вызвана операция: " + codeOperation.toString());

                switch (codeOperation) {
                    case addTrack:
                        ctrl.addTrack((ArrayList<String>) oin.readObject());
                        sendAnswer(1, null);
                        break;
                    case addGenre:
                        ctrl.addGenre(oin.readUTF());
                        sendAnswer(1, null);
                        break;
                    case findTrackById:
                        answer.add(ctrl.findTrackById(oin.readLong()));
                        sendAnswer(1, answer);
                        break;
                    case findTrackByName:
                        answer.addAll(ctrl.findTrackByTitle(oin.readUTF()));
                        sendAnswer(1, answer);
                        break;
                    case findTrackByGenre:
                        answer = ctrl.findTrackByGenre(oin.readUTF());
                        sendAnswer(1, answer);
                        break;
                    case findTrackByArtist:
                        answer = ctrl.findTrackByArtist(oin.readUTF());
                        sendAnswer(1, answer);
                        break;
                    case findTrackByAlbum:
                        answer = ctrl.findTrackByAlbum(oin.readUTF());
                        sendAnswer(1, answer);
                        break;
                    case findTrackByLength:
                        answer = ctrl.findTrackByLength(oin.readLong());
                        sendAnswer(1, answer);
                        break;
                    case findGenreByName:
                        answer.add(ctrl.findGenreByTitle(oin.readUTF()));
                        sendAnswer(1, answer);
                        break;
                    case findAllTrack:
                        answer = ctrl.findAllTracks();
                        sendAnswer(1, answer);
                        break;
                    case findAllGenre:
                        answer = ctrl.findAllGenre();
                        sendAnswer(1, answer);
                        break;
                    case loadTrackAndGenre:
                        ConnectorThread.replaceFile(fileManager.getFileName(), oin.readUTF(), clientID);
//                        ctrl.load(oin.readUTF());
                        sendAnswer(1, null);
                        break;
                    case saveTrackAndGenre:
                        ctrl.save(oin.readUTF());
                        sendAnswer(1, null);
                        break;
                    case importTracks:
                        ctrl.importTracks(oin.readUTF());
                        sendAnswer(1, null);
                        break;
                    case removeTrackById:
                        ctrl.removeTrackById(oin.readLong());
                        sendAnswer(1, null);
                        break;
                    case removeGenreByName:
                        ctrl.removeGenreByTitle(oin.readUTF());
                        sendAnswer(1, null);
                        break;
                    case removeAllTrack:
                        ctrl.removeAllTracks();
                        sendAnswer(1, null);
                        break;
                    case removeAllGenre:
                        ctrl.removeAllGenres();
                        sendAnswer(1, null);
                        break;
                    case updateTrack://нулевой элемент-id, далее остальные параметры                           
                        ctrl.updateTrack((ArrayList<String>) oin.readObject());
                        sendAnswer(1, null);
                        break;
                    case updateGenre:
                        ctrl.updateGenre((ArrayList<String>) oin.readObject());//принимает старое значение жанра и новое
                        sendAnswer(1, null);
                        break;
                }
            } catch (SocketException ex) {
                //Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Клиент №" + clientID + "отключился");
                ConnectorThread.clientExit(clientID, fileManager.getFileName());                
                break;
            } catch (IOException e) {
                e.getStackTrace();
                sendAnswer(-1, null);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
                sendAnswer(-1, null);
            } catch (IndexOutOfBoundsException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
                sendAnswer(-1, null);
            } catch (NullPointerException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
                sendAnswer(-1, null);
            }

        }

    }
}
