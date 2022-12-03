import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static final int PUERTO = 5001;

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor");
        }

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Error al aceptar la conexión");
            }

            //Crear un hilo para atender al cliente
            Hilo hilo = new Hilo(socket);
            hilo.start();
        }


    }

}
