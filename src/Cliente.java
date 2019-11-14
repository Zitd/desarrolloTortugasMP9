import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    private final int PUERTO = 4321;
    private final String HOST = "localhost";
    private Socket socket;

    public Cliente() throws IOException {
        System.out.println("Iniciando cliente...");
        socket = new Socket(HOST, PUERTO);
    }

    public void initCliente() throws IOException {
        //Variables
        Scanner reader = new Scanner(System.in);
        String menuTortuga;
        int salida = 0;
        int test;
        String mensajeServer;
        DataOutputStream salidaServidor;
        DataInputStream din = new DataInputStream(socket.getInputStream());
        salidaServidor = new DataOutputStream(socket.getOutputStream());
        System.out.println(din.readUTF());//Recibimos la confirmacion del servidor
        while (salida == 0){ //bucle while de menú

            //MENU
            System.out.println(" ");
            System.out.println("¿Que deseas hacer?\n");
            System.out.println("1. Añadir tortuga");
            System.out.println("2. Eliminar tortuga");
            System.out.println("3. Ver tortugas");
            System.out.println("4. Empezar carrera");
            System.out.println("5. Salir\n");


            menuTortuga = reader.nextLine(); //Leemos datos por consola para el case

            salidaServidor.writeUTF(menuTortuga+"\n");
                      /*Enviamos esa información al servidor
            Necesita \n para decir que eso es to.do que es el final de la linea*/

            switch(menuTortuga){ //case de menú
                case "1"://Añadir tortuga
                    System.out.println("Introduce el nombre de la tortuga");
                    salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola

                    System.out.println("Introduce el dorsal de la tortuga");
                    salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola

                    System.out.println(din.readUTF());// este mensaje nos sirve de confirmación
                    break;

                case "2": //Eliminar tortuga
                    System.out.println("¿Que tortuga deseas eliminar?");
                    salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola
                    System.out.println(din.readUTF());//Eliminado
                    break;

                case "3": //Ver tortugas

                    System.out.println("Mostrando tortugas\n");
                    while(true) {
                        mensajeServer = din.readUTF();
                        if (mensajeServer.equals("fin")){
                            break;
                        }
                        System.out.println(mensajeServer);

                    }


                    break;

                case "4": //Empezar carrera
                    System.out.println("Empezando carrera");
                    break;

                case "5": //Salir
                    System.out.println("Cerrando servidor. . .");
                        salidaServidor.writeUTF("5"+"\n");

                    salida = 1; // Fin de este bucle
                    break;
                default:
                    System.out.println("Escribe solo un número del 1 al 5");
                    break;
            }

        }
        salidaServidor.writeUTF("Finalizando conexión. . .");
        System.out.println("Cerrando cliente. . .");

        socket.close();
    }

}
