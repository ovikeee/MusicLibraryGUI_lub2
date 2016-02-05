package NC_Lab1.Util;

import NC_Lab1.controller.ClientController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.interrupted;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Поток для регистрации клиентов на сервере. <br>
 * Создает потоки для работы с каждым клиентом. <br>
 * Поля класса:<br>
 * serverSocket - сокет сервера<br>
 * clientThreadsGroup - группа потоков fileManegerMap - связывает имя файла и
 * его fileManager clientMap - связывает имя файла и его клиента
 */
public class ConnectorThread extends Thread {

    private final ServerSocket serverSocket;
    private final ThreadGroup clientThreadsGroup;
    private static Map<String, FileManager> fileManagerMap;//имя файла, файл
    private static Map<Long, ClientThread> clientMap;//id клиента, клиент

    /**
     * Конструктор.
     *
     * @param ss сокет сервера
     */
    public ConnectorThread(ServerSocket ss) {
        fileManagerMap = new HashMap<>();//имя файла, файл
        clientMap = new HashMap<>();//имя файла, клиент
        serverSocket = ss;
        clientThreadsGroup = new ThreadGroup("AllClients");
    }

    /**
     * Регистрирация клиентов на сервере.<br>
     * Метод ждет подключения клиентов и направляет нового клиента к выбранному
     * fileManeger-у.<br>
     * Если файл, который пытается открыть клиент ещё не открывался ранее, то
     * создается fileManeger с данным файлом, и этот файл добавляется в
     * fileManegerMap и clientManegerMap. Если файл, который пытается открыть
     * клиент уже существует, то он получает ссылку на существующие fileManeger,
     * и добавляется запись только в clientMap. Когда клиент завершает работу с
     * файлом, то он вызывает метод dec у объекта fileManeger, который уменьшает
     * количество клиентов, работающих с этим файлом. Если колиество клиентов,
     * работающих с этим файлом становиться = 0, то fileManeger удаляется из
     * fileManegerMap.
     *
     */
    @Override
    public void run() {
        System.out.println("Waiting for clients...");
        ClientThread clientThread;
        ClientController.NumberOperation codeOperation;
        String fileName = null;
        Socket tmpSocket = null;
        ObjectInputStream oi = null;
        ObjectOutputStream oos = null;
        long clientNum = 0;
        while (!interrupted()) {

            try {
                tmpSocket = serverSocket.accept(); //получаем сокет клиента
                System.out.println("Новый клиент!");
                FileInputStream fileOpenner;//для проверки на существование файла
                oi = new ObjectInputStream(tmpSocket.getInputStream());
                oos = new ObjectOutputStream(tmpSocket.getOutputStream());
                codeOperation = (ClientController.NumberOperation) oi.readObject();
                System.out.println("Код операции: " + codeOperation.toString());
                fileName = oi.readUTF();//считываем у клиента имя файла 
                System.out.println("Имя файла: " + fileName);
                fileOpenner = new FileInputStream(fileName); //если не откроется файл, то кидаем исклюение и там создаем новый fileManeger
                fileOpenner.close();
                clientNum = System.nanoTime();//генерируем id пользователя
                if (fileManagerMap.containsKey(fileName)) {//подключаемся к открытому файлу 
                    System.out.println("Данный файл уже используется другими клиентами");
                    clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, fileManagerMap.get(fileName)); //создаем поток для работы с клиентом
                    clientMap.put(clientNum, clientThread);//добавляем нового клиента                
                    fileManagerMap.get(fileName).inc();//увеличиваем кол-во клиентов работающих с этим файлом
                    System.out.println("Вы подключились к файлу: " + fileName);
                } else {//подключаемся к неоткрытому файлу, но сущетвующему файлу
                    FileManager tmpFileManager = new FileManager(fileName);
                    tmpFileManager.loadFromFile(fileName);  //загружаем состояние
                    tmpFileManager.inc();
                    System.out.println("Открыли файл: " + fileName);
                    fileManagerMap.put(fileName, tmpFileManager);//имя файла, файл
                    clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, tmpFileManager);
                    clientMap.put(clientNum, clientThread);//добавляем нового клиента                
                    System.out.println("Вы подключились к файлу: " + fileName);
                }
                clientThread.start();//запускаем клиента
            } catch (FileNotFoundException fileNotFoundException) {//создаем новый fileManeger и подключаемся к нему.
                System.out.println("Такой файл не существует");
                FileManager tmpFileManager = new FileManager(fileName);
                tmpFileManager.inc();
                System.out.println("Файл: " + fileName + " был создан.");
                fileManagerMap.put(fileName, tmpFileManager);//имя файла, файл
                clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, tmpFileManager);
                clientMap.put(clientNum, clientThread);//добавляем нового клиента                
                System.out.println("Вы подключились к файлу: " + fileName);
                clientThread.start();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clientThreadsGroup.interrupt();
    }

    /**
     * Метод, который подключает клиента к новому fileManeger-у, чтобы он
     * работал вместе с другими клментами над одним файлом в реальном времени.
     *
     * @param oldFileManager старый fileManeger
     * @param newFileManager новый fileManeger
     * @param clietNumber id клиента
     */
    public static void replaceFile(String oldFileManager, String newFileManager, long clietNumber) {
        try {
            FileInputStream fileOpenner;//для проверки на существование файла
            fileOpenner = new FileInputStream(newFileManager); //если не откроется файл, то кидаем исклюение и там создаем новый
            fileOpenner.close();
            if (fileManagerMap.containsKey(newFileManager)) {//подключаемся к открытому файлу 
                System.out.println("Данный файл уже используется другими клиентами");
                fileManagerMap.get(oldFileManager).dec();//у старого fileManeger-а убавляем количество клиентов
                if (fileManagerMap.get(oldFileManager).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
                    fileManagerMap.remove(oldFileManager);
                }
                fileManagerMap.get(newFileManager).inc();//увеличиваем кол-во клиентов работающих с этим файлом
                clientMap.get(clietNumber).replaceFileManager(fileManagerMap.get(newFileManager));//устанавливаем клиенту новый fileManeger
                System.out.println("Вы подключились к файлу: " + newFileManager);
            } else {//подключаемся к неоткрытому файлу, но сущетвующему файлу
                FileManager tmpFileManager = new FileManager(newFileManager);
                tmpFileManager.loadFromFile(newFileManager);  //загружаем состояние
                fileManagerMap.get(oldFileManager).dec();
                if (fileManagerMap.get(oldFileManager).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
                    fileManagerMap.remove(oldFileManager);
                }
                tmpFileManager.inc();
                System.out.println("Открыли файл: " + newFileManager);
                fileManagerMap.put(newFileManager, tmpFileManager);
                clientMap.get(clietNumber).replaceFileManager(tmpFileManager);
                System.out.println("Вы подключились к файлу: " + newFileManager);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Такой файл не существует");
            FileManager tmpFileManager = new FileManager(newFileManager);
            fileManagerMap.get(oldFileManager).dec();
            if (fileManagerMap.get(oldFileManager).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
                fileManagerMap.remove(oldFileManager);
            }
            tmpFileManager.inc();
            System.out.println("Файл: " + newFileManager + " был создан.");
            fileManagerMap.put(newFileManager, tmpFileManager);//имя файла, файл
            clientMap.get(clietNumber).replaceFileManager(tmpFileManager);
            System.out.println("Вы подключились к файлу: " + newFileManager);
        } catch (IOException ex) {
            Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Метод, который удаляет клиента из clientMap.
     *
     * @param clientNum id клиента
     * @param fileName название файла
     */
    public static void clientExit(long clientNum, String fileName) {//!!!!!!!!!!!! ресурсы освобождаются? fileManeger нужен ли метод closeFile???
        clientMap.remove(clientNum); //удаляем клиента из HashMap
        fileManagerMap.get(fileName).dec();//уменьшаем кол-во клиентов работающих с этим файлом
        if (fileManagerMap.get(fileName).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
            fileManagerMap.remove(fileName);
        }
    }

    /**
     * Метод, позволяет отправить сообщение всем активным клиентам.
     *
     * @param message сообщение
     */
    void respond(String message) throws IOException {
        ClientThread[] threadList = new ClientThread[clientThreadsGroup.activeCount()];
        clientThreadsGroup.enumerate(threadList);       // Все активные потоки из группы должны скопироваться в массив          
        for (int i = 0; i < threadList.length; i++) {   // Теперь выводим сообщение каждому клиенту
            threadList[i].oout.writeUTF(message);
        }
    }
}
