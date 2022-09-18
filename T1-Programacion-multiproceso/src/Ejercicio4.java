import java.io.*;

public class Ejercicio4 {
    public static void main(String[] args) throws InterruptedException {
        try {
            java.util.Properties p = System.getProperties();
            String cadena = p.getProperty("os.name");


            //Memoria disponible en la JVM
            long memoria = Runtime.getRuntime().freeMemory();
            System.out.println("Memoria disponible: " + memoria);
            //Memoria total de la JVM
            long memoriaTotal = Runtime.getRuntime().totalMemory();
            System.out.println("Memoria total: " + memoriaTotal);
            //Numero de procesadores
            int procesadores = Runtime.getRuntime().availableProcessors();
            System.out.println("Numero de procesadores: " + procesadores);
            //Maxima memoria que puede utilizar la JVM
            long maximaMemoria = Runtime.getRuntime().maxMemory();
            System.out.println("Maxima memoria: " + maximaMemoria);
            //Version de Java
            String version = System.getProperty("java.version");
            System.out.println("Version de Java: " + version);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
