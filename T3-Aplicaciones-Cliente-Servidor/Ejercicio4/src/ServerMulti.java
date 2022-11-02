import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMulti {
    public static void main(String[] args) {

        final int PUERTO = 12345;
        final InetAddress GRUPO;
        try {
            GRUPO = InetAddress.getByName("225.0.0.1");

            //Input stream
            Scanner entrada = new Scanner(System.in);

            //Crear un socket multicast
            MulticastSocket socket = new MulticastSocket();
            System.out.println("Socket abierto");

            String mensaje = "";
            while (!mensaje.trim().equalsIgnoreCase("exit")) {
                mensaje = entrada.nextLine();
                //Enviar el mensaje
                DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(), mensaje.length(), GRUPO, PUERTO);
                socket.send(paquete);
            }

            //Cerrar el socket
            socket.close();
            System.out.println("Socket cerrado");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}