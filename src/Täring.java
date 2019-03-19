import java.util.Random;

public class Täring {

    private static Random random = new Random();
    private int arv = 0;

    int getArv() {
        return arv;
    }

    void veereta() {
        arv = random.nextInt(6) + 1;
    }

}