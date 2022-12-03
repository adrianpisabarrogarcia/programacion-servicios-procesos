import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    final int PUERTO = 5001;
    private ServerSocket Servidor;
    private Socket socket;


    public static void main(String[] args) {
        // Crea el servidor
        Servidor s = new Servidor();
        s.initServer();
    }

    private void initServer() {
        try {
            // Crea el Socket de servicio
            Servidor = new ServerSocket(PUERTO);
            socket = new Socket();
            // Espera conexión de un cliente
            System.out.println("Esperando conexion cliente");
            while (true) {
                //aceptamos cualquier conexion
                socket = Servidor.accept();
                //creamos un hilo para atender al cliente por cada cliente, lo que lo hace multihilo
                //para atender más de un cliente a la vez
                Hilo hilo = new Hilo(socket);
                hilo.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}