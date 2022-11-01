import java.io.*;
import java.net.ServerSocket;

public class Servidor {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4999);

            for (int i = 0; i < 5; i++) {
                HiloDelServidor hilo = new HiloDelServidor(serverSocket.accept(), i);
                hilo.start();
            }

            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
