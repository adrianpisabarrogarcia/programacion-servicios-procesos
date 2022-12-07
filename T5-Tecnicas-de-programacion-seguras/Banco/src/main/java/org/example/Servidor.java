package org.example;

import org.apache.log4j.Logger;
import org.example.models.Usuario;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private static Logger logger = Logger.getLogger(Servidor.class);
    public static final int PUERTO = 50001;

    //Usuarios
    public static final ArrayList<Usuario> usuarios = new ArrayList<Usuario>();


    public static void main(String[] args) {
        //logger.info("Hello world!");
        //logger.debug("Hello world!");
        //logger.error("Hello world!");
        //logger.warn("Hello world!");
        //logger.fatal("Hello world!");

        //Crear el socket del servidor
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PUERTO);
        } catch (Exception e) {
            logger.error("Error al crear el socket del servidor" + e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Servidor iniciado");
        Socket socket = null;

        // Aceptar conexiones
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                logger.error("Error al aceptar conexiones" + e.getMessage());
                throw new RuntimeException(e);
            }
            logger.info("Cliente conectado");
            Hilo hilo = new Hilo(socket);
            hilo.start();
        }

    }
}