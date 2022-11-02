import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientMulti {
    public static void main(String[] args) {

        System.out.println("Hola, cómo te llamas?");
        Scanner entrada = new Scanner(System.in);
        String nombre = entrada.nextLine();

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

            //Saludo al grupo
            System.out.println("Hola " + nombre + ", bienvenid@ al chat");
            System.out.println("Escribe 'exit' para salir");
            System.out.println("Escribe cualquier cosa para enviar mensajes al chat.");


            //Crear un hilo para recibir mensajes
            ClienteMultiHilo hilo = new ClienteMultiHilo(escucha);
            hilo.start();

            //mientras no se escriba 'exit' voy a estar enviando mensajes
            String msg = "";
            while (!msg.trim().equalsIgnoreCase("exit")) {
                //Enviar mensaje al servidor
                msg = entrada.nextLine();
                String msgEnviar = nombre + ": " + msg;
                DatagramPacket paquete = new DatagramPacket(msgEnviar.getBytes(), msgEnviar.length(), dirIP, 12345);
                escucha.send(paquete);

                //Recibir paquete del servidor
                /*
                DatagramPacket paquete = new DatagramPacket(new byte[1000], 1000);
                escucha.receive(paquete);
                msg = new String(paquete.getData());
                System.out.println("Recibo: " + msg.trim());
                */
            }

            //Cerrar conexion y abandonar grupo
            escucha.leaveGroup(grupo, red);
            escucha.close();
            System.exit(0);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
