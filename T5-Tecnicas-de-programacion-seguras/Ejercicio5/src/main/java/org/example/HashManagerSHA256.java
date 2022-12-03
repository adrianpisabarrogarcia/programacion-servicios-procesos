package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashManagerSHA256 {
    private static final String ALGORITMO = "SHA-256";
    public static byte[] getDigest(byte[] mensaje) throws NoSuchAlgorithmException {
        MessageDigest algoritmo = MessageDigest.getInstance(ALGORITMO);
        algoritmo.reset();
        algoritmo.update(mensaje);
        return algoritmo.digest();
    }

    public static boolean compararResumenes(byte[] resumen1, byte[] resumen2) throws NoSuchAlgorithmException {
        MessageDigest algoritmo = MessageDigest.getInstance(ALGORITMO);
        algoritmo.reset();
        return algoritmo.isEqual(resumen1, resumen2);
    }

}
