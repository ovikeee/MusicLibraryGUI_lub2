package NC_Lab1;

import NC_Lab1.GUI.GUI;
/**
 * Класс-клиент. 
 * Создает графический интерфейс пользователя.
 * 
 */
public class Client {

    private static GUI gui;

    public static void main(String[] args) {
        gui = new GUI();
        gui.setVisible(true);
        int serverPort = 7777;          // порт сервера (его знают все клиенты, для подключения к серверу)
        String address = "127.0.0.1";   // IP Сервера
        gui.start(serverPort, address);
    }
}
