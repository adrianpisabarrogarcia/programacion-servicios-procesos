import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static OutputStream os = null;
    static InputStream is = null;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            Socket cliente = serverSocket.accept();
            System.out.println("Cliente conectado");

            os = cliente.getOutputStream();
            is = cliente.getInputStream();
            int valor = is.read();
            System.out.println("Valor recibido: " + valor);

            os.write(valor + 5);
            os.flush();
            System.out.println("Valor enviado "+(valor+5));

            //Cerramos la conexion
            /*os.close();
            is.close();
            cliente.close();
            serverSocket.close();*/

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
