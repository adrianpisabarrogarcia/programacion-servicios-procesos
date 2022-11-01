import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HiloDelServidor extends Thread {
    Socket cliente;
    int contador;

    public HiloDelServidor(Socket cliente) {
        this.cliente = cliente;
    }

    public HiloDelServidor(Socket cliente, int contador) {
        this.cliente = cliente;
        this.contador=contador;
    }

    @Override
    public void run() {
        //traza
        System.out.println("Le llega al hilo el valor: " + contador);
        OutputStream aux = null;
        try {
            //Stream de salida
            aux = cliente.getOutputStream();

            //Flujo de datos de salida
            DataOutputStream flujo = new DataOutputStream(aux);
            flujo.writeUTF("Hola cliente " + contador);

            //Cerramos ambas conexiones
            flujo.close();
            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
