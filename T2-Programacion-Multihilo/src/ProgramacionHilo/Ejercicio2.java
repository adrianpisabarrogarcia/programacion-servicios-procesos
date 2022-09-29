package ProgramacionHilo;

public class Ejercicio2 {
    public static void main(String args[]) {
        System.out.println("Creamos el objeto de ProgramacionHilo.MiltiPares");
        MiltiPares mPar = new MiltiPares();
        System.out.println("Creamos el objeto de ProgramacionHilo.MiltiImpares");
        MiltiImpares mImpar = new MiltiImpares();



        System.out.println("Creamos hilo 1");
        Thread t1 = new Thread(mPar);
        t1.setPriority(10);


        System.out.println("Creamos hilo 2");
        Thread t2 = new Thread(mImpar);

        System.out.println("Lanzamos ejecución del hilo 1");
        t1.start();
        System.out.println("Lanzamos ejecución del hilo 2");
        t2.start();
    }
}


class MiltiPares implements Runnable {
    public void run() {
        for (int i = 1; i < 101; i++) {
            //numeros pares
            if (i % 2 == 0) {
                System.out.println("El número " + i + " es par");
            }
        }
    }
}

class MiltiImpares implements Runnable {
    public void run() {
        for (int i = 1; i < 101; i++) {
            //numeros impares
            if (i % 2 != 0) {
                System.out.println("El número " + i + " es impar");
            }
        }
    }
}