import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {

        final String HOST = "localhost";
        final int PUERTO = 4999;

        try {
            //Creamos el socket para conectarnos al servidor
            Socket peticion = new Socket(HOST, PUERTO);

            //Enviamos información al servidor
            OutputStream aux = peticion.getOutputStream();
            DataOutputStream flujo = new DataOutputStream(aux);
            String mensaje = "Hola mundo";
            flujo.writeUTF(mensaje);
            System.out.println("Mensaje enviado: "+mensaje);

            //Esperar a recibir la información
            InputStream aux2 = peticion.getInputStream();
            DataInputStream flujo2 = new DataInputStream(aux2);
            String respuesta = flujo2.readUTF();
            System.out.println("Respuesta recibida: " + respuesta);

            //Cerramos las conexiones
            aux.close();
            flujo.close();
            aux2.close();
            flujo2.close();
            peticion.close();

            System.out.println("Fin");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
