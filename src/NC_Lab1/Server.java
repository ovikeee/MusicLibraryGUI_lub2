package NC_Lab1;

import NC_Lab1.Util.ConnectorThread;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;





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
