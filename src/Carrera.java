import java.util.concurrent.ThreadLocalRandom;
public class Carrera extends Thread{
    static String ganador;

    public Carrera(String s) {
        super(s);
    }

    @Override
    public void run(){

        int rnd = ThreadLocalRandom.current().nextInt(0, 500 + 1);
       // System.out.println("La carrera de "+ getName()+" ha comenzado");
        for(int i = 0;i>=500;i++){
            i = i + rnd;
        }
       // System.out.println("La carrera de "+ getName()+" ha terminado");

        if (ganador == null){
            ganador = getName();
            System.out.println("Ganador elegido");
        }

    }

}
