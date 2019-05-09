package oop.yatzy;

import javafx.scene.control.Label;

public class TaringuLabel extends Label {

    public void setInt(int x) {
        setText(String.valueOf(x));
    }

    public void addInt(int x) {
        if (getText().equals(""))
            setText("0");
        setText(String.valueOf(x + Integer.parseInt(getText())));
    }

    public int getInt() {
        if (getText().equals(""))
            return -1;
        return Integer.parseInt(getText());
    }

}
