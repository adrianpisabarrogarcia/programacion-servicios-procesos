package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class TwoLogin {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //Preguntar por el usuario y la contraseña
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el usuario: ");
        String user = sc.nextLine();
        System.out.println("Introduce la contraseña: ");
        String password = sc.nextLine();
        sc.close();

        //Hash de la contraseña
        byte[] hashPassword = null;
        try {
            hashPassword = HashManagerSHA256.getDigest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //Abir el archivo de las credenciales
        File file = new File("credenciales.cre");

        //Leer el archivo de las credenciales
        byte[] hashPasswordFile = null;
        try {
            hashPasswordFile = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Comparar las credenciales
        if (HashManagerSHA256.compararResumenes(hashPassword, hashPasswordFile)) {
            System.out.println("Autorizado");
        } else {
            System.out.println("Error de validacion");
        }
    }


}
