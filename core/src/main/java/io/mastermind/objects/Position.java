package io.mastermind.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Position extends Table {

    String color;

    public Position() {
        super();
        setSize(1f, 1f);
        setTransform(true);
    }

    public String getColorName() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
