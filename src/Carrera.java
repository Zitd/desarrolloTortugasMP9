import java.util.Random;

public class Carrera implements Runnable{
    @Override
    public void run(){
        Random rnd = new Random();
        for(int i = 0;i<=500;i++){
            i = i + rnd;

        }
        System.out.println("La carrera ha comenzado");
    }

}
