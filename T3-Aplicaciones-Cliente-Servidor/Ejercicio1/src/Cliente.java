import java.io.*;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 5; i++) {
                Socket peticion = new Socket("localhost", 4999);

                DataOutputStream dos = new DataOutputStream(peticion.getOutputStream());
                DataInputStream dis = new DataInputStream(peticion.getInputStream());

                //Enviamos un valor
                int valor = i;
                //int to byte
                byte b = (byte) valor;
                dos.write(b);
                dos.flush();
                System.out.println("Valor enviado");

                //esperamos la respuesta
                String respuesta = dis.readUTF();
                System.out.println("Respuesta recibida: " + respuesta);

                //Cerramos la conexion
                dos.close();
                dis.close();
                peticion.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
