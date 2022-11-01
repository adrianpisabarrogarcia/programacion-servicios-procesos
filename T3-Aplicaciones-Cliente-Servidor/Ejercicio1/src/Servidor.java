import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4999);

            for (int i = 0; i < 5; i++) {
                Socket cliente = serverSocket.accept();
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = new DataInputStream(cliente.getInputStream());

                //Esperamos el valor del cliente
                int valor = dis.read();
                System.out.println("Valor recibido: " + valor);

                //Enviamos el valor al cliente
                dos.writeUTF("Hola cliente: " + valor);
                dos.flush();
                System.out.println("Valor enviado " + valor);

                //Cerramos la conexion
                dos.close();
                dis.close();
                cliente.close();
            }

            serverSocket.close();




            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
