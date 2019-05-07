package oop.yatzy;

import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Täring implements Comparable<Täring> {

    static final Map<Täring, Label> taringud = new HashMap<>() {{
        put(new Täring(), new Label());
        put(new Täring(), new Label());
        put(new Täring(), new Label());
        put(new Täring(), new Label());
        put(new Täring(), new Label());
    }};

    private static Random random = new Random();

    private int arv = 0;
    private boolean veeretada = true;

    int getArv() {
        return arv;
    }

    void veereta() {
        arv = random.nextInt(6) + 1;
        //veeretada = false;
    }

    void toggleVeereta() {
        veeretada = !veeretada;
    }

    public boolean getVeeretada() {
        return veeretada;
    }

    @Override
    public int compareTo(Täring o) {
        return (this.getArv() > o.getArv()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Täring{" +
                "arv=" + arv +
                '}';
    }

}