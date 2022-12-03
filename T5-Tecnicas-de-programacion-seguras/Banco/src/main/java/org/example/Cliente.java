package org.example;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private static Logger logger = Logger.getLogger(Cliente.class);

    public static final int PUERTO = 50001;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket(HOST, PUERTO);
        } catch (IOException e) {
            logger.error("Error de conexion al servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Conectado al servidor");

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

        pantallaPrincipal(output, input);



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

    private static void pantallaAcceso(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("Introduzca su nombre de usuario");
    }

    private static void pantallaRegistro(ObjectOutputStream output, ObjectInputStream input) {
        System.out.println("Ingrese el nombre de usuario");
    }
}
