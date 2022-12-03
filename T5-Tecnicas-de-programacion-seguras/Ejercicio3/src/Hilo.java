import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Hilo extends Thread {

    Socket c = new Socket();

    public Hilo(Socket c) {
        this.c = c;
    }

    public void run() {
        try {
            //Generador de claves
            KeyGenerator keygen = null;
            Cipher desCipher = null;
            SecretKey key = null;
            try {
                //Obteniendo generador de claves con cifrado DES
                keygen = KeyGenerator.getInstance("DES");
                //Crear cifrador y poner en modo descifrado
                //Esta clave esta en base 64 y codificada
                key = keygen.generateKey();
                System.out.println("Clave generada: " + key);
                desCipher = Cipher.getInstance("DES");
                desCipher.init(Cipher.DECRYPT_MODE, key);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }


            //Receptor de mensajes en ese orden ⚠️
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                output = new ObjectOutputStream(c.getOutputStream());
                input = new ObjectInputStream(c.getInputStream());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }

            //Enviamos la clave generada

            try {
                output.writeObject(key);
                output.flush();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            System.out.println("Clave enviada: "+desCipher);

            //Recibimos todos los mensajes
            byte[] mensajeRecibido = null;
            String mensajeRecibidoDescifrado = null;
            do{
                mensajeRecibido = (byte[]) input.readObject();
                System.out.println("Mensaje recibido cifrado: " + mensajeRecibido);
                //Desencriptamos el mensaje
                mensajeRecibidoDescifrado = new String(desCipher.doFinal(mensajeRecibido));
                System.out.println("Mensaje recibido descifrado: " + mensajeRecibidoDescifrado);
            }while (!mensajeRecibidoDescifrado.equals("fin"));

            System.out.println("Cerrando conexion nombre: "+c.getInetAddress().getHostName());
            c.close();
            input.close();
            output.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
