import java.io.*;

public class Ejercicio5 {
    public static void main(String[] args) throws InterruptedException {
        try {

            ProcessBuilder probuilder = new ProcessBuilder();
            probuilder.environment().forEach((k, v) -> System.out.println(k + ":" + v));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
