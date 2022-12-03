package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class OneRegistro {
    public static void main(String[] args) {

        //Registro de usuario y contraseña
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el usuario: ");
        String user = sc.nextLine();
        System.out.println("Introduce la contraseña: ");
        String password = sc.nextLine();
        sc.close();

        //Encriptación de la contraseña
        byte[] hashPassword = null;
        try {
            hashPassword = HashManagerSHA256.getDigest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        //Guardado de la contraseña encriptada
        File file = new File("credenciales.cre");
        try {
            Files.write(file.toPath(), hashPassword);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}