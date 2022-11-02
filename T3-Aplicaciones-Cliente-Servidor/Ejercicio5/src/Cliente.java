import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 4999;

        try {
            //Creamos el socket para conectarnos al servidor
            Socket cliente = new Socket(HOST, PUERTO);

            //Creamos el flujo de entrada para leer la respuesta del servidor
            InputStream aux = cliente.getInputStream();
            DataInputStream flujo = new DataInputStream(aux);

            //Esperamos la respuesta
            String respuesta = flujo.readUTF();
            System.out.println("Respuesta recibida: " + respuesta);

            //Separar el mensaje en dos partes
            String[] partes = respuesta.split(" ");
            Multiplicacion multiplicacion = new Multiplicacion(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
            String resultado = Double.toString(multiplicacion.getTotal());
            System.out.println("Resultado: " + resultado);

            //Enviamos el resultado
            OutputStream aux2 = cliente.getOutputStream();
            DataOutputStream flujo2 = new DataOutputStream(aux2);
            flujo2.writeUTF(resultado);

            System.out.println("Resultado enviado");

            //Cerramos las conexiones
            aux.close();
            flujo.close();
            cliente.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}