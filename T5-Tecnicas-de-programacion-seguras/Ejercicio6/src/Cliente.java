import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class Cliente {
    public static final int PUERTO = 5001;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        //Conectarse al servidor
        Socket socket = null;
        try {
            socket = new Socket(HOST, PUERTO);
            System.out.println("Conectado al servidor");
        } catch (IOException e) {
            System.out.println("Error al conectarse al servidor");
        }

        //Crear los flujos de entrada y salida
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Obtener la clave publica del servidor
        PublicKey clavePublica = null;
        try {
            clavePublica = (PublicKey) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Clave publica recibida es " + clavePublica);

        //Obtener el mensaje del servidor que va a ser firmado posteriormente por le servidor
        String mensaje = null;
        try {
            mensaje = (String) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Mensaje recibido es " + mensaje);

        //Obtener la firma del servidor del mensaje
        byte[] firmaBytes = null;
        try {
            firmaBytes = (byte[]) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        //Verificar la firma del servidor
        Signature firma = null;
        try {
            firma = Signature.getInstance("SHA1WITHRSA");
            firma.initVerify(clavePublica);
            firma.update(mensaje.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean verificacion = false;
        try {
            verificacion = firma.verify(firmaBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Verificacion de la firma: " + verificacion);

        if(verificacion){
            System.out.println("La firma es correcta");
        } else {
            System.out.println("La firma es incorrecta");
        }

        //Cerramos los flujos
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}