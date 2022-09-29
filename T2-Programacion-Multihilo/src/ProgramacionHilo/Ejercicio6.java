package ProgramacionHilo;

public class Ejercicio6 {
    public static void main(String args[]) {
        int numero = 0;
        for (int i = 0; i < 4; i++) {
            numero += 5000;
            Contador contador = new Contador(numero);
            Thread t = new Thread(contador);
            t.start();
        }
    }
}

class Contador implements Runnable {
    private int numero;

    //constructor
    public Contador(int numero) {
        this.numero = numero;
    }
    public void run() {
        for (int i = 0; i < 100; i++) {
               if (i == 99) {
                   System.out.println("El número es " + this.numero);
               }
        }
    }
}
