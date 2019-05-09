package oop.yatzy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private static HBox taringuteAla;
    private static HBox nuppudeAla;
    private static List<Button> alustamiseNupud = new ArrayList<>();
    private static List<Button> manguNupud = new ArrayList<>();

    private static Täring[] taringud = new Täring[5];
    private static TaringuLabel[] tulemus = new TaringuLabel[18];

    private static int käike = 0;
    private static int viskeid = 0;


    private static void alustaManguga() {
        restartMang();
        manguNupud.get(0).setVisible(true);
        taringuteAla.setVisible(true);
        nuppudeAla.getChildren().setAll(manguNupud);
        veeretaTäringud(true);
    }

    private static void lopetaManuga() {
        viskeid = 0;
        taringuteAla.setVisible(false);
        nuppudeAla.getChildren().setAll(alustamiseNupud);
    }

    private static void restartMang() {
        for (int i = 0; i < taringud.length; i++) {
            if (taringud[i] == null)
                taringud[i] = new Täring();
            else
                taringud[i].setVeeretada(true);
        }

        for (int i = 0; i < tulemus.length; i++) {
            if (tulemus[i] == null)
                tulemus[i] = new TaringuLabel();
            else
                tulemus[i].setText("");
        }
    }

    private static void veeretaTäringud(boolean koik) {
        viskeid++;
        for (Täring taring : taringud)
            if (taring.getVeeretada() || koik)
                taring.veereta();
    }


    private static int punktid(int lahter) {
        Arrays.sort(taringud);
        int sum = 0;
        int temp = 0;

        if (lahter < 6) { // Ülemine sektsioon
            lahter++;
            for (Täring täring : taringud) {
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
                if (taringud[i].getArv() == taringud[i - 1].getArv()) {
                    sum = taringud[i].getArv() * 2;
                    break;
                }
            }

        } else if (lahter == 9) { // Kaks paari

            boolean leitud = false;
            int paar = 0;

            for (int i = 0; i < taringud.length - 1; i++) {
                if (taringud[i].getArv() == paar)
                    continue;
                for (int j = i + 1; j < taringud.length; j++) {
                    if (taringud[i].getArv() == taringud[j].getArv()) {
                        if (taringud[i].getArv() != paar) {
                            if (leitud)
                                sum = (paar + taringud[i].getArv()) * 2;
                            paar = taringud[i].getArv();
                            leitud = true;
                            break;
                        }
                    }
                }
            }

        } else if (lahter == 10) { // Kolmik

            for (int i = 0; i < taringud.length - 2; i++) {
                for (int j = i + 1; j < taringud.length - 1; j++) {
                    for (int k = j + 1; k < taringud.length; k++) {
                        if (taringud[i].getArv() == taringud[j].getArv() && taringud[j].getArv() == taringud[k].getArv())
                            sum = taringud[i].getArv() + taringud[j].getArv() + taringud[k].getArv();
                    }
                }
            }
        } else if (lahter == 11) { // Nelik

            for (int i = 0; i < taringud.length - 2; i++) {
                for (int j = i + 1; j < taringud.length - 1; j++) {
                    for (int k = j + 1; k < taringud.length; k++) {
                        for (int l = k + 1; l < taringud.length; l++) {
                            if (taringud[i].getArv() == taringud[j].getArv() && taringud[j].getArv() == taringud[k].getArv() && taringud[k].getArv() == taringud[l].getArv())
                                sum = taringud[i].getArv() + taringud[j].getArv() + taringud[k].getArv() + taringud[l].getArv();
                        }
                    }
                }
            }

        } else if (lahter == 12 || lahter == 13) { // Väike rida ja Suur rida
            for (int i = 1; i < taringud.length; i++) {
                if (taringud[i - 1].getArv() + 1 == taringud[i].getArv())
                    sum += taringud[i - 1].getArv();
            }

            if (sum == 10 || sum == 14)
                sum += taringud[4].getArv();
            else
                sum = 0;

        } else if (lahter == 14) { // Täismaja

            for (int i = 0; i < taringud.length - 2; i++) {
                for (int j = i + 1; j < taringud.length - 1; j++) {
                    for (int k = j + 1; k < taringud.length; k++) {
                        if (taringud[i].getArv() == taringud[j].getArv() && taringud[j].getArv() == taringud[k].getArv()) {
                            sum = taringud[i].getArv() + taringud[j].getArv() + taringud[k].getArv();
                            int[] tempL = new int[2];
                            for (Täring täring : taringud) {
                                if (!(täring.getArv() == taringud[i].getArv())) {
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

            for (Täring i : taringud) {
                sum += i.getArv();
            }

        } else if (lahter == 16) { // Yatzy

            int arv = 0;
            temp = taringud[0].getArv();
            for (int i = 1; i < taringud.length; i++) {
                if (temp == taringud[i].getArv())
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
                    Font font;
                    if (lahter.getText().startsWith("%i")) {
                        font = Font.font("System", FontPosture.ITALIC, 12.0);
                        lahter.setText(lahter.getText().substring(2));
                    } else {
                        font = Font.font("System", FontWeight.BOLD, 12.0);
                    }
                    lahter.setFont(font);

                    rida.getChildren().addAll(vahe, lahter);
                } else if (i == 1) {
                    TaringuLabel lahter = tulemus[j];

                    if (j != 6 && j != 7 && j != 17) {
                        int finalJ = j;
                        rida.setOnMouseClicked(event -> {
                            if (viskeid != 0 && lahter.getText().equals("")) {
                                viskeid = 0;
                                käike++;
                                lahter.setInt(punktid(finalJ));
                                veeretaTäringud(true);
                                manguNupud.get(0).setVisible(true);
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

        for (Täring taring : taringud)
            taringuteAla.getChildren().add(taring.getLabel());

        Yatzy.taringuteAla = taringuteAla;

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

        Button lopetamine = new Button("Lõpeta mäng");
        lopetamine.setMinWidth(80);
        lopetamine.setOnMouseClicked(event -> Yatzy.lopetaManuga());

        Yatzy.nuppudeAla = nupud;
        Yatzy.alustamiseNupud.add(alustamine);
        Yatzy.manguNupud.add(veereta);
        Yatzy.manguNupud.add(lopetamine);

        nupud.getChildren().setAll(Yatzy.alustamiseNupud);
        pane.setBottom(nupud);

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

        lava.setScene(stseen);
        lava.setResizable(false);
        lava.setTitle("Yatzy");
        lava.show();
    }

    public static void main(String[] args) {
        restartMang();
        launch(args);
    }


}

