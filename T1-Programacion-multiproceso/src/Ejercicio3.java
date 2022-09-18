import java.io.*;

public class Ejercicio3 {
    public static void main(String[] args) throws InterruptedException {
        String input = "";
        Process proceso = null;
        try {
            String[] cmd = {"ls"};
            ProcessBuilder probuilder = new ProcessBuilder(cmd);
            proceso = probuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String lineas = "";
            while ((input = br.readLine()) != null) {
                lineas += input + "\n";
            }

            //Crear un archivo con todo el contenido de lineas
            String ruta = "/Users/adrianpisabarrogarcia/Desktop/T1-Programacion-multiproceso/assets/";
            File archivo = new File(ruta + "salida.txt");
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado: " + archivo.getName());
                FileWriter fw = new FileWriter(archivo);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(lineas);
                bw.close();
                System.out.println("Se ha escrito en el archivo");
            } else {
                System.out.println("El archivo ya existe.");
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
