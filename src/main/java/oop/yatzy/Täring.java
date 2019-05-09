package oop.yatzy;

import javafx.scene.control.Label;

import java.util.Random;

public class Täring implements Comparable<Täring> {

    private static Random random = new Random();

    private boolean veeretada = true;
    private TaringuLabel label = new TaringuLabel();

    public Täring() {
        veereta();
    }

    void veereta() {
        label.setInt(random.nextInt(6) + 1);
        //veeretada = false;
    }

    int getArv() {
        return Integer.parseInt(label.getText());
    }

    boolean getVeeretada() {
        return veeretada;
    }

    void toggleVeereta() {
        veeretada = !veeretada;
    }

    void setVeeretada(boolean veeretada) {
        this.veeretada = veeretada;
    }

    public Label getLabel() {
        return label;
    }

    @Override
    public int compareTo(Täring o) {
        return (this.getArv() > o.getArv()) ? 1 : -1;
    }

    @Override
    public String toString() {
        return "Täring{" +
                "arv=" + getArv() +
                '}';
    }

}