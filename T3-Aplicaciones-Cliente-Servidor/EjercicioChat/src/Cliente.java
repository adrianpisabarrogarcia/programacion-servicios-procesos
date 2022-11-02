import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.exit;


public class Cliente {
    public static void main(String[] args) {

        //El puerto de escucha del cliente será el 8052
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

            while (true) {

                //Creamos el mensaje a enviar
                String mensaje = "mostrar menu";

                //Creamos el dataGrama a enviar
                DatagramPacket datagramPacket = new DatagramPacket(
                        mensaje.getBytes(),
                        mensaje.getBytes().length,
                        HOST,
                        PUERTO
                );

                //Enviamos información al servidor
                socketUDP.send(datagramPacket);

                //Creamos el datagrama para recibir la respuesta
                DatagramPacket datagramPacket2 = new DatagramPacket(
                        new byte[1024],
                        1024
                );

                //Recibimos la respuesta del servidor
                socketUDP.receive(datagramPacket2);

                //Imprimimos la respuesta del servidor
                System.out.println(new String(datagramPacket2.getData()).trim());

                //Elegimos la opción que proviene del servidor
                Scanner scanner = new Scanner(System.in);
                String opciones = scanner.nextLine();

                //Hacemos una petición al servidor
                DatagramPacket datagramPacket3 = new DatagramPacket(
                        opciones.getBytes(),
                        opciones.getBytes().length,
                        HOST,
                        PUERTO
                );
                socketUDP.send(datagramPacket3);

                //Creamos el datagrama para recibir la respuesta
                DatagramPacket datagramPacket4 = new DatagramPacket(
                        new byte[1024],
                        1024
                );
                socketUDP.receive(datagramPacket4);
                String respuestaOpciones = new String(datagramPacket4.getData()).trim();
                System.out.println(respuestaOpciones);
                if (opciones.equals("salir")) {
                    //Cerrar todas las conexiones
                    System.out.println("Saliendo");
                    socketUDP.close();
                    exit(0);
                } else if (opciones.equals("Opción no válida")) {
                    System.out.println(respuestaOpciones);
                } else {
                    System.out.println(respuestaOpciones);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
