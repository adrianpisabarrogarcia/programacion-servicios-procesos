package org.example;

import org.apache.log4j.Logger;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Hilo extends Thread {

    private static Logger logger = Logger.getLogger(Hilo.class);

    Socket socket;

    public Hilo(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        ObjectOutputStream output = null;
        ObjectInputStream  input = null;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            logger.error("Error al crear el flujo de salida" + e.getMessage());
            throw new RuntimeException(e);
        }

        pantallaPrincipal(output, input);

    }

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

    private void pantallaLogin(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("Pantalla de login");
    }

    private void pantallaRegistro(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("Pantalla de registro");

    }


}
