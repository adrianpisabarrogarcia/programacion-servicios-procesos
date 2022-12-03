import java.net.Socket;

public class Hilo extends Thread {

    Socket c;

    public Hilo(Socket c) {
        this.c = c;
    }

    public void run() {
        System.out.println("Hola");
    }
}
