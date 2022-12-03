import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;

public class Cliente {

    public static int PUERTO = 50001;
    public static String HOST = "localhost";

    public static void main(String[] args) {
        //Nos conectamos al servidor
        Socket socket = null;
        try {
            socket = new Socket(HOST, PUERTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Conectado");

        //Recibimos la clave publica del servidor
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PublicKey publicKey = null;
        try {
            publicKey = (PublicKey) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Clave publica recibida del servidor - clave publica: " + publicKey);

        //Cifrar con la clave publica para enviar al servidor
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(System.in);
        String mensajeEviar = "";
        do{
            System.out.println("Escribe texto para cifrar con clave publica del servidor: ");
            mensajeEviar = sc.nextLine();
            //Enviamos el mensaje cifrado al servidor
            byte[] mensaje = null;
            try {
                mensaje = cipher.doFinal(mensajeEviar.getBytes());
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            }
            try {
                output.writeObject(mensaje);
                output.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (!mensajeEviar.equalsIgnoreCase("fin"));

        System.out.println("Fin de la conexion con el servidor");

        //Cerramos las conexiones
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}