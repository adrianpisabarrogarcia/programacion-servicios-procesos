import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio2v2 {
    public static void main(String[] args) throws InterruptedException {
        String input = "";
        Process proceso = null;
        try {
            String[] cmd = {"ls"};
            ProcessBuilder probuilder = new ProcessBuilder( cmd );
            proceso = probuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            while ((input = br.readLine()) != null) {
                System.out.println(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            int codigoRetorno = proceso.waitFor();
            System.out.println("Fin de la ejecución :" + codigoRetorno);
        }

    }
}
