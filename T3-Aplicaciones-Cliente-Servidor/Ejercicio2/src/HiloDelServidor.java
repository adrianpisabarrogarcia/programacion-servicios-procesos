import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HiloDelServidor extends Thread {
    Socket cliente;
    String mensaje;

    public HiloDelServidor(Socket cliente) {
        this.cliente = cliente;
    }

    public HiloDelServidor(Socket cliente, String mensaje) {
        this.cliente = cliente;
        this.mensaje=mensaje;
    }

    @Override
    public void run() {
        //traza
        System.out.println("Le llega al hilo el valor: " + mensaje);
        OutputStream aux = null;
        try {
            //Stream de salida
            aux = cliente.getOutputStream();

            //Flujo de datos de salida
            DataOutputStream flujo = new DataOutputStream(aux);
            flujo.writeUTF(mensaje.toUpperCase());

            //Cerramos ambas conexiones
            flujo.close();
            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
