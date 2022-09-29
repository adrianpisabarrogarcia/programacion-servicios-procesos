package ProgramacionHilo;

import java.util.Random;

public class Ejercicio4 {
    public static void main(String args[]) {
        System.out.println("Creamos el objeto de MultiHolaMundo");
        Cajera cajera1 = new Cajera(1);
        Cajera cajera2 = new Cajera(2);

        System.out.println("Creamos hilo 1");
        Thread t1 = new Thread(cajera1);
        System.out.println("Lanzamos ejecución del hilo 1");
        t1.start();

        System.out.println("Creamos hilo 2");
        Thread t2 = new Thread(cajera2);
        System.out.println("Lanzamos ejecución del hilo 2");
        t2.start();
    }
}

class Cajera implements Runnable {

    private int idCajera;

    public Cajera(int idCajera) {
        this.idCajera = idCajera;
    }
    public void run() {
        String nombreCliente = Thread.currentThread().getName();
        if(nombreCliente.equals("Thread-0")){
            nombreCliente = "1";
        } else{
            nombreCliente = "2";
        }

        Random r = new Random();
        int producto = r.nextInt(3-1) + 1;
        System.out.println("La cajera " + this.idCajera + " comienza a procesar la compra del cliente " + nombreCliente + ", con el producto " + producto + " en el tiempo: " + System.currentTimeMillis() * 0.001  + " segundos");
    }
}
