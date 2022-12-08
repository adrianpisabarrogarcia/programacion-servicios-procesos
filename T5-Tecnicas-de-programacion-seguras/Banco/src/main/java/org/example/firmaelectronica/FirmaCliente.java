package org.example.firmaelectronica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;

public class FirmaCliente {

    public static void firmarCliente(ObjectInputStream input) {

        //Obtener la clave publica del servidor
        PublicKey clavePublica = null;
        try {
            clavePublica = (PublicKey) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

        //Obtener el mensaje del servidor que va a ser firmado posteriormente por el servidor
        String mensaje = null;
        try {
            mensaje = (String) input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Mensaje recibido es " + mensaje);
        System.out.println("⬆️ con este mensaje vamos a firmar ⬆️");

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

        if(verificacion){
            System.out.println("La firma es correcta");
        } else {
            System.out.println("La firma es incorrecta");
        }


    }


}
