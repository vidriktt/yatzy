package oop.yatzy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;

public class Yatzy extends Application {

    private static final String[] NIMETUSED = {
            "Ühed", "Kahed", "Kolmed", "Neljad", "Viied", "Kuued",
            "%iSumma", "%iBoonus",
            "Üks paar", "Kaks paari", "Kolmik", "Nelik", "Väike rida", "Suur rida", "Täismaja", "Juhus", "Yatzy",
            "%iKokku"
    };

    private static Label vihje;
    private static HBox täringuteAla;
    private static HBox nuppudeAla;
    private static List<Button> alustamiseNupud = new ArrayList<>();
    private static List<Button> mänguNupud = new ArrayList<>();

    private static Täring[] täringud = new Täring[5];
    private static TäringuteLabel[] tulemus = new TäringuteLabel[18];

    private static int käike = 0;
    private static int viskeid = 0;


    private static void alustaManguga() {
        restartMang();
        vihje.setVisible(true);
        mänguNupud.get(0).setVisible(true);
        täringuteAla.setVisible(true);
        nuppudeAla.getChildren().setAll(mänguNupud);
        veeretaTäringud(true);
    }

    private static void lopetaManuga() {
        vihje.setVisible(false);
        viskeid = 0;
        käike = 0;
        täringuteAla.setVisible(false);
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
        for (Täring taring : täringud)
            if (taring.getVeeretada() || koik)
                taring.veereta();
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

        pea.getColumnConstraints().add(new ColumnConstraints(160)); // Esimese rea laius
        pea.getColumnConstraints().add(new ColumnConstraints(60)); // Teise rea laius

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 18; j++) {
                Pane rida = new HBox();

                if (i == 0) { // Rida käikude nimetuste jaoks
                    /* Väike vahe, et tekst ei oleks vastu seina */
                    Pane vahe = new Pane();
                    vahe.setMinWidth(5);

                    Label lahter = new Label(Yatzy.NIMETUSED[j]);

                    /* Fonti lisamine */
                    if (lahter.getText().startsWith("%i")) {
                        lahter.setStyle("-fx-font: italic 9pt \"Arial\";");
                        lahter.setText(lahter.getText().substring(2));
                    } else {
                        lahter.setStyle("-fx-font: bold 9pt \"Arial\";");
                    }

                    rida.getChildren().addAll(vahe, lahter);
                } else if (i == 1) {
                    TäringuteLabel lahter = tulemus[j];

                    if (j != 6 && j != 7 && j != 17) {
                        int finalJ = j;
                        rida.setOnMouseClicked(event -> {
                            if (viskeid != 0 && lahter.getText().equals("")) {
                                viskeid = 0;
                                käike++;
                                lahter.setInt(punktid(finalJ));
                                veeretaTäringud(true);
                                mänguNupud.get(0).setVisible(true);
                                if (käike >= 15) {
                                    lopetaManuga();
                                }
                            }
                        });
                    }

                    Pane vahe1 = new Pane();
                    Pane vahe2 = new Pane();
                    vahe2.setMinWidth(5);

                    rida.getChildren().addAll(vahe1, lahter, vahe2);
                    HBox.setHgrow(vahe1, Priority.ALWAYS);
                }

                rida.getStyleClass().add("table-grid-cell");

                if (i == 0)
                    rida.getStyleClass().add("first-column");
                if (j == 0)
                    rida.getStyleClass().add("first-row");
                if (j % 2 == 0)
                    rida.getStyleClass().add("second");

                //TODO Events

                pea.add(rida, i, j);
            }
        }

        return pea;
    }

    private static Pane looNuppudeJaTaringute() {
        BorderPane pane = new BorderPane();
        pane.setMinWidth(300);
        pane.setMinHeight(300);
        pane.setPadding(new Insets(0, 0, 0, 20));

        HBox taringuteAla = new HBox();
        taringuteAla.setSpacing(20);
        taringuteAla.setAlignment(Pos.CENTER);
        taringuteAla.setVisible(false);
        pane.setCenter(taringuteAla);

        for (Täring taring : täringud)
            taringuteAla.getChildren().add(taring.getLabel());

        Yatzy.täringuteAla = taringuteAla;

        VBox alumineOsa = new VBox();
        alumineOsa.setAlignment(Pos.CENTER);
        alumineOsa.setSpacing(5);

        vihje = new Label("Veereta uuesti või sisesta väärtus lahtrisse.");
        vihje.setStyle("-fx-font: italic 8pt \"Arial\";");
        vihje.setVisible(false);

        HBox nupud = new HBox();
        nupud.setSpacing(20);
        nupud.setAlignment(Pos.CENTER);

        Button alustamine = new Button("Alusta mänguga!");
        alustamine.setMinWidth(80);
        alustamine.setOnMouseClicked(event -> Yatzy.alustaManguga());

        Button veereta = new Button("Veereta");
        veereta.setMinWidth(80);
        veereta.setOnMouseClicked(event -> {
            Yatzy.veeretaTäringud(false);
            if (viskeid >= 3)
                veereta.setVisible(false);
        });

        Yatzy.nuppudeAla = nupud;
        Yatzy.alustamiseNupud.add(alustamine);
        Yatzy.mänguNupud.add(veereta);

        nupud.getChildren().setAll(Yatzy.alustamiseNupud);

        alumineOsa.getChildren().addAll(vihje, nupud);
        pane.setBottom(alumineOsa);

        return pane;
    }

    @Override
    public void start(Stage lava) {
        BorderPane pea = new BorderPane();
        pea.getStyleClass().add("main-grid");

        pea.setLeft(looTabeliAla());
        pea.setRight(looNuppudeJaTaringute());

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
        restartMang();
        launch(args);
    }

}

