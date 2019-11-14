import java.util.concurrent.ThreadLocalRandom;
public class Carrera implements Runnable{
    @Override
    public void run(){
        int rnd = ThreadLocalRandom.current().nextInt(0, 500 + 1);
        System.out.println("La carrera ha comenzado");
        for(int i = 0;i>=500;i++){
            i = i + rnd;
        }
        //return tortuga;
    }

}
