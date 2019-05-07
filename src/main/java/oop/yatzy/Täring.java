package oop.yatzy;

import java.util.Random;

public class Täring implements Comparable<Täring> {

    private static Random random = new Random();
    private int arv = 0;

    int getArv() {
        return arv;
    }

    void veereta() {
        arv = random.nextInt(6) + 1;
    }

    @Override
    public int compareTo(Täring o) {
        return Integer.compare(this.getArv(), o.getArv());
    }

    @Override
    public String toString() {
        return "Täring{" +
                "arv=" + arv +
                '}';
    }
}