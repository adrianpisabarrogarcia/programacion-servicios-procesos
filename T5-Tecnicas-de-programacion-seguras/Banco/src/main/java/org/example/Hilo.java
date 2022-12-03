package org.example;

import org.apache.log4j.Logger;

import java.net.Socket;

public class Hilo extends Thread {

    private static Logger logger = Logger.getLogger(Hilo.class);

    Socket socket;

    public Hilo(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        logger.info("Hola");
    }


}
