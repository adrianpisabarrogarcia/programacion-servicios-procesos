package org.example;

import org.apache.log4j.Logger;

public class Servidor {

    private static Logger logger = Logger.getLogger(Servidor.class);


    public static void main(String[] args) {
        System.out.println("Hello world!");
        logger.info("Hello world!");
        logger.debug("Hello world!");
        logger.error("Hello world!");
        logger.warn("Hello world!");
        logger.fatal("Hello world!");
    }
}