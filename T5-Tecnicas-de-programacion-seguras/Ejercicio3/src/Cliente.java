import javax.crypto.*;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Cliente {

    final int PUERTO = 5001;
    private Socket cliente;

    public static void main(String[] args) {
        // Crea el cliente
        Cliente c = new Cliente();
        c.initClient();
    }

    private void initClient() {
        //Conectarme al servidor
        try {
            cliente = new Socket("localhost", PUERTO);
            System.out.println("Conectado: "+cliente.getInetAddress().getHostName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Crear input y output de datos
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try {
            input = new ObjectInputStream(cliente.getInputStream());
            output = new ObjectOutputStream(cliente.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Recibir clave simetrica
        SecretKey key = null;
        try {
            key = (SecretKey) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al recibir clave simetrica");
            throw new RuntimeException(e);
        }
        System.out.println("Clave recibida: " + key);

        //Configurar cipher para encriptar
        Cipher desCipher = null;
        try {
            desCipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        try {
            desCipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        //Pedimos mensajes al cliente
        Scanner sc = new Scanner(System.in);
        String mensajeEnviar = "";
        do{
            System.out.println("Escriba mensajes para enviar al servidor, finaliza con 'fin': ");
            mensajeEnviar = sc.nextLine();
            //Encriptar mensaje
            byte[] mensajeEncriptado = null;
            try {
                mensajeEncriptado = desCipher.doFinal(mensajeEnviar.getBytes());
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                throw new RuntimeException(e);
            }
            //Enviar mensaje encriptado
            try {
                output.writeObject(mensajeEncriptado);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while (!mensajeEnviar.equalsIgnoreCase("fin"));

        //Cerrar conexion
        System.out.println("Cerrando cliente nombre: "+cliente.getInetAddress().getHostName());

        try {
            input.close();
            output.close();
            cliente.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
