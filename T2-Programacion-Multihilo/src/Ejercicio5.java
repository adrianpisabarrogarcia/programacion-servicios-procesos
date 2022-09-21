import java.util.Random;

public class Ejercicio5 {
    public static void main(String args[]) {


        for (int i = 0; i < 25; i++) {
            Caballo caballo = new Caballo(i);
            Thread t1 = new Thread(caballo);
            t1.start();
        }
    }
}

class Caballo implements Runnable {
    private int posicion;

    //constructor
    public Caballo(int posicion) {
        this.posicion = posicion;
    }
    public void run() {
        for (int i = 0; i < 100; i++) {
               if (i == 99) {
                   System.out.println("El caballo " + this.posicion +" ha llegado a la meta");
               }
        }
    }
}
