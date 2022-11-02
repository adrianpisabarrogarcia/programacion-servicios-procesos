import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MenuHiloServer extends Thread {

    private DatagramPacket datagramPacket;
    private DatagramSocket socketUDP;

    public MenuHiloServer(DatagramPacket datagramPacket, DatagramSocket socketUDP) {
        this.datagramPacket = datagramPacket;
        this.socketUDP = socketUDP;
    }

    @Override
    public void run() {
        //Mostrar mensaje
        String mensaje = new String(datagramPacket.getData());
        System.out.println("Ha llegado una peticion con mensaje: " + mensaje);

        //Mostar dirección IP
        String IP = datagramPacket.getAddress().toString();
        System.out.println("Procedente de: " + IP);

        //Mostrar puerto
        int puerto = datagramPacket.getPort();
        System.out.println("En el puerto: " + puerto);

        //Vamos a devolver el menú con las opciones del chat
        String menu = "1. Chat 1 \n2. Chat 2\n3. Chat 3\n4. Salir";

        //Preparar mensaje a enviar
        byte[] mensajeEnBytes = menu.getBytes();
        DatagramPacket datagramPacketMensajeEnviar = new DatagramPacket(
                mensajeEnBytes,
                mensajeEnBytes.length,
                datagramPacket.getAddress(),
                datagramPacket.getPort()
        );


        //Enviar mensaje
        try {
            socketUDP.send(datagramPacketMensajeEnviar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Mostrar mensaje de confirmación
        System.out.println("Menú enviado");

    }




}
