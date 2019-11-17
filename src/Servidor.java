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
        boolean salida = false;//Esta variable indicará la salida del servidor
        System.out.println("Esperando al cliente...");
        socket = serverSocket.accept();//Aceptamos al cliente
        System.out.println("Cliente conectado...");
        ArrayList<Tortuga> tortugas = new ArrayList<>();//Almacén de tortugas
        String menu;

        DataOutputStream cliente = new DataOutputStream(socket.getOutputStream()); //Obtener la entrada del cliente
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        cliente.writeUTF("Cliente conectado");


        while(!salida){
            System.out.println("El cliente está usando el menú. . .");
            menu = entrada.readLine().trim();

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
    //MOSTRAR UNA UNICA TORTUGA (DESDE UNA POSICION DEL ARRAYLIST)
    private String ViewArraylistItem(ArrayList<Tortuga> tortugas, int n) {
        String frase;
        Tortuga tortuga = tortugas.get(n);
        frase= ("tortuga de nombre " +tortuga.getNombre()+" y dorsal "+tortuga.getDorsal());
        return frase;//Devuelve una frase lista para mostrar
    }
    //EMPEZAR CARRERA
    private void startCarrera(ArrayList<Tortuga> tortugas, DataOutputStream cliente) throws IOException {
        int numGanador;
        String confirmacion = null;
        System.out.println("El cliente ha empezado una carrera");
        if (tortugas.size() > 1) {//Comprobamos que hayan suficientes tortugas
            for (int i = 0; i < tortugas.size(); i++) {
                Carrera temp = new Carrera(" " + i);//Mandamos un numero como representante de las tortugas
                temp.start();
            }
            while (confirmacion == null) {//Repetir hasta que haya sido elegido un ganador
                if (Carrera.ganador != null) {
                    numGanador = Integer.parseInt(Carrera.ganador.trim());//Cogemos el nombre del ganador y lo pasamos a int
                    cliente.writeUTF("Ganador la " + ViewArraylistItem(tortugas,numGanador));
                    System.out.println("Ganador " + ViewArraylistItem(tortugas,numGanador));
                    confirmacion="ACK";//Damos un valor no nulo, que se reiniciara cada vez que se llame este método
                }
            }
            Carrera.ganador = null;//Ponemos el valor del ganador a nulo para repetir otra carrera
        }
        else {
            cliente.writeUTF("fallo");//en caso de que no hayan suficientes tortugas enviaremos un fallo
        }
    }
    //MOSTRAMOS TODAS LAS TORTUGAS DEL ARRAYLIST
    private void showTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente) throws IOException {
        System.out.println("El cliente está viendo las tortugas");

        if(tortugas.size() != 0) {//Si hay tortugas en el arraylist
            System.out.println("Mostrando tortugas");
            cliente.writeUTF("Mostrando tortugas");
            Iterator itr = tortugas.iterator();//Iterator para recorrer el arraylist junto a un bucle while
            int i = 0;
            while (itr.hasNext()) {
                Tortuga tortuga = (Tortuga) itr.next();
                i++; //Esto nos indicará el numero con el que podemos referenciar a nuestra tortuga para eliminarla
                cliente.writeUTF(i + ". Tortuga " + tortuga.getNombre() + " dorsal: " + tortuga.getDorsal());
            }
        }
        else{
            System.out.println("No hay tortugas");
            cliente.writeUTF("No hay tortugas");
        }
        cliente.writeUTF("fin");
    }
    //ELIMINAR TORTUGA
    private void delTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {
        String readCli;
        int eliminar;
        System.out.println("El cliente esta eliminando una tortuga");
        if(tortugas.size() != 0){//Si no hay tortugas que se salte el proceso e informe al usuario
            System.out.println("Eliminando tortugas");
            readCli = entrada.readLine()+"\n";
            eliminar = Integer.parseInt(readCli.trim())-1;//Aquí pasamos del valor recibido al valor necesario
            if(eliminar<tortugas.size()){//Si el numero se encuentra en la lista que proceda con la eliminación
                tortugas.remove(eliminar);
                cliente.writeUTF("La tortuga ha sido eliminada correctamente");
            }
            else {
                cliente.writeUTF("Esa tortuga no se encuentra en la lista");//Fallo Núm. Demasiado alto
            }
        }
        else{
            cliente.writeUTF("No existen tortugas que eliminar");//Fallo ArrayList vacía
        }
    }
    //AÑADIR TORTUGA
    private void addTortuga(ArrayList<Tortuga> tortugas, DataOutputStream cliente, BufferedReader entrada) throws IOException {
        String readCli;
        System.out.println("Añadiendo tortuga...");
        Tortuga tortuga1 = new Tortuga(); //Creamos una nueva tortuga
        //Nombre
        readCli = entrada.readLine()+"\n";
        tortuga1.setNombre(readCli.trim());
        System.out.println("Nombre introducido");
        //Dorsal
        readCli = entrada.readLine()+"\n";
        tortuga1.setDorsal( Integer.parseInt(readCli.trim()));
        System.out.println("Dorsal introducido");
        //La añadimos a la lista
        tortugas.add(tortuga1);
        cliente.writeUTF("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
        System.out.println("La tortuga de nombre "+tortuga1.getNombre()+" y dorsal "+tortuga1.getDorsal()+" ha sido creada correctamente");
        System.out.println(tortugas.size()+" tortugas en memoria");//Indicamos las tortugas guardadas
    }
}