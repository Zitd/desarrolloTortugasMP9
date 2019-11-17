import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
public class Carrera extends Thread{
    static String ganador;

    Carrera(String s) {
        super(s);
    }

    @Override
    public void run(){
        int runner = Integer.parseInt(getName().trim())+1;
        System.out.println("Carrera de " + runner+ " ha empezado");
        int rnd = ThreadLocalRandom.current().nextInt(0, 500 + 1);//Creamos un numero aleatorio entre 0 y 500
        for(int i = 0;i>=500;i++) i += rnd;//Bucle para carrera
        System.out.println("Carrera de " + runner+ " ha terminado");
        if (ganador == null){//El primero que entre cerrará este if para los demás que lo intenten
            ganador = getName();
            System.out.println("Ganador "+runner+" elegido");
        }
    }

}
