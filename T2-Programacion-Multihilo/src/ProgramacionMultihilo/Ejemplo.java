package ProgramacionMultihilo;

public class Ejemplo {
    public static void main(String args[]) {
        Contenedor contenedor = new Contenedor();
        Hilo hilo1 = new Hilo(contenedor);
        Hilo hilo2 = new Hilo(contenedor);
        Hilo hilo3 = new Hilo(contenedor);
        Hilo hilo4 = new Hilo(contenedor);
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
    }
}

