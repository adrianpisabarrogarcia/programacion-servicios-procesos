import java.io.IOException;
import java.net.Socket;

public class Cliente {

    public static int PUERTO = 50001;
    public static String HOST = "localhost";

    public static void main(String[] args) {
        //Nos conectamos al servidor
        try {
            Socket socket = new Socket(HOST, PUERTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Conectado");
    }
}