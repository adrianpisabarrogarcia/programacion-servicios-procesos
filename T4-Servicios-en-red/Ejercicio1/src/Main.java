import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {


        URL url = null;
        try {
            url = new URL("https://www.egibide.org/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        //Sacar datos de la URL
        System.out.println("Protocolo: " + url.getProtocol());
        System.out.println("Host: " + url.getHost());
        System.out.println("Puerto: " + url.getPort());
        System.out.println("Ruta: " + url.getPath());
        System.out.println("Archivo: " + url.getFile());
        System.out.println("Referencia: " + url.getRef());
        System.out.println("Consulta: " + url.getQuery());
        System.out.println("Usuario: " + url.getUserInfo());
        System.out.println("Autoridad: " + url.getAuthority());
        System.out.println("URL: " + url.toString());
        System.out.println("URL: " + url.toExternalForm());






    }
}