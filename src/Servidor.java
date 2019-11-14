import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


class Servidor {
    private ServerSocket serverSocket;//Socket correspondiente al servidor
    private Socket socket; //Socket correspondiente al cliente

    Servidor() throws IOException {
        System.out.println("Iniciando servidor..");
        int PUERTO = 4321;
        serverSocket = new ServerSocket(PUERTO);
        socket = new Socket();

    }

    void initServer() throws IOException {
        boolean salida = false;

        System.out.println("Esperando al cliente...");
        socket = serverSocket.accept();
        System.out.println("Cliente conectado...");
        ArrayList<Tortuga> tortugas = new ArrayList<>();
        String menu;

        DataOutputStream cliente = new DataOutputStream(socket.getOutputStream()); //Obtener la entrada del cliente cliente.writeUTF("Petición recibida y aceptada");
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        cliente.writeUTF("Cliente conectado");


        while(!salida){
            System.out.println("El cliente esta usando el menú. . .");
            menu = entrada.readLine();
            menu= menu.trim();

            switch (menu) {
                case "1"://Añadir tortuga
                    addTortuga(tortugas, cliente, entrada);
                    break;
                case "2": //Eliminar tortuga
                    delTortuga(tortugas, cliente, entrada);
                    break;
                case "3": //Mostrar tortuga
                    showTortuga(tortugas, cliente);
                    break;
                case "4"://Carrera
                    startCarrera(tortugas, cliente);
                    break;
                case "5":
                    System.out.println("Saliendo. . .");
                    salida = true;
                    break;
                default:
                    System.out.println("Fallo Default"); // En caso de que el cliente y el servidor se desincronizan
                    break;
            }
        }
        System.out.println("Fin de la conexión");
        socket.close();
        serverSocket.close();
    }

    private String ViewArraylistItem(ArrayList<Tortuga> tortugas, int n) {
        String frase;
        Tortuga tortuga = tortugas.get(n);
        frase= ("tortuga de nombre " +tortuga.getNombre()+" y dorsal "+tortuga.getDorsal());
        return frase;
    }

    private void startCarrera(ArrayList<Tortuga> tortugas, DataOutputStream cliente) throws IOException {
        int numGanador;
        String confirmacion = null;
        System.out.println("El cliente ha empezado una carrera");
        if (tortugas.size() > 1) {
            for (int i = 0; i < tortugas.size(); i++) {
                Carrera temp = new Carrera(" " + i);
                temp.start();
            }
            while (confirmacion == null) {
                System.out.println("wile");
                if (Carrera.ganador != null) {
                    System.out.println("ifi");
                    numGanador = Integer.parseInt(Carrera.ganador.trim());
                    cliente.writeUTF("Ganador la " + ViewArraylistItem(tortugas,numGanador));
                    System.out.println("Ganador " + ViewArraylistItem(tortugas,numGanador));
                    confirmacion="ACK";
                }
            }
            Carrera.ganador = null;
        }
        else {
            cliente.writeUTF("fallo");
        }
    }

    private void showTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente) throws IOException {//mostramos las tortugas
        System.out.println("El cliente está viendo las tortugas");

        if(tortugas.size() != 0) {
            System.out.println("Mostrando tortugas");
            cliente.writeUTF("Mostrando tortugas");
            Iterator itr = tortugas.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Tortuga tortuga = (Tortuga) itr.next();
                i++;
                cliente.writeUTF(i + ". Tortuga " + tortuga.getNombre() + " dorsal: " + tortuga.getDorsal());
            }
        }
        else{
            System.out.println("No hay tortugas");
            cliente.writeUTF("No hay tortugas");
        }
        cliente.writeUTF("fin");
    }

    private void delTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {//eliminamos tortuga
        String readCli;
        int eliminar;
        System.out.println("El cliente esta eliminando una tortuga");
        if(tortugas.size() != 0){
            System.out.println("Eliminando tortugas");
            readCli = entrada.readLine()+"\n";
            eliminar = Integer.parseInt(readCli.trim())-1;
            if(eliminar<tortugas.size()){
            tortugas.remove(eliminar);
            cliente.writeUTF("La tortuga ha sido eliminada correctamente");
            }
            else {
                cliente.writeUTF("Esa tortuga no se encuentra en la lista");
            }
        }
        else{
            cliente.writeUTF("No existen tortugas que eliminar");
        }
    }

    private void addTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {//añadimos tortugas
        String readCli;
        System.out.println("Añadiendo tortuga...");
        Tortuga tortuga1 = new Tortuga();
        //Nombre
        readCli = entrada.readLine()+"\n";
        tortuga1.setNombre(readCli.trim());
        System.out.println("Nombre introducido");
        //Dorsal
        readCli = entrada.readLine()+"\n";
        tortuga1.setDorsal( Integer.parseInt(readCli.trim()));
        System.out.println("Dortsal introducida");

        tortugas.add(tortuga1);
        cliente.writeUTF("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
        System.out.println("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
        System.out.println(tortugas.size()+" tortugas en memoria");
    }
}
