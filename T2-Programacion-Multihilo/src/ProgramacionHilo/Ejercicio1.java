package ProgramacionHilo;

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String args[]) {
        System.out.println("Creamos el objeto de la clase ProgramacionHilo.Multi");
        Multi m1 = new Multi();

        System.out.println("Creamos hilo 1");
        Thread t1 = new Thread(m1);
        System.out.println("Lanzamos ejecución del hilo 1");
        t1.start();

        System.out.println("Creamos hilo 2");
        Thread t2 = new Thread(m1);
        System.out.println("Lanzamos ejecución del hilo 2");
        t2.start();

        System.out.println("Creamos hilo 3");
        Thread t3 = new Thread(m1);
        System.out.println("Lanzamos ejecución del hilo 3");
        t3.start();
    }
}


class Multi implements Runnable {
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un texto: ");
        String texto = sc.nextLine();
        for (int i = 0; i < 10; i++) {
            System.out.println("El texto introducido es: " + texto);
        }
    }
}