package org.example;

import org.apache.log4j.Logger;
import org.example.models.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import java.security.*;
import javax.crypto.*;

public class Cliente {

    private static Logger logger = Logger.getLogger(Cliente.class);

    public static final int PUERTO = 50001;
    public static final String HOST = "localhost";

    //Clave publica del servidor
    static PublicKey publicKeyServidor = null;
    //Clave simetrica del cliente
    static SecretKey secretKeyCliente;

    //Método para generar las claves del cliente y el cifrador
    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket(HOST, PUERTO);
        } catch (IOException e) {
            logger.error("Error de conexion al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Conectado al servidor");


        //Crear una clave simétrica AES
        KeyGenerator generadorAES = null;
        SecretKey claveSimetrica = null;
        try {
            generadorAES = KeyGenerator.getInstance("AES");
            generadorAES.init(128);
            claveSimetrica = generadorAES.generateKey();
            logger.info("Generada la clave simétrica AES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error al generar la clave simétrica AES" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Crear flujos de entrada y de salida
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            logger.error("Error al crear el flujo de salida" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Recibir la clave publica del servidor
        try {
            publicKeyServidor = (PublicKey) input.readObject();
            logger.info("Recibida la clave publica del servidor");
        } catch (Exception e) {
            logger.error("Error al recibir la clave publica del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Encriptar la clave simétrica con la clave publica del servidor
        Cipher cipherCliente = null;
        byte[] claveSimetricaEncriptada = null;
        try {
            cipherCliente = Cipher.getInstance("RSA");
            cipherCliente.init(Cipher.ENCRYPT_MODE, publicKeyServidor);
            claveSimetricaEncriptada = cipherCliente.doFinal(claveSimetrica.getEncoded());
            logger.info("Encriptada la clave simétrica con la clave publica del servidor");
        } catch (Exception e) {
            logger.error("Error al encriptar la clave simétrica con la clave publica del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }

        //Guardar la clave en la variable global
        secretKeyCliente = claveSimetrica;
        //Guardar la clave publica del servidor en la variable global
        publicKeyServidor = publicKeyServidor;


        //Enviar la clave simétrica encriptada al servidor
        try {
            output.writeObject(claveSimetricaEncriptada);
            logger.info("Enviada la clave simétrica encriptada al servidor");
        } catch (Exception e) {
            logger.error("Error al enviar la clave simétrica encriptada al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }


        //Pruebas de envio y recepcion
        //pruebasEnvioRecepcion(output, input);

        //Pantalla Principal
        pantallaPrincipal(output, input);
    }

    private static void pruebasEnvioRecepcion(ObjectOutputStream output, ObjectInputStream input) {
        //Enviar un mensaje al servidor
        String mensaje = "Hola servidor";
        //Cifrar el mensaje
        byte[] mensajeCifrado = cifrarSimetrico(mensaje);
        //Enviar el mensaje cifrado
        try {
            output.writeObject(mensajeCifrado);
            logger.info("Enviado el mensaje cifrado al servidor");
        } catch (Exception e) {
            logger.error("Error al enviar el mensaje cifrado al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Esperar la respuesta del servidor
        byte[] mensajeRecibido = null;
        try {
            mensajeRecibido = (byte[]) input.readObject();
            logger.info("Recibido el mensaje cifrado del servidor");
        } catch (Exception e) {
            logger.error("Error al recibir el mensaje cifrado del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Descifrar el mensaje
        String mensajeDescifrado = descifrarSimetrico(mensajeRecibido);
        //Mostrar el mensaje
        logger.info("Mensaje recibido del servidor: " + mensajeDescifrado);

    }

    /**
     * Cifra un texto con la clave simétrica
     *
     * @param contenido
     * @return byte[]
     */
    private static byte[] cifrarSimetrico(String contenido) {
        //Crear el cifrador simétrico con la clave simétrica
        Cipher cifradorSimetrico = null;
        try {
            cifradorSimetrico = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cifradorSimetrico.init(Cipher.ENCRYPT_MODE, secretKeyCliente);
            logger.info("Creado el cifrador simétrico");
        } catch (Exception e) {
            logger.error("Error al crear el cifrador simétrico" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Cifrar el contenido
        byte[] contenidoCifrado = null;
        try {
            contenidoCifrado = cifradorSimetrico.doFinal(contenido.getBytes());
            logger.info("Cifrado el contenido");
        } catch (Exception e) {
            logger.error("Error al cifrar el contenido" + e.getMessage());
            throw new RuntimeException(e);
        }
        return contenidoCifrado;
    }

    /**
     * Descifra un texto con la clave simétrica
     *
     * @param contenidoCifrado
     * @return
     */
    private static String descifrarSimetrico(byte[] contenidoCifrado) {
        //Crear el cifrador simétrico con la clave simétrica
        Cipher cifradorSimetrico = null;
        try {
            cifradorSimetrico = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cifradorSimetrico.init(Cipher.DECRYPT_MODE, secretKeyCliente);
            logger.info("Creado el cifrador simétrico");
        } catch (Exception e) {
            logger.error("Error al crear el cifrador simétrico" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Descifrar el contenido
        byte[] contenidoDescifrado = null;
        try {
            contenidoDescifrado = cifradorSimetrico.doFinal(contenidoCifrado);
            logger.info("Descifrado el contenido");
        } catch (Exception e) {
            logger.error("Error al descifrar el contenido" + e.getMessage());
            throw new RuntimeException(e);
        }
        return new String(contenidoDescifrado);


    }

    //Método para enviar un mensaje cifrado al servidor
    private static void enviarMensajeCifrado(ObjectOutputStream output, String mensaje) {
        //Cifrar el mensaje
        byte[] mensajeCifrado = cifrarSimetrico(mensaje);
        //Enviar el mensaje cifrado
        try {
            output.writeObject(mensajeCifrado);
            logger.info("Enviado el mensaje cifrado al servidor");
        } catch (Exception e) {
            logger.error("Error al enviar el mensaje cifrado al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //Método para recibir un mensaje cifrado del servidor
    private static String recibirMensajeCifrado(ObjectInputStream input) {
        //Esperar la respuesta del servidor
        byte[] mensajeRecibido = null;
        try {
            mensajeRecibido = (byte[]) input.readObject();
            logger.info("Recibido el mensaje cifrado del servidor");
        } catch (Exception e) {
            logger.error("Error al recibir el mensaje cifrado del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        //Descifrar el mensaje
        String mensajeDescifrado = descifrarSimetrico(mensajeRecibido);
        return mensajeDescifrado;
    }

    private static void pantallaPrincipal(ObjectOutputStream output, ObjectInputStream input) {
        //Esperar a el mensaje del servidor
        String mensajePantallaPrincipal = null;
        try {
            mensajePantallaPrincipal = (String) input.readObject();
        } catch (Exception e) {
            logger.error("Error al leer el mensaje del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println(mensajePantallaPrincipal);
        Scanner scanner = new Scanner(System.in);
        String opcion = scanner.nextLine();
        try {
            output.writeObject(opcion);
        } catch (IOException e) {
            logger.error("Error al enviar la opcion al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        switch (opcion) {
            case "1":
                pantallaRegistro(output, input);
                break;
            case "2":
                pantallaAcceso(output, input);
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Opcion no valida");
                pantallaPrincipal(output, input);
        }
    }

    private static void pantallaRegistro(ObjectOutputStream output, ObjectInputStream input) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario();

        //Nombre de usuario
        boolean nombreValido = false;
        do {
            System.out.println("Ingrese el nombre de usuario: ");
            String nombre = scanner.nextLine();
            if (usuario.validarNombre(nombre)) {
                enviarMensajeCifrado(output, nombre);
                nombreValido = true;
            }
        } while (!nombreValido);

        //Apellido de usuario
        boolean apellidoValido = false;
        do {
            System.out.println("Ingrese el apellido de usuario: ");
            String apellido = scanner.nextLine();
            if (usuario.validarApellido(apellido)) {
                enviarMensajeCifrado(output, apellido);
                apellidoValido = true;
            }
        } while (!apellidoValido);

        //Edad de usuario
        boolean edadValida = false;
        do {
            System.out.println("Ingrese la edad de usuario: ");
            String edad = scanner.nextLine();
            if (usuario.validarEdad(edad)) {
                enviarMensajeCifrado(output, edad);
                edadValida = true;
            }
        } while (!edadValida);

        //Email de usuario
        boolean emailValido = false;
        do {
            System.out.println("Ingrese el email de usuario: ");
            String email = scanner.nextLine();
            if (usuario.validarEmail(email)) {
                enviarMensajeCifrado(output, email);
                emailValido = true;
            }
        } while (!emailValido);

        //Password de usuario
        boolean passwordValido = false;
        do {
            System.out.println("Ingrese el password de usuario. La " +
                    "contraseña debe de tener una minúscula, una mayúscula, un caracter no alfanumérico, " +
                    "un caracter alfanumérico y mínimo 8 caracteres: ");
            String password = scanner.nextLine();
            if (usuario.validarPassword(password)) {
                //Hashear la contraseña
                byte[] passwordBytes = password.getBytes();
                byte[] passwordHash = null;
                try {
                    passwordHash = HashManagerSHA256.getDigest(passwordBytes);
                    logger.info("Hasheada la contraseña");
                } catch (NoSuchAlgorithmException e) {
                    logger.error("Error al hashear la contraseña" + e.getMessage());
                    throw new RuntimeException(e);
                }
                //from byte[] to String
                String passwordHashString = Base64.getEncoder().encodeToString(passwordHash);
                enviarMensajeCifrado(output, passwordHashString);
                passwordValido = true;
            }
        } while (!passwordValido);

        //Esperar a el mensaje del servidor para saber si se ha registrado correctamente
        String mensajeRegistro = null;
        try {
            mensajeRegistro = recibirMensajeCifrado(input);
        } catch (Exception e) {
            logger.error("Error al leer el mensaje del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println(mensajeRegistro);
        pantallaPrincipal(output, input);
    }

    private static void pantallaAcceso(ObjectOutputStream output, ObjectInputStream input) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca tu correo electronico: ");
        String email = scanner.nextLine();
        System.out.println("Introduzca tu contraseña: ");
        String password = scanner.nextLine();
        Usuario usuario = new Usuario();

        //Enviar el email
        enviarMensajeCifrado(output, email);
        //Enviar la contraseña
        enviarMensajeCifrado(output, password);

        //Recibir el mensaje del servidor
        String mensajeServidor = recibirMensajeCifrado(input);
        System.out.println(mensajeServidor);
        if (mensajeServidor.equals("Acceso correcto")) {
            pantallaMenuUsuario(output, input);
        } else {
            pantallaPrincipal(output, input);
        }

    }

    private static void pantallaMenuUsuario(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("pantaMenuUsuario iniciada");
    }

}

