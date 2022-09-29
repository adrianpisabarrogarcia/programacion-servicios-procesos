package ProgramacionHilo;

public class Ejercicio3 {
    public static void main(String args[]) {
        System.out.println("Creamos el objeto de ProgramacionHilo.MultiHelloWorld");
        MultiHelloWorld mHelloWorld = new MultiHelloWorld();

        System.out.println("Creamos hilo 1");
        Thread t1 = new Thread(mHelloWorld);
        System.out.println("Lanzamos ejecución del hilo 1");
        t1.start();

        System.out.println("Creamos hilo 2");
        Thread t2 = new Thread(mHelloWorld);
        System.out.println("Lanzamos ejecución del hilo 2");
        t2.start();
    }
}

class MultiHelloWorld implements Runnable {
    public void run() {
        System.out.println("Hello World");
    }
}