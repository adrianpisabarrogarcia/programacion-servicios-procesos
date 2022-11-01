import java.io.*;
import java.net.ServerSocket;

public class Servidor {

    public static void main(String[] args) {

        final int PUERTO = 4999;

        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);

            HiloDelServidor hilo = new HiloDelServidor(serverSocket.accept(), "Hola mundo");
            hilo.start();

            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
