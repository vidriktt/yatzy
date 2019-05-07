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
                if (i == 0) {
                    rida = new HBox();

                    /* Väike vahe, et tekst ei oleks vastu sina */
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

        HBox taringud = new HBox();
        taringud.setSpacing(20);
        taringud.setAlignment(Pos.CENTER);
        taringud.getChildren().addAll(new Label("1"), new Label("2"), new Label("3"), new Label("4"), new Label("5"));

        pane.setCenter(taringud);

        HBox nupud = new HBox();
        nupud.setSpacing(20);
        nupud.setAlignment(Pos.CENTER);
        nupud.getChildren().addAll(new Button("asd"), new Button("asd"));
        pane.setBottom(nupud);

        return pane;
    }


}
