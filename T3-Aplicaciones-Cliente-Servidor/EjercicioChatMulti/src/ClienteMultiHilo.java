import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ClienteMultiHilo extends Thread {

    private MulticastSocket escucha;

    public ClienteMultiHilo(MulticastSocket escucha) {
        this.escucha = escucha;
    }

    public void run() {
        try {
            //Mensajes
            String msg = "";

            while (true) {
                //Recibir paquete del servidor
                DatagramPacket paquete = new DatagramPacket(new byte[1000], 1000);
                escucha.receive(paquete);

                //Imprimirlos
                msg = new String(paquete.getData());
                System.out.println(msg.trim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
