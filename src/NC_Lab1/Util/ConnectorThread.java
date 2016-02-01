/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Поток для регистрации клиентов на сервере. <br>
 * Создает поток для работы с каждым клиентом. <br>
 */
public class ConnectorThread extends Thread {

    private ServerSocket serverSocket;
    private ThreadGroup clientThreadsGroup;
    // private ArrayList<ThreadGroup> fileThreadGroups;
    // private static HashMap<String, Integer> countClinet = new HashMap<String, Integer>();//количество клиентов, работающих над каждым файлом
    private static HashMap<String, FileManager> fileManagerMap = new HashMap<String, FileManager>();//имя файла, файл
    private static HashMap<Long, ClientThread> clientMap = new HashMap<Long, ClientThread>();//имя файла, клиент
    // private static ArrayList<Thread> fileThreads = new ArrayList<Thread>();

    public ConnectorThread(ServerSocket ss) {
        serverSocket = ss;
        clientThreadsGroup = new ThreadGroup("AllClients");
    }

    /**
     * Регистрирация клиентов на сервере.
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
                tmpSocket = serverSocket.accept();
                System.out.println("Новый клиент!");
                FileInputStream fileOpenner;//для проверки на существование файла
                oi = new ObjectInputStream(tmpSocket.getInputStream());
                oos = new ObjectOutputStream(tmpSocket.getOutputStream());
                codeOperation = (ClientController.NumberOperation) oi.readObject();
                System.out.println("CodeOperation: " + codeOperation.toString());
                fileName = oi.readUTF();//oi.readUTF();//считываем у клиента имя файла 
                System.out.println("Имя файла: " + fileName);
                fileOpenner = new FileInputStream(fileName); //если не откроется файл, то кидаем исклюение и там создаем новый
                fileOpenner.close();
                clientNum = System.nanoTime();
                if (fileManagerMap.containsKey(fileName)) {//подключаемся к открытому файлу 
                    System.out.println("Данный файл уже используется другими клиентами");
                    clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, fileManagerMap.get(fileName));
                    clientMap.put(clientNum, clientThread);//добавляем нового клиента                
                    fileManagerMap.get(fileName).inc();//увеличиваем кол-во клиентов работающих с этим файлом
// countClinet.put(fileName, countClinet.get(fileName) + 1);//увеличиваем кол-во клиентов работающих с этим файлом
                    System.out.println("Вы подключились к этому файлу");
                } else {//подключаемся к неоткрытому файлу, но сущетвующему файлу
                    FileManager tmpFileManager = new FileManager(fileName);
                    tmpFileManager.loadFromFile(fileName);  //загружаем состояние
                    tmpFileManager.inc();
                    System.out.println("Открыли файл: " + fileName);
                    fileManagerMap.put(fileName, tmpFileManager);//имя файла, файл
                    clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, tmpFileManager);
                    clientMap.put(clientNum, clientThread);//добавляем нового клиента                
//countClinet.put(fileName, 1);//добавляем новый файл и кол-во клиентов 1             
                    System.out.println("Вы подключились к этому файлу");
                }
                clientThread.start();
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Такой файл не существует");
                FileManager tmpFileManager = new FileManager(fileName);
                tmpFileManager.inc();
                System.out.println("File: " + fileName + " was crate.");
                fileManagerMap.put(fileName, tmpFileManager);//имя файла, файл
                clientThread = new ClientThread(clientThreadsGroup, oi, oos, clientNum, tmpFileManager);
                clientMap.put(clientNum, clientThread);//добавляем нового клиента                
                System.out.println("Вы подключились к этому файлу");
                clientThread.start();
            } catch (IOException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clientThreadsGroup.interrupt();

    }

    public static void replaceFile(String oldFileManager, String newFileManager, long clietNumber) {
        //    private static HashMap<String, FileManager> fileManagerMap = new HashMap<String, FileManager>();//имя файла, файл
        //    private static HashMap<Long, ClientThread> clientMap = new HashMap<Long, ClientThread>();//имя файла, клиент

        //clientMap.get(clietNumber).replaceFileManager(null);
        // replaceFileManager;
        try {
            FileInputStream fileOpenner;//для проверки на существование файла
            fileOpenner = new FileInputStream(newFileManager); //если не откроется файл, то кидаем исклюение и там создаем новый
            fileOpenner.close();
            if (fileManagerMap.containsKey(newFileManager)) {//подключаемся к открытому файлу 
                System.out.println("Данный файл уже используется другими клиентами");
                fileManagerMap.get(oldFileManager).dec();
                if (fileManagerMap.get(oldFileManager).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
                    fileManagerMap.remove(oldFileManager);
                }
                fileManagerMap.get(newFileManager).inc();//увеличиваем кол-во клиентов работающих с этим файлом
                clientMap.get(clietNumber).replaceFileManager(fileManagerMap.get(newFileManager));
                System.out.println("Вы подключились к этому файлу");
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
                System.out.println("Вы подключились к этому файлу");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Такой файл не существует");
            FileManager tmpFileManager = new FileManager(newFileManager);
            fileManagerMap.get(oldFileManager).dec();
            if (fileManagerMap.get(oldFileManager).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
                fileManagerMap.remove(oldFileManager);
            }
            tmpFileManager.inc();
            System.out.println("File: " + newFileManager + " was crate.");
            fileManagerMap.put(newFileManager, tmpFileManager);//имя файла, файл
            clientMap.get(clietNumber).replaceFileManager(tmpFileManager);
            System.out.println("Вы подключились к этому файлу");
        } catch (IOException ex) {
            Logger.getLogger(ConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void clientExit(long clientNum, String fileName) {
        clientMap.remove(clientNum); //удаляем клиента из HashMap
        fileManagerMap.get(fileName).dec();//уменьшаем кол-во клиентов работающих с этим файлом
        if (fileManagerMap.get(fileName).getClientsCount() == 0) {//если никто не работает с ним, то его удаляем
            fileManagerMap.remove(fileName);
        }
    }

    //метод, позволяет отправить сообщение всем активным клиентам
    void respond(String message) throws IOException {
        ClientThread[] threadList = new ClientThread[clientThreadsGroup.activeCount()];
        clientThreadsGroup.enumerate(threadList);       // Все активные потоки из группы должны скопироваться в массив          
        for (int i = 0; i < threadList.length; i++) {   // Теперь выводим сообщение каждому клиенту
            threadList[i].oout.writeUTF(message);
        }
    }
}
