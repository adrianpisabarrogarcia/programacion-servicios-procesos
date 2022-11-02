import java.io.IOException;
import java.net.*;

public class ClientMulti {
    public static void main(String[] args) {


        try {
            //Crear socket multicast
            MulticastSocket escucha = new MulticastSocket(12345);

            //Recoger la IP
            InetAddress dirIP = InetAddress.getByName("225.0.0.1");
            //Crear grupo
            InetSocketAddress grupo = new InetSocketAddress(dirIP, 12345);
            //Recoger la interfaz de red
            NetworkInterface red = NetworkInterface.getByName("localhost");

            //Unirse al grupo multicast
            escucha.joinGroup(grupo, red);

            //mensajes
            String msg = "";

            //mientras no se escriba 'exit' se reciben mensajes
            while (!msg.trim().equalsIgnoreCase("exit")) {
                //Recibir paquete del servidor
                DatagramPacket paquete = new DatagramPacket(new byte[1000], 1000);
                escucha.receive(paquete);
                msg = new String(paquete.getData());
                System.out.println("Recibo: " + msg.trim());
            }

            //Cerrar conexion y abandonar grupo
            escucha.leaveGroup(grupo, red);
            escucha.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
