import java.util.concurrent.ThreadLocalRandom;
public class Carrera extends Thread{
    static String ganador;

    Carrera(String s) {
        super(s);
    }

    @Override
    public void run(){

        int rnd = ThreadLocalRandom.current().nextInt(0, 500 + 1);
        for(int i = 0;i>=500;i++) i += rnd;//Bucle para carrera
        if (ganador == null){
            ganador = getName();
            System.out.println("Ganador elegido");
        }

    }

}
