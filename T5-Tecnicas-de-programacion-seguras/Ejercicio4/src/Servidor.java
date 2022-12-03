import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static int PUERTO = 50001;
    public static String HOST = "localhost";

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PUERTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Socket socket = null;

        while (true){
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Hilo hilo = new Hilo(socket);
            hilo.start();
        }



    }


}
