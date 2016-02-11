/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NC_Lab1.Util;

import NC_Lab1.controller.ClientController;
import NC_Lab1.controller.ServerController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.interrupted;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 */
public class ClientThread extends Thread {

    private final ServerController ctrl;
    private FileManager fileManager;
    public ObjectOutputStream oout; //!!!!!!!!!!!!!!!!!!!!!!!11
    public ObjectInputStream oin;   //!!!!!!!!!!!!!!!!!!!!!!! не public
    private final long clientID;
    private ClientController.NumberOperation codeOperation;//!!!!!!!!! полная чушь, если клиент и сервер находятся в разных проектах. Нужно загружать из файла или по сети

    /**
     * Передает ответ от сервера к клиенту.<br> 
     * В ответе сначала идет информация о статусе ошибки(1-ошибок нет, -1-ошибки есть),
     * затем массив строк. В случае, если передаются треки, то вся информация о
     * треке передается в виде строки. В случае, если передается один трек(т.е
     * был поиск по ID), то информация в виде параметров трека, начиная с Id
     * трека.
     *
     * Информация передается только тогда, когда err !=-1  и ansver!=null
     *
     * @param err код ошибки
     * @param answer передаваемая информация клиенту
     */
    public void sendAnswer(int err, ArrayList<String> answer) {
        try {
            if (err == -1) {
                System.out.println("Server error");
            }
            //System.out.println("err = " + err);
            oout.writeInt(err);
            if ((err != -1) && (answer != null)) {
                //sort
                
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
     * Конструктор.
     * 
     * @param g группа потоков
     * @param oi поток чтения
     * @param oos поток записи
     * @param clientNum id клиента
     * @param fileManeger объект агрегирующего класса, для хранения и работы с треками и жанрами
     */
    ClientThread(ThreadGroup g, ObjectInputStream oi, ObjectOutputStream oos, long clientNum, FileManager fileManager) {
        super(g, String.valueOf(clientNum));
        this.fileManager = fileManager;
        ctrl = new ServerController(this.fileManager);
        clientID = clientNum;
        oout = oos;
        oin = oi;
        System.out.println("К нам присоединился новый клиент!");
    }

    /**
     * Замена fileManeger-а
     * @param newFileManager новый объект FileManager-а
     */
    public void replaceFileManager(FileManager newFileManager) {
        fileManager = newFileManager;
        ctrl.setFileManager(fileManager);
    }

    /**
     * Метод, запуска потока.<br>
     * Метод слушает от клиента код операции, затем по данному коду вызывает
     * метод, который выполняет сервер.
     */
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
                        sendAnswer(ctrl.addTrack((ArrayList<String>) oin.readObject()), null);
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
                        answer = ctrl.findGenreByTitle(oin.readUTF());
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
                    //case getSortedById:
                    //ctrl.getSortedById()
                    //case getSortedByTitle
                    //case getSortedByLenght
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
