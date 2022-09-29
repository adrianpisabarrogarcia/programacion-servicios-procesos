package ProgramacionMultihilo;

public class Hilo extends Thread {
    Contenedor contenedor;

    public Hilo(Contenedor contenedor){
        this.contenedor = contenedor;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            contenedor.suma();
            contenedor.salida();
        }
    }
}
