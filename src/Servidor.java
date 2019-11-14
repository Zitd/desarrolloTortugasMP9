import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class Servidor {
    private final int PUERTO = 4321;
    private ServerSocket serverSocket;//Socket correspondiente al servidor
    private Socket socket; //Socket correspondiente al cliente

    public Servidor() throws IOException {
        System.out.println("Iniciando servidor..");
        serverSocket = new ServerSocket(PUERTO);
        socket = new Socket();

    }

    public void initServer() throws IOException {

        do {
            System.out.println("Esperando al cliente...");
            socket = serverSocket.accept();
            System.out.println("Cliente conectado...");
            ArrayList<Tortuga> tortugas = new ArrayList<Tortuga>();
            String menu = "9";
            int salida = 0;
            int test;

            DataOutputStream cliente = new DataOutputStream(socket.getOutputStream()); //Obtener la entrada del cliente cliente.writeUTF("Petición recibida y aceptada");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            cliente.writeUTF("Cliente conectado");


            while(salida == 0){
                System.out.println("El cliente esta usando el menú. . .");
                menu = entrada.readLine();
                menu= menu.trim();

                switch (menu) {
                    case "1":
                        System.out.println("Añadiendo tortuga...");
                        Tortuga tortuga1 = new Tortuga();
                        //Nombre
                        menu = entrada.readLine()+"\n";
                        tortuga1.setNombre(menu.trim());
                        System.out.println("Nombre introducido");
                        //Dorsal
                        menu = entrada.readLine()+"\n";
                        tortuga1.setDorsal( Integer.parseInt(menu.trim()));
                        System.out.println("Dortsal introducida");

                        tortugas.add(tortuga1);
                        cliente.writeUTF("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
                        System.out.println("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
                        System.out.println(tortugas.size()+" tortugas en memoria");

                        break;
                    case "2":
                        break;
                    case "3":
                        System.out.println("Mostrando tortugas");
                        Iterator itr = tortugas.iterator();
                        int i = 0;
                        while(itr.hasNext()){
                            Tortuga tortuga = (Tortuga)itr.next();
                            i++;
                            cliente.writeUTF(i+". Tortuga "+tortuga.getNombre()+" dorsal: "+tortuga.getDorsal());
                        }

                        break;
                    case "4":
                        break;
                    case "5":
                        System.out.println("Saliendo. . .");
                        salida = 1;
                        break;
                    default:

                        break;


                }
            }

            System.out.println("Fin de la conexión");

            serverSocket.close();
        } while (true);
    }
}
