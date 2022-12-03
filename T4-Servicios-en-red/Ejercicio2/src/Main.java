import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        //Objeto URL
        URL url = null;
        try {
            url = new URL("https://www.egibide.org/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        //Objeto HttpURLConnection
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Código de resposta
        int codigoResposta = 0;
        try {
            codigoResposta = http.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Imprime o código de resposta
        System.out.println(codigoResposta);


        //Imprimir contenido de la página
        InputStream is = null;
        try {
            is = http.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int byteLeido = 0;
        try {
            while ((byteLeido = is.read()) != -1) {
                System.out.print((char) byteLeido);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}