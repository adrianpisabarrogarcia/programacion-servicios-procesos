import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ejercicio1 {
    public static void main(String[] args) throws InterruptedException {
        String input = "";
        Process proceso = null;
        try {
            //Imprimir hola en consola
            //proceso = Runtime.getRuntime().exec("echo hola mundo");
            //Abrir VSCode
            //proceso = Runtime.getRuntime().exec("code .");
            //Abrir Notas
            String[] cmd = {"/usr/bin/open", "-a" , "Notes"};
            proceso = Runtime.getRuntime().exec(cmd);
            //Abrir Firefox
            //String[] cmd = {"/usr/bin/open", "-a" , "Firefox"};
            //proceso = Runtime.getRuntime().exec(cmd);

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
