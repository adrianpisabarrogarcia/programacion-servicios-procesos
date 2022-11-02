import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PUERTO = 4999;

        try {
             for (int i = 0; i < 5; i++) {
                //Creamos el socket para conectarnos al servidor
                Socket peticion = new Socket(HOST, PUERTO);

                InputStream aux = peticion.getInputStream();
                DataInputStream flujo = new DataInputStream(aux);

                //Esperamos la respuesta
                String respuesta = flujo.readUTF();
                System.out.println("Respuesta recibida: " + respuesta);

                //Cerramos las conexiones
                aux.close();
                flujo.close();
                peticion.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
