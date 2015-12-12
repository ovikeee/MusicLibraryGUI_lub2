package NC_Lab1;

import NC_Lab1.Model.Genre;
import NC_Lab1.Model.Track;
import NC_Lab1.controller.ClientController.NumberOperation;
import NC_Lab1.controller.ServerController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.interrupted;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectorThread extends Thread {

    private ServerController ctrl = ServerController.getInstance();
    private ServerSocket serverSocket;
    private ThreadGroup clientThreadsGroup;
    int clientNum = 0;

    public ConnectorThread(ServerSocket ss) {
        serverSocket = ss;
        clientThreadsGroup = new ThreadGroup("ClientThreadsGroup");
    }

    @Override
    public void run() {
        System.out.println("Waiting for clients...");
        while (!isInterrupted()) {
            try {
                new ClientThread(clientThreadsGroup, serverSocket.accept()).start();//создаем поток для работы с новым клиентом
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
        clientThreadsGroup.interrupt();

    }

    //метод, позволяет отправить сообщение всем активным клиентам
    void respond(String message) throws IOException {
        ClientThread[] threadList = new ClientThread[clientThreadsGroup.activeCount()];
        clientThreadsGroup.enumerate(threadList);       // Все активные потоки из группы должны скопироваться в массив          
        for (int i = 0; i < threadList.length; i++) {   // Теперь выводим сообщение каждому клиенту
            threadList[i].oout.writeUTF(message);
        }
    }

    class ClientThread extends Thread {

        private ObjectOutputStream oout;
        private ObjectInputStream oin;
        private Socket clientSocket;

        private NumberOperation codeOperation;

        /**
         * Передает ответ от сервера к клиенту в ответе сначала идет ина о
         * ошибке, затем массив строк. В случае, если передаются треки, то вся
         * информация о треке передается в виде строки. В случае, если
         * передается один трек(т.е был поиск по ID), то информация в виде
         * параметров трека, начиная с Id трека.
         *
         * Информация передается только тогда когда err !=-1
         *
         */
        public void sendAnswer(int err, ArrayList<String> answer) {
            try {
                System.out.println("err = " + err);
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
         * Конструктор клиента. Клиент добавляется в thread group, который
         * работает над одним файлом.
         *
         */
        ClientThread(ThreadGroup g, Socket s) {
            super(g, "Client: " + clientNum);
            clientSocket = s;
            System.out.println("К нам присоединился новый клиент!");
        }

        @Override
        public void run() {

            try {
                oout = new ObjectOutputStream(clientSocket.getOutputStream());
                oin = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
            }

            while (!interrupted()) {
                try {
                    System.out.println("");

                    ArrayList<String> answer = new ArrayList();

                    codeOperation = (NumberOperation) oin.readObject();
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
                        case findGenreById:
                            answer.add(ctrl.findGenreById(oin.readLong()));
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
                            ctrl.load(oin.readUTF());
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
                        case removeGenreById:
                            ctrl.removeGenreById(oin.readLong());
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
                            ctrl.updateGenre((ArrayList<String>) oin.readObject());
                            sendAnswer(1, null);
                            break;
                    }

                } catch (SocketException ex) {
                    Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Клиент №" + clientNum + "отключился");
                    break;//выходим из while
                    //текущий клиент отвалился
                    //отправить сообщение всем оставшимся или просто забить
                    //sendAnswer(-1, null);
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

}

public class Server {

    public static void main(String[] args) {

        int port = 7777;
        Scanner scanner = new Scanner(System.in);
        try {
            ServerSocket ss = new ServerSocket(port);
            Thread t = new ConnectorThread(ss);
            t.start();

            System.out.println("Enter 'quit' to close server");
            String input = "";
            while (!input.equalsIgnoreCase("quit")) {
                input = scanner.next();
            }

            t.interrupt();

            System.out.println("Port closed");
        } catch (IOException e) {
            System.out.print("Can't access port ");
            System.out.println(port);
        }
    }
}
