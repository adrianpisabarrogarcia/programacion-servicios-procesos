import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {

    public static void main(String[] args) {

        //El puerto de escucha del servidor será el 8051
        final int PUERTO = 8052;

        //Creamos el socket UDP del servidor en el pueto asociado
        try {
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
            System.out.println("Servidor Activo");

            //Implementación del protocolo del servidor en un bucle infinito
            while (true) {
                //Llega un datagrama
                DatagramPacket datagramPacket = new DatagramPacket(
                        new byte[1024],
                        1024
                );
                socketUDP.receive(datagramPacket);
                HiloDeServidorUDP hilo = new HiloDeServidorUDP(datagramPacket, socketUDP);
                hilo.start();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}