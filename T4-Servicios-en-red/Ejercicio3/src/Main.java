import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {

        //new URL
        URL url = null;
        final String PALABRA = "edificio";
        try {
            url = new URL("https://dle.rae.es/" + PALABRA);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        //URL connection
        URLConnection connection = null;
        try {
            connection = url.openConnection();
            connection.addRequestProperty("User-Agent","Mozilla/5.0");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //InputStream
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Read the stream
        int data = 0;
        StringBuilder html = new StringBuilder();
        try {
            while ((data = inputStream.read()) != -1) {
                 html.append((char) data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Output page.html
        System.out.println(html);

       //FileOutputStream
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("page.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Write the stream
        try {
            outputStream.write(html.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Close the stream
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}