package org.example;

import org.apache.log4j.Logger;
import org.example.models.Usuario;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.*;


public class Hilo extends Thread {

    private static Logger logger = Logger.getLogger(Hilo.class);

    Socket socket;
    //Clave publica del servidor
    PublicKey publicKeyServidor;
    //Clave privada del servidor
    PrivateKey privateKeyServidor;
    //Cifrador del cliente
    Cipher cipherCliente;
    //Clave simetrica del cliente
    byte[] claveSimetrica = null;

    public Hilo(Socket socket) {
        this.socket = socket;
    }

    //Método para generar las claves del servidor y el cifrador
    public void run() {

        // Generar claves privadas y públicas RSA
        KeyPairGenerator generadorRSA = null;
        KeyPair claves = null;
        try {
            generadorRSA = KeyPairGenerator.getInstance("RSA");
            generadorRSA.initialize(1024);
            claves = generadorRSA.genKeyPair();
            logger.info("Generadas las claves RSA asimétricas");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error al generar las claves RSA" + e.getMessage());
            throw new RuntimeException(e);
        }

        // Crear flujos de entrada y de salida
        ObjectOutputStream output = null;
        ObjectInputStream  input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            logger.error("Error al crear el flujo de salida" + e.getMessage());
            throw new RuntimeException(e);
        }

        // Enviar clave pública al cliente
        try {
            output.writeObject(claves.getPublic());
            logger.info("Enviada la clave pública al cliente");
        } catch (IOException e) {
            logger.error("Error al enviar la clave pública al cliente" + e.getMessage());
            throw new RuntimeException(e);
        }

        // Esperar a recibir la clave simétrica cifrada con la clave pública
        byte[] claveSimetricaCifrada = null;
        try {
            claveSimetricaCifrada = (byte[]) input.readObject();
            logger.info("Recibida la clave simétrica cifrada con la clave pública");
        } catch (Exception e) {
            logger.error("Error al recibir la clave simétrica cifrada con la clave pública" + e.getMessage());
            throw new RuntimeException(e);
        }

        // Descifrar la clave simétrica con la clave privada
        try {
            Cipher descifradorRSA = Cipher.getInstance("RSA");
            descifradorRSA.init(Cipher.DECRYPT_MODE, claves.getPrivate());
            claveSimetrica = descifradorRSA.doFinal(claveSimetricaCifrada);
            logger.info("Descifrada la clave simétrica con la clave privada");
        } catch (Exception e) {
            logger.error("Error al descifrar la clave simétrica con la clave privada" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Guardar la clave privada del servidor
        privateKeyServidor = claves.getPrivate();
        //Guardar la clave publica del servidor
        publicKeyServidor = claves.getPublic();

        //Pruebas de envio y recepcion
        //pruebasEnvioRecepcion(output, input);

        //Pantalla principal
        pantallaPrincipal(output, input);

    }

    private void pruebasEnvioRecepcion(ObjectOutputStream output, ObjectInputStream input) {
        //recibir mensaje cifrado
        byte[] mensajeCifrado = null;
        try {
            mensajeCifrado = (byte[]) input.readObject();
            logger.info("Recibido el mensaje cifrado");
        } catch (Exception e) {
            logger.error("Error al recibir el mensaje cifrado" + e.getMessage());
            throw new RuntimeException(e);
        }
        //descifrar mensaje
        String mensaje = null;
        try {
            mensaje = descifrarContenido(mensajeCifrado);
            logger.info("Descifrado el mensaje");
            System.out.println("Mensaje descifrado: " + mensaje);
        } catch (Exception e) {
            logger.error("Error al descifrar el mensaje" + e.getMessage());
            throw new RuntimeException(e);
        }
        //enviar un mensaje cifrado
        mensaje = "Hola, soy el servidor";
        byte[] mensajeCifrado2 = null;
        //cifrar mensaje
        mensajeCifrado2 = cifrarContenido(mensaje);
        //enviar mensaje cifrado
        try {
            output.writeObject(mensajeCifrado2);
            logger.info("Enviado el mensaje cifrado");
        } catch (IOException e) {
            logger.error("Error al enviar el mensaje cifrado" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Método Cifrador de contenido a enviar
    public byte[] cifrarContenido(String contenido) {
        byte[] contenidoCifrado = null;

        //Obtener datos del cifrador
        try {
            cipherCliente = Cipher.getInstance("AES");
            cipherCliente.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(claveSimetrica, "AES"));
            logger.info("Obtenidos los datos del cifrador");
        } catch (Exception e) {
            logger.error("Error al obtener los datos del cifrador" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Cifrar contenido
        try {
            contenidoCifrado = cipherCliente.doFinal(contenido.getBytes());
            logger.info("Cifrado el contenido");
        } catch (Exception e) {
            logger.error("Error al cifrar el contenido" + e.getMessage());
            throw new RuntimeException(e);
        }

        return contenidoCifrado;
    }

    //Método Descifrador de contenido recibido
    public String descifrarContenido(byte[] contenido) {
        //Crear el descifrador simétrico
        Cipher descifrador = null;
        try {
            descifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, new SecretKeySpec(claveSimetrica, "AES"));
            logger.info("Creado el descifrador simétrico");
        } catch (Exception e) {
            logger.error("Error al crear el descifrador simétrico" + e.getMessage());
            throw new RuntimeException(e);
        }


        String contenidoDescifrado = null;
        try {
            contenidoDescifrado = new String(descifrador.doFinal(contenido));
            logger.info("Contenido descifrado");
        } catch (Exception e) {
            logger.error("Error al descifrar el contenido" + e.getMessage());
            throw new RuntimeException(e);
        }
        return contenidoDescifrado;
    }

    //Método para enviar un mensaje cifrado
    public void enviarMensaje(ObjectOutputStream output, String mensaje) {
        byte[] mensajeCifrado = null;
        //cifrar mensaje
        mensajeCifrado = cifrarContenido(mensaje);
        //enviar mensaje cifrado
        try {
            output.writeObject(mensajeCifrado);
            logger.info("Enviado el mensaje cifrado");
        } catch (IOException e) {
            logger.error("Error al enviar el mensaje cifrado" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Método para recibir un mensaje cifrado
    public String recibirMensaje(ObjectInputStream input) {
        //recibir mensaje cifrado
        byte[] mensajeCifrado = null;
        try {
            mensajeCifrado = (byte[]) input.readObject();
            logger.info("Recibido el mensaje cifrado");
        } catch (Exception e) {
            logger.error("Error al recibir el mensaje cifrado" + e.getMessage());
            throw new RuntimeException(e);
        }
        //descifrar mensaje
        String mensaje = null;
        try {
            mensaje = descifrarContenido(mensajeCifrado);
            logger.info("Descifrado el mensaje");
            System.out.println("Mensaje descifrado: " + mensaje);
        } catch (Exception e) {
            logger.error("Error al descifrar el mensaje" + e.getMessage());
            throw new RuntimeException(e);
        }
        return mensaje;
    }

    /********** Métodos de la aplicación **********/

    private void pantallaPrincipal(ObjectOutputStream output, ObjectInputStream input) {
        String mensaje = "Bienvenido al sistema de transacciones de la cuenta bancaria On-Line\n" +
                "1. Registrar un usuario\n" +
                "2. Acceder al sistema\n" +
                "3. Salir\n";
        //Enviar el mensaje al cliente
        try {
            output.writeObject(mensaje);
            output.flush();
            logger.info("Mensaje de pantalla principal enviado al cliente");
        } catch (Exception e) {
            logger.error("Error al enviar el mensaje al cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Dejar escuchando al cliente
        String opcion = "";
        try {
            opcion = (String) input.readObject();
        } catch (Exception e) {
            logger.error("Error al leer la opcion del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Opcion recibida del cliente: " + opcion);
        switch (opcion) {
            case "1":
                pantallaRegistro(output, input);
                break;
            case "2":
                pantallaLogin(output, input);
                break;
            case "3":
                //salir
                break;
            default:
                pantallaPrincipal(output, input);
                break;
        }
    }

    private void pantallaRegistro(ObjectOutputStream output, ObjectInputStream input) {
        //Ir haciendo el registro del usuario
        Usuario usuario = new Usuario();
        //Recibir el nombre
        String nombre = "";
        try {
            nombre = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer el nombre del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        usuario.setNombre(nombre);
        //Recibir el apellido
        String apellido = "";
        try {
            apellido = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer el apellido del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        usuario.setApellido(apellido);
        //Recibir la edad
        String edad = "";
        try {
            edad = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer la edad del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        usuario.setEdad(Integer.parseInt(edad));
        //Recibir el email
        String email = "";
        try {
            email = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer el email del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        usuario.setEmail(email);
        //Recibir la password
        String password = "";
        try {
            password = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer la password del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }
        //From string to byte[] base64
        byte[] passwordCifrada = Base64.getDecoder().decode(password);
        usuario.setPassword(passwordCifrada);

        //Guardar el usuario en el arraylist
        Servidor.usuarios.add(usuario);

        //Enviar mensaje de confirmación
        String mensaje = "Usuario registrado correctamente";
        try {
            enviarMensaje(output, mensaje);
        } catch (Exception e) {
            logger.error("Error al enviar el mensaje de confirmación al cliente" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Imprimir el arraylist
        logger.info("Usuario registrado: " + usuario.toString());
        pantallaPrincipal(output, input);

    }

    private void pantallaLogin(ObjectOutputStream output, ObjectInputStream input) {

        //Recibir el email del cliente
        String email = "";
        try {
            email = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer el email del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Recibir el password del cliente
        String password = "";
        try {
            password = recibirMensaje(input);
        } catch (Exception e) {
            logger.error("Error al leer el password del cliente" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Hashear el password
        byte[] passwordLoginHash = null;
        try {
            passwordLoginHash = HashManagerSHA256.getDigest(password.getBytes());
        } catch (Exception e) {
            logger.error("Error al hashear el password" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Buscar el usuario
        boolean usuarioEncontrado = false;
        for (Usuario usuario : Servidor.usuarios) {
            if (usuario.getEmail().equals(email)) {
                byte[] passwordHash = usuario.getPassword();
                boolean iguales = false;
                try {
                    iguales = HashManagerSHA256.compararResumenes(passwordLoginHash, passwordHash);
                } catch (Exception e) {
                    logger.error("Error al comparar los passwords" + e.getMessage());
                    throw new RuntimeException(e);
                }
                if(iguales){
                    usuarioEncontrado = true;
                }
                break;
            }
        }

        String mensaje = "";
        if(usuarioEncontrado){
            mensaje = "Acceso correcto";
            enviarMensaje(output, mensaje);
            pantallaMenuUsuario(output, input);
        }else{
            mensaje = "Acceso incorrecto";
            enviarMensaje(output, mensaje);
            pantallaPrincipal(output, input);
        }
    }

    private void pantallaMenuUsuario(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("Pantalla menu usuario");
    }





}
