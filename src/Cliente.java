import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Cliente {

    private Socket socket;

    Cliente() throws IOException {
        System.out.println("Iniciando cliente...");
        int PUERTO = 4321;
        String HOST = "localhost";
        socket = new Socket(HOST, PUERTO);
    }

    void initCliente() throws IOException {
        //Variables
        Scanner reader = new Scanner(System.in);
        String menuTortuga;
        int salida = 0;
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
            salidaServidor.writeUTF(menuTortuga+"\n");//Enviamos esa información al servidor, Necesita \n para decir que es el final de la linea

            switch(menuTortuga){ //Switch como menú
                case "1"://Añadir tortuga
                    addTortuga(reader, salidaServidor, din);
                    break;
                case "2": //Eliminar tortuga
                    delTortuga(reader, salidaServidor, din);
                    break;
                case "3": //Ver tortugas
                    seeTortugas(din);
                    break;
                case "4": //Empezar carrera
                    cliCarrera(din);
                    break;
                case "5": //Salir
                    System.out.println("Cerrando servidor. . .");
                    salidaServidor.writeUTF("5"+"\n");
                    salida = 1; // Fin de este bucle
                    break;
                default://En caso de introducir cualquier otro valor
                    System.out.println("Escribe solo un número del 1 al 5");
                    break;
            }
        }

        salidaServidor.writeUTF("Finalizando conexión. . .");
        System.out.println("Cerrando cliente. . .");
        socket.close();
    }
    //ELIMINAR TORTUGA
    private void delTortuga(Scanner reader, DataOutputStream salidaServidor, DataInputStream din) throws IOException {
        System.out.println("¿Que tortuga deseas eliminar?");
        salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola y enviamos
        System.out.println(din.readUTF());//Recibimos confirmación
    }
    //EMPEZAR CARRERA
    private void cliCarrera(DataInputStream din) throws IOException {
        String readServ;
        System.out.println("Empezando carrera");
        readServ = din.readUTF();
        if(readServ.equals("fallo")) {//En caso de que se quiera iniciar la carrera con 0 o 1 tortugas
            System.out.println("No hay suficientes tortugas");
        }
        else {
            System.out.println(readServ);//Escribimos al ganador
        }
    }
    //VER TORTUGAS
    private void seeTortugas(DataInputStream din) throws IOException {
        String mensajeServer;
        System.out.println(din.readUTF()+"\n");
        while(true) {
            mensajeServer = din.readUTF();
            if (mensajeServer.equals("fin")){
                break;
            }
            System.out.println(mensajeServer);

        }
    }
    //AÑADIR TORTUGA
    private void addTortuga(Scanner reader, DataOutputStream salidaServidor, DataInputStream din) throws IOException {
        System.out.println("Introduce el nombre de la tortuga");
        salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola y enviamos
        System.out.println("Introduce el dorsal de la tortuga");
        salidaServidor.writeUTF(reader.nextLine()+"\n"); //Leemos consola y enviamos

        System.out.println(din.readUTF());// este mensaje nos sirve de confirmación
    }

}