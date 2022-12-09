package org.example.firmaelectronica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;

public class FirmaServer {

    public static void firmarServidor(ObjectOutputStream output) {
        //Creamos una clave publica y privada con RSA
        KeyPairGenerator keysGenerador = null;
        try {
            keysGenerador = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeyPair parKeys = keysGenerador.generateKeyPair();
        PrivateKey clavePrivada = parKeys.getPrivate();
        PublicKey clavePublica = parKeys.getPublic();

        //Enviamos la clave publica
        try {
            output.writeObject(clavePublica);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Crear un mensaje y firmarlo
        String mensaje = "https://crisolsv.com/contacto/politica-de-privacidad/";
        try {
            output.writeObject(mensaje);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Firmar el mensaje con la clave privada
        Signature firma = null;
        byte[] firmaBytes = null;
        try {
            firma = Signature.getInstance("SHA1WITHRSA");
            firma.initSign(clavePrivada);
            firma.update(mensaje.getBytes());
            firmaBytes = firma.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        //Enviar la firma
        try {
            output.writeObject(firmaBytes);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
