package oop.yatzy;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Täring implements Comparable<Täring> {

    private static Random random = new Random();

    private int arv;
    private boolean veeretada = true;
    private ImageView image = new ImageView();

    Täring() {
        veereta();
    }

    void veereta() {
        veeretada = true;
        arv = random.nextInt(6) + 1;
        image.setTranslateY(0);
        image.setImage(Yatzy.täringutePildid[arv - 1]);
    }

    int getArv() {
        return arv;
    }

    boolean getVeeretada() {
        return veeretada;
    }

    void toggleVeereta() {
        veeretada = !veeretada;
        if (veeretada)
            image.setTranslateY(0);
        else
            image.setTranslateY(-60);
    }

    void setVeeretada(boolean veeretada) {
        if (veeretada != this.veeretada)
            toggleVeereta();
    }

    ImageView getImage() {
        return image;
    }

    @Override
    public int compareTo(Täring o) {
        return Integer.compare(this.getArv(), o.getArv());
    }

    @Override
    public String toString() {
        return "Täring{" +
                "arv=" + getArv() +
                '}';
    }

}