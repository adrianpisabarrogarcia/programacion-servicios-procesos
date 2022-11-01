import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HiloDeServidorUDP extends Thread {

    private DatagramPacket datagramPacket;
    private DatagramSocket socketUDP;

    public HiloDeServidorUDP(DatagramPacket datagramPacket, DatagramSocket socketUDP) {
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

        //Fecha y hora actual
        String fecha = new java.util.Date().toString();
        System.out.println("Fecha y hora actual: " + fecha);

        //Preparar mensaje a enviar
        byte[] mensajeEnBytes = fecha.getBytes();
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
        System.out.println("Mensaje enviado: " + fecha);

    }




}
