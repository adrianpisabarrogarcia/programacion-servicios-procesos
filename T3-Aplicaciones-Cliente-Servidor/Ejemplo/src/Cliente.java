import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cliente {

    static OutputStream os = null;
    static InputStream is = null;

    public static void main(String[] args) {
        try {
            Socket peticion = new Socket("localhost", 4000);
            System.out.println("Cliente enviando...");

            os = peticion.getOutputStream();
            is = peticion.getInputStream();
            //Enviamos un valor
            int valor = 400;
            //int to byte
            byte b = (byte) valor;
            os.write(b);
            os.flush();
            System.out.println("Valor enviado");

            //esperamos la respuesta
            int respuesta = is.read();
            System.out.println("Respuesta recibida: "+respuesta);



            //Cerramos la conexion
            /*os.close();
            is.close();
            peticion.close();*/



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
