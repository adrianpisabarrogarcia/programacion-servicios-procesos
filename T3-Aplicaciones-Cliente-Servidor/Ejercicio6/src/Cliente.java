import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        //puerto
        final int PUERTO = 5000;
        //ip
        final String HOST = "localhost";

        //Conexión con el servidor
        try {
            Socket peticion = new Socket(HOST, 6000);
            System.out.println("Conexión establecida con el servidor");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scanner sc = new Scanner(System.in);
        menu();
        Boolean salir = false;
        while(!salir){
            menu();
            int opcion = sc.nextInt();
            String envio = "";
            switch (opcion){
                case 1:
                    //suma
                    int[] num1 = pedirNumeros();
                    envio = "suma " + num1[0] + " " + num1[1];
                    break;
                case 2:
                    //resta
                    int[] num2 = pedirNumeros();
                    envio = "resta " + num2[0] + " " + num2[1];

                    break;
                case 3:
                    //multiplicacion
                    int[] num3 = pedirNumeros();
                    envio = "multiplicacion " + num3[0] + " " + num3[1];
                    break;
                case 4:
                    //division
                    int[] numeros = pedirNumeros();
                    envio = "division " + numeros[0] + " " + numeros[1];

                    break;
                    case 5:
                    //salir
                    salir = true;
                default:
                    System.out.println("Opción incorrecta");
                    break;
            }

            if(!salir){
                try {
                    //enviar mensaje
                    peticion.getOutputStream().write(envio.getBytes());
                    //recibir mensaje
                    //mostrar mensaje
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //recibir mensaje
                //mostrar mensaje
            }
        }
    }


    public static void menu(){
        System.out.println("Vamos a realizar operaciones:" +
                "\n1. Suma" +
                "\n2. Resta" +
                "\n3. Multiplicación" +
                "\n4. División" +
                "\n5. Salir");
    }

    public static int[] pedirNumeros(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el primer número");
        int num1 = sc.nextInt();
        System.out.println("Introduce el segundo número");
        int num2 = sc.nextInt();
        int[] numeros = {num1, num2};
        return numeros;
    }
}