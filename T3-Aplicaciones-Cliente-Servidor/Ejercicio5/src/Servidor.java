import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 4999;

        try {

            //Creamos el socket del servidor
            ServerSocket serverSocket = new ServerSocket(PUERTO);

            //Escuchamos peticiones
            System.out.println("Escuchando...");
            Socket cliente = serverSocket.accept();
            //Creamos el flujo de salida para enviar la respuesta al cliente
            OutputStream aux = cliente.getOutputStream();
            DataOutputStream flujo = new DataOutputStream(aux);

            //Enviamos la respuesta
            Multiplicacion multiplicacion = new Multiplicacion(2, 3);
            flujo.writeUTF(multiplicacion.toString());

            System.out.println("Respuesta enviada: "+multiplicacion.toString());

            //Escuchamos peticiones
            System.out.println("Escuchando...");
            //Creamos el flujo de entrada para leer la respuesta del cliente
            InputStream aux2 = cliente.getInputStream();
            DataInputStream flujo2 = new DataInputStream(aux2);

            //Esperamos la respuesta
            String respuesta = flujo2.readUTF();
            System.out.println("Respuesta recibida: " + respuesta);

            //Cerramos las conexiones
            aux.close();
            flujo.close();
            cliente.close();
            aux2.close();
            flujo2.close();
            serverSocket.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

