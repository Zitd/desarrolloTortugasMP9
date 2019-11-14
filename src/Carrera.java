import java.util.concurrent.ThreadLocalRandom;
public class Carrera extends Thread{
    static String ganador;

    Carrera(String s) {
        super(s);
    }

    @Override
    public void run(){

        int rnd = ThreadLocalRandom.current().nextInt(0, 500 + 1);//Creamos un numero aleatorio entre 0 y 500
        for(int i = 0;i>=500;i++) i += rnd;//Bucle para carrera
        if (ganador == null){//El primero que entre cerrará este if para los demas que lo intenten
            ganador = getName();
            System.out.println("Ganador elegido");
        }

    }

}
