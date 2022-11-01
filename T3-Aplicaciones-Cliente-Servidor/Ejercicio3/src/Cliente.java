import java.io.IOException;
import java.net.*;


public class Cliente {
    public static void main(String[] args) {

        //El puerto de escucha del cliente será el 8051
        final int PUERTO = 8052;
        //El host del servidor
        final InetAddress HOST;
        try {
            HOST = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try {

            //Creamos un datagrama socket para conectarnos al servidor
            DatagramSocket socketUDP = new DatagramSocket();
            System.out.println("Cliente Activo");

            //Creamos el mensaje a enviar
            String mensaje = "Hola mundo";

            //Creamos el dataGrama a enviar
            DatagramPacket datagramPacket = new DatagramPacket(
                    mensaje.getBytes(),
                    mensaje.getBytes().length,
                    HOST,
                    PUERTO
            );

            //Enviamos información al servidor
            socketUDP.send(datagramPacket);
            System.out.println("Mensaje enviado: " + mensaje);

            //Creamos el datagrama para recibir la respuesta
            DatagramPacket datagramPacket2 = new DatagramPacket(
                    new byte[1024],
                    1024
            );

            //Recibimos la respuesta del servidor
            socketUDP.receive(datagramPacket2);

            //Imprimimos la respuesta del servidor
            System.out.println(new String(datagramPacket2.getData()));
            System.out.println("Mensaje imprimido");

            //Cerramos todas las conexiones
            socketUDP.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
