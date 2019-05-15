package oop.yatzy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Yatzy extends Application {

    public static Image[] täringutePildid = new Image[6];

    private static final String[] NIMETUSED = {
            "Ühed", "Kahed", "Kolmed", "Neljad", "Viied", "Kuued",
            "Summa", "Boonus",
            "Üks paar", "Kaks paari", "Kolmik", "Nelik", "Väike rida", "Suur rida", "Täismaja", "Juhus", "Yatzy",
            "Kokku"
    };

    private static VBox tekstid;
    private static HBox täringuteAla;
    private static HBox nuppudeAla;
    private static List<Button> alustamiseNupud = new ArrayList<>();
    private static List<Button> mänguNupud = new ArrayList<>();

    private static Täring[] täringud = new Täring[5];
    private static TäringuteLabel[] tulemus = new TäringuteLabel[18];

    private static int käike = 0;
    private static int viskeid = 0;
    private static String[] seaded = new String[2];


    private static void alustaManguga() {
        ((Label) tekstid.getChildren().get(0)).setText("Valige täringud, mida jätta");
        tekstid.getChildren().get(1).setVisible(true);
        restartMang();
        tekstid.setVisible(true);
        täringuteAla.setVisible(true);
        nuppudeAla.getChildren().setAll(mänguNupud);
        mänguNupud.get(0).setVisible(true);
        veeretaTäringud(true);
    }

    private static void lopetaManuga() {
        tekstid.setVisible(false);
        viskeid = 0;
        käike = 0;
        täringuteAla.setVisible(false);

        StringBuilder faili = new StringBuilder(seaded[0] + "\t" + new Timestamp(System.currentTimeMillis()) + "\t");
        for (TäringuteLabel i : tulemus)
            faili.append("\t").append(i.getInt());
        kirjutaFaili(faili.toString());

        nuppudeAla.getChildren().setAll(alustamiseNupud);
    }

    private static void restartMang() {
        for (int i = 0; i < täringud.length; i++) {
            if (täringud[i] == null)
                täringud[i] = new Täring();
            else
                täringud[i].setVeeretada(true);
        }

        for (int i = 0; i < tulemus.length; i++) {
            if (tulemus[i] == null)
                tulemus[i] = new TäringuteLabel();
            else
                tulemus[i].setText("");
        }
    }

    private static void veeretaTäringud(boolean koik) {
        viskeid++;
        for (Täring täring : täringud)
            if (täring.getVeeretada() || koik)
                täring.veereta();
    }

    private static boolean midagiVeeretada() {
        for (Täring täring : täringud)
            if (täring.getVeeretada())
                return true;
        return false;
    }

    private static int punktid(int lahter) {
        Arrays.sort(täringud);
        int sum = 0;
        int temp = 0;

        if (lahter < 6) { // Ülemine sektsioon
            lahter++;
            for (Täring täring : täringud) {
                if (täring.getArv() == lahter)
                    sum += lahter;
            }

            tulemus[6].addInt(sum);
            if (tulemus[6].getInt() >= 63) { // Boonus
                tulemus[7].setInt(50);
                tulemus[17].addInt(50);
            }
        } else if (lahter == 8) { // Paar
            for (int i = 4; i > 0; i--) {
                if (täringud[i].getArv() == täringud[i - 1].getArv()) {
                    sum = täringud[i].getArv() * 2;
                    break;
                }
            }

        } else if (lahter == 9) { // Kaks paari

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

        } else if (lahter == 10) { // Kolmik

            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv())
                            sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv();
                    }
                }
            }
        } else if (lahter == 11) { // Nelik

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

        } else if (lahter == 12 || lahter == 13) { // Väike rida ja Suur rida
            for (int i = 1; i < täringud.length; i++) {
                if (täringud[i - 1].getArv() + 1 == täringud[i].getArv())
                    sum += täringud[i - 1].getArv();
            }

            if (sum == 10 || sum == 14)
                sum += täringud[4].getArv();
            else
                sum = 0;

        } else if (lahter == 14) { // Täismaja

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

        } else if (lahter == 15) { // Juhus

            for (Täring i : täringud) {
                sum += i.getArv();
            }

        } else if (lahter == 16) { // Yatzy

            int arv = 0;
            temp = täringud[0].getArv();
            for (int i = 1; i < täringud.length; i++) {
                if (temp == täringud[i].getArv())
                    arv++;
            }
            if (arv == 4)
                sum = 50;
            else
                sum = 0;

        }
        tulemus[17].addInt(sum);
        return sum;
    }

    private static Pane looTabeliAla() {
        GridPane pea = new GridPane();
        pea.setAlignment(Pos.CENTER_RIGHT);
        pea.getColumnConstraints().add(new ColumnConstraints(160)); // Esimese rea laius
        pea.getColumnConstraints().add(new ColumnConstraints(60)); // Teise rea laius

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 18; j++) {
                Pane rida = new HBox();

                if (i == 0) { // Rida käikude nimetuste jaoks
                    Label lahter = new Label(Yatzy.NIMETUSED[j]);

                    /* Fonti lisamine */
                    if (j == 6 || j == 7 || j == 17)
                        lahter.setStyle("-fx-font: italic 9pt \"Arial\";");
                    else
                        lahter.setStyle("-fx-font: bold 9pt \"Arial\";");

                    /* Väike vahe, et tekst ei oleks vastu seina */
                    Pane vahe = new Pane();
                    vahe.setMinWidth(5);

                    rida.getChildren().addAll(vahe, lahter);
                } else { // Mängija punktide jaoks
                    TäringuteLabel lahter = tulemus[j];

                    if (j != 6 && j != 7 && j != 17) { // Summa, boonus, kokku v.a
                        int finalJ = j; // Effectively final temp var
                        rida.setOnMouseClicked(event -> {
                            /* Kui mäng käib ja lahtrisse ei ole midagi sisestatud */
                            if (viskeid != 0 && lahter.getText().equals("")) {
                                mänguNupud.get(0).setVisible(true);
                                tekstid.getChildren().get(1).setVisible(true);
                                ((Label) tekstid.getChildren().get(0)).setText("Valige täringud, mida jätta");
                                viskeid = 0;
                                lahter.setInt(punktid(finalJ));
                                veeretaTäringud(true);
                                käike++;
                                if (käike >= 15) {
                                    lopetaManuga();
                                }
                            }
                        });
                    }

                    /* Vahe1 viib numbri paremasse nurka, vahe2 viib numbri natukene vasakule */
                    Pane vahe1 = new Pane();
                    HBox.setHgrow(vahe1, Priority.ALWAYS);
                    Pane vahe2 = new Pane();
                    vahe2.setMinWidth(5);

                    rida.getChildren().addAll(vahe1, lahter, vahe2);

                }

                /* Tabeli kujundus */
                rida.getStyleClass().add("table-grid-cell");
                if (i == 0)
                    rida.getStyleClass().add("first-column");
                if (j == 0)
                    rida.getStyleClass().add("first-row");
                if (j % 2 == 0)
                    rida.setStyle("-fx-background-color: black, #e3e3e3;");

                pea.add(rida, i, j);
            }
        }

        return pea;
    }

    private static Pane looTäringuteAla() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER_LEFT);

        HBox taringuteAla = new HBox();
        taringuteAla.setMinHeight(245);
        taringuteAla.setMinWidth(245);
        taringuteAla.setSpacing(20);
        taringuteAla.setAlignment(Pos.CENTER);
        taringuteAla.setVisible(false);
        pane.add(taringuteAla, 0, 0);

        Yatzy.täringuteAla = taringuteAla;

        for (Täring taring : täringud) {
            taring.getImage().setOnMouseClicked(event -> {
                if (viskeid != 3) {
                    taring.toggleVeereta();

                    if (midagiVeeretada())
                        mänguNupud.get(0).setVisible(true);
                    else
                        mänguNupud.get(0).setVisible(false);
                }
            });
            taringuteAla.getChildren().add(taring.getImage());
        }

        VBox alumineOsa = new VBox();
        alumineOsa.setAlignment(Pos.CENTER);
        alumineOsa.setSpacing(5);

        tekstid = new VBox();
        tekstid.setVisible(false);
        tekstid.setAlignment(Pos.CENTER);
        tekstid.setSpacing(2);

        Label vihje1 = new Label(); // väärtustatakse mujal
        vihje1.setStyle("-fx-font: italic 8pt \"Arial\";");
        Label vihje2 = new Label("või sisesta väärtus soovitud lahtrisse.");
        vihje2.setStyle("-fx-font: italic 8pt \"Arial\";");

        tekstid.getChildren().addAll(vihje1, vihje2);

        HBox nupud = new HBox();
        nupud.setSpacing(20);
        nupud.setAlignment(Pos.CENTER);
        Yatzy.nuppudeAla = nupud;

        Button alustamine = new Button("Alusta mänguga!");
        alustamine.setMinWidth(80);
        alustamine.setOnMouseClicked(event -> Yatzy.alustaManguga());

        Button veereta = new Button("Veereta");
        veereta.setMinWidth(80);
        veereta.setOnMouseClicked(event -> {
            Yatzy.veeretaTäringud(false);
            if (viskeid >= 3) {
                veereta.setVisible(false);
                ((Label) tekstid.getChildren().get(0)).setText("Sisesta väärtus soovitud lahtrisse.");
                tekstid.getChildren().get(1).setVisible(false);
                for (Täring täring : täringud)
                    täring.setVeeretada(false);
            }
        });

        Yatzy.alustamiseNupud.add(alustamine);
        Yatzy.mänguNupud.add(veereta);

        nupud.getChildren().setAll(Yatzy.alustamiseNupud);
        alumineOsa.getChildren().addAll(tekstid, nupud);

        pane.add(alumineOsa, 0, 1);

        return pane;
    }

    private static void kirjutaFaili(String sisu) {
        try (PrintWriter out = new PrintWriter(new FileWriter(new File("tulemused.txt"), true))) {
            out.println(sisu);
        } catch (IOException e) {
            System.out.println("Faili kirjutamisel tekkis viga: " + e.getMessage());
        }
    }

    private static void loadTäringutePildid() {
        for (int i = 0; i < 6; i++)
            täringutePildid[i] = new Image("d" + (i + 1) + ".png", 50, 50, false, false);
    }

    private static void loeSeaded() {
        try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
            seaded[0] = br.readLine().substring(6);
            seaded[1] = br.readLine().substring(7);
        } catch (IOException e) {
            System.out.println("Failist lugemisel tekkis viga: " + e.getMessage());
        }
    }


    @Override
    public void start(Stage lava) {
        HBox pea = new HBox();
        pea.setAlignment(Pos.CENTER);
        pea.setPadding(new Insets(20));
        pea.setSpacing(30);
        pea.setStyle("-fx-background-color: " + seaded[1]);

        Pane tabeliAla = looTabeliAla();
        Pane täringuteAla = looTäringuteAla();
        HBox.setHgrow(tabeliAla, Priority.ALWAYS);
        HBox.setHgrow(täringuteAla, Priority.ALWAYS);
        pea.getChildren().addAll(tabeliAla, täringuteAla);

        Scene stseen = new Scene(pea);
        stseen.getStylesheets().add("yatzy.css");

        stseen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                lopetaManuga();
        });

        lava.setScene(stseen);
        lava.setResizable(true);
        lava.setTitle("Yatzy");
        lava.show();
    }

    public static void main(String[] args) {
        loadTäringutePildid();
        loeSeaded();
        restartMang();
        launch(args);
    }

}