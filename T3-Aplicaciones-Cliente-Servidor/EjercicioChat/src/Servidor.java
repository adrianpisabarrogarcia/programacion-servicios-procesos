import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {

    public static void main(String[] args) {

        //El puerto de escucha del servidor será el 8052
        final int PUERTO = 8052;

        //Creamos el socket UDP del servidor en el pueto asociado
        try {
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
            System.out.println("Servidor Activo");

            //Implementación del protocolo del servidor en un bucle infinito
            while (true) {
                //Llega un datagrama de la elección del cliente
                DatagramPacket datagramPacket = new DatagramPacket(
                        new byte[1024],
                        1024
                );
                socketUDP.receive(datagramPacket);
                String eleccion = new String(datagramPacket.getData()).trim();
                System.out.println("Eleccion del cliente: " + eleccion);

                if (eleccion.equalsIgnoreCase("mostrar menu")){
                    //Enviará de vuelta el menú
                    MenuHiloServer hilo = new MenuHiloServer(datagramPacket, socketUDP);
                    hilo.start();
                } else if (eleccion.equalsIgnoreCase("1") || eleccion.equalsIgnoreCase("2") || eleccion.equalsIgnoreCase("3")){
                    //Enviará de vuelta la opción elegida para entrar en un chat
                    Chat hilo = new Chat(datagramPacket, socketUDP);
                    hilo.start();
                } else if (eleccion.equalsIgnoreCase("4")) {
                    //Finalizar la conexión del cliente
                    System.out.println("Cliente desconectado");
                    //Opción no válida del menú
                    String mensaje = "salir";
                    DatagramPacket datagramPacket2 = new DatagramPacket(
                            mensaje.getBytes(),
                            mensaje.getBytes().length,
                            datagramPacket.getAddress(),
                            datagramPacket.getPort()
                    );
                    socketUDP.send(datagramPacket2);

                }else {
                    //Opción no válida del menú
                    String mensaje = "Opción no válida";
                    DatagramPacket datagramPacket2 = new DatagramPacket(
                            mensaje.getBytes(),
                            mensaje.getBytes().length,
                            datagramPacket.getAddress(),
                            datagramPacket.getPort()
                    );
                    socketUDP.send(datagramPacket2);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}