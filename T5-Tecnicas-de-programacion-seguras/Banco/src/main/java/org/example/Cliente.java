package org.example;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

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

    }
}
