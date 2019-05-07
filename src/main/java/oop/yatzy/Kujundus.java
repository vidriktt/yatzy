package oop.yatzy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

class Kujundus {

    /**
     * Loob vastavalt GridPane kasutades uue tabeli ja
     * lisab sellele ka mõned eventid juurde.
     *
     * @return Tagastab tabeli
     */
    static Pane looTabel() {
        GridPane pea = new GridPane();

        pea.getColumnConstraints().add(new ColumnConstraints(160)); // Esimese rea laius
        pea.getColumnConstraints().add(new ColumnConstraints(60)); // Teise rea laius

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 18; j++) {
                Pane rida;

                if (i == 0) { // Rida käikude nimetuste jaoks
                    rida = new HBox();

                    /* Väike vahe, et tekst ei oleks vastu seina */
                    Pane vahe = new Pane();
                    vahe.setMinWidth(5);

                    Label nimi = new Label(Yatzy.NIMETUSED[j]);

                    /* Fonti lisamine */
                    Font font;
                    if (nimi.getText().startsWith("%i")) {
                        font = Font.font("System", FontPosture.ITALIC, 12.0);
                        nimi.setText(nimi.getText().substring(2));
                    } else {
                        font = Font.font("System", FontWeight.BOLD, 12.0);
                    }
                    nimi.setFont(font);

                    rida.getChildren().addAll(vahe, nimi);
                } else {
                    rida = new Pane();
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

    static Pane looNupud() {
        BorderPane pane = new BorderPane();
        pane.setMinWidth(300);
        pane.setMinHeight(300);
        pane.setPadding(new Insets(0, 0, 0, 20));

        HBox taringuteAla = new HBox();
        taringuteAla.setSpacing(20);
        taringuteAla.setAlignment(Pos.CENTER);
        taringuteAla.setVisible(false);
        pane.setCenter(taringuteAla);

        Täring.taringud.forEach((k, v) -> {
            v.setText(String.valueOf(k.getArv()));
            taringuteAla.getChildren().add(v);
        });

        Yatzy.taringuteAla = taringuteAla;

        HBox nupud = new HBox();
        nupud.setSpacing(20);
        nupud.setAlignment(Pos.CENTER);

        Button alustamine = new Button("Alusta mänguga!");
        alustamine.setMinWidth(80);
        alustamine.setOnMouseClicked(event -> Yatzy.alustaManguga());

        Button veereta = new Button("Veereta");
        veereta.setMinWidth(80);
        veereta.setOnMouseClicked(event -> Yatzy.täringuViskamine());

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


}
