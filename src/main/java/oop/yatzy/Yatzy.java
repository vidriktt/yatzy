package oop.yatzy;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Yatzy extends Application {

    private static Täring[] täringud = {new Täring(), new Täring(), new Täring(), new Täring(), new Täring()};
    private static Integer[] tulemus = new Integer[16];
    private static Integer[] kokku = {0, 0, 0};
    private static int käike = 0;
    private static int viskeid = 0;

    static final String[] NIMETUSED = {
            "Ühed", "Kahed", "Kolmed", "Neljad", "Viied", "Kuued",
            "%iSumma", "%iBoonus",
            "Üks paar", "Kaks paari", "Kolmik", "Nelik", "Väike rida", "Suur rida", "Täismaja", "Juhus", "Yatzy",
            "%iKokku"
    };

    private static void täringuViskamine(String sisend) {
        /* Kui täringuid ei pea enam veeretama */
        if (sisend.equals("11111")) {
            viskeid = 3;
            return;
        }

        /* Veeretab nõutud täringud uuesti */
        int temp = 0;
        for (Character number : sisend.toCharArray()) {
            if (number == '0')
                täringud[temp].veereta();
            temp++;
        }
        viskeid++;

        /* Prindib kõik täringud välja */
        System.out.print("Sinu visked: ");
        for (Täring täring : täringud)
            System.out.print(täring.getArv() + " ");
        System.out.println();
    }

    private static int punktid(int lahter) {
        int sum = 0;
        int temp = 0;

        if (lahter >= 1 && lahter <= 6) { // Ülemine sektsioon

            for (Täring täring : täringud) {
                if (täring.getArv() == lahter)
                    sum += lahter;
            }
            kokku[0] += sum;
            if (kokku[0] >= 63) { // Boonus
                kokku[1] = 50;
                kokku[2] += 50;
            }
            return sum;

        } else if (lahter == 7) { // Paar

            Arrays.sort(täringud);
            for (int i = 4; i > 0; i--) {
                if (täringud[i].getArv() == täringud[i - 1].getArv()) {
                    sum = täringud[i].getArv() * 2;
                    break;
                }
            }
            return sum;

        } else if (lahter == 8) { // Kaks paari

            boolean leitud = false;
            int paar = 0;

            for (int i = 0; i < täringud.length - 1; i++) {
                if (täringud[i].getArv() == paar)
                    continue;
                for (int j = i + 1; j < täringud.length; j++) {
                    if (täringud[i].getArv() == täringud[j].getArv()) {
                        if (täringud[i].getArv() != paar) {
                            if (leitud)
                                sum = (paar + täringud[i].getArv()) * 2;
                            paar = täringud[i].getArv();
                            leitud = true;
                            break;
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 9) { // Kolmik

            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv())
                            sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv();
                    }
                }
            }
            return sum;

        } else if (lahter == 10) { // Nelik

            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        for (int l = k + 1; l < täringud.length; l++) {
                            if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv() && täringud[k].getArv() == täringud[l].getArv())
                                sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv() + täringud[l].getArv();
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 11 || lahter == 12) { // Väike rida ja Suur rida

            Arrays.sort(täringud);
            for (int i = 1; i < täringud.length; i++) {
                if (täringud[i - 1].getArv() + 1 == täringud[i].getArv())
                    sum += täringud[i - 1].getArv();
            }
            if (sum == 10 || sum == 14)
                return sum + täringud[4].getArv();
            else
                return 0;

        } else if (lahter == 13) { // Täismaja

            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv()) {
                            sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv();
                            int[] tempL = new int[2];
                            for (Täring täring : täringud) {
                                if (!(täring.getArv() == täringud[i].getArv())) {
                                    tempL[temp] = täring.getArv();
                                    temp++;
                                }
                            }
                            if (tempL[0] == tempL[1])
                                sum += tempL[0] * 2;
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 14) { // Juhus

            for (Täring i : täringud) {
                sum += i.getArv();
            }
            return sum;

        } else if (lahter == 15) { // Yatzy

            int arv = 0;
            temp = täringud[0].getArv();
            for (int i = 1; i < täringud.length; i++) {
                if (temp == täringud[i].getArv())
                    arv++;
            }
            if (arv == 4)
                return 50;
            else
                return 0;

        } else
            return 0; // Kui ei täitnud kriteeriumeid, siis lisab lahtrisse 0
    }

    @Override
    public void start(Stage lava) {
        BorderPane pea = new BorderPane();
        pea.getStyleClass().add("main-grid");

        pea.setLeft(Kujundus.looTabel());
        pea.setRight(Kujundus.looNupud());

        Scene stseen = new Scene(pea);
        stseen.getStylesheets().add("yatzy.css");

        lava.setScene(stseen);
        lava.setResizable(false);
        lava.setTitle("Yatzy");
        lava.show();
    }

    public static void main(String[] args) {
        launch(args);
//        Scanner scanner = new Scanner(System.in);
//        String sisend;
//        int punkt;
//
//        while (käike < 15) {
//            puhastaScreen();
//            prindiTabel();
//
//            /* Täringute viskamine */
//            täringuViskamine("00000");
//            while (viskeid != 3) {
//                System.out.print("Millised täringud säilitada (1 - säilitamiseks, 0 - veeretamiseks)? ");
//                sisend = scanner.nextLine().trim();
//                while (!sisend.matches("[0-1]+") || sisend.length() != 5) {
//                    System.out.print("Vigane sisend! Palun kirjtage õiged numbrid (nt 10000): ");
//                    sisend = scanner.nextLine().trim();
//                }
//                täringuViskamine(sisend);
//            }
//            viskeid = 0;
//            System.out.println();
//
//            /* Lahtri numbri saavutamine */
//            System.out.print("Millisesse lahtrisse soovite punktid sisestada? ");
//            sisend = scanner.nextLine().trim();
//            int lahter;
//            do {
//                lahter = -1;
//                if (sisend.matches("[0-9]+")) // Kui sisend koosneb ainult numbritest.
//                    lahter = Integer.parseInt(sisend);
//
//                if (!(0 < lahter && lahter < 16)) {
//                    System.out.print("Palun sisestage sobiv lahtrinumber: ");
//                    sisend = scanner.nextLine().trim();
//                } else if (tulemus[lahter - 1] != null) {
//                    System.out.println("Sellesse lahtrisse on juba number sisestatud.");
//                    sisend = " ";
//                } else break;
//            } while (true);
//
//            /* Punktide kirjapanemine */
//            punkt = punktid(lahter);
//            tulemus[lahter - 1] = punkt;
//            kokku[2] += punkt;
//            käike++;
//
//        }
//        puhastaScreen();
//        prindiTabel();
//
//        System.out.println("Mäng on läbi!\nPalju õnne, kokku Saite " + kokku[2] + " punkti.");
    }


}

