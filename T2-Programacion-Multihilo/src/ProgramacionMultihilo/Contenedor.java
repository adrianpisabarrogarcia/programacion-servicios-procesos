package ProgramacionMultihilo;

public class Contenedor {
    int num;

    public synchronized void suma(){
        num++;
    }

    public synchronized void salida(){
        System.out.println("El número es " + num);
    }

    public void saludo(){
        System.out.println("Hola");
    }
}
