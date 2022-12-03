import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;

public class Hilo extends Thread {

    Socket c;

    public Hilo(Socket c) {
        this.c = c;
    }

    public void run() {
        //Lo primero, vamos a generar una clave publica y privada
        //para ello, vamos a usar el algoritmo RSA
        KeyPairGenerator keygen;
        try {
            keygen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyPair keys = keygen.generateKeyPair();
        PrivateKey privateKey = keys.getPrivate();
        PublicKey publicKey = keys.getPublic();

        //Ahora, vamos a mandar la clave publica al cliente
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(c.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            output.writeObject(publicKey);
            System.out.println("Clave publica enviada al cliente - clave publica: " + publicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectInputStream input = null;
        try {
            input = new ObjectInputStream(c.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Ahora, vamos a recibir el texto encriptado
        Cipher descifrador = null;
        try {
            descifrador = Cipher.getInstance("RSA");
            descifrador.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] mensajeEncriptado = null;
        String mensajeDesencriptado = "";
        do{
            try {
                mensajeEncriptado = (byte[]) input.readObject();
                mensajeDesencriptado = new String(descifrador.doFinal(mensajeEncriptado));
            } catch (IllegalBlockSizeException | BadPaddingException | ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Mensaje recibido: " + mensajeDesencriptado);
        }while (!mensajeDesencriptado.equalsIgnoreCase("fin"));

        //Cerrar los flujos
        try {
            input.close();
            output.close();
            c.close();
            System.out.println("Fin de la conexion con el cliente");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
