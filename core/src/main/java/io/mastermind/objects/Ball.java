package io.mastermind.objects;

import com.badlogic.gdx.graphics.Texture;

public class Ball {
    String color;
    Texture texture;

    public Ball(String color) {
        this.color = color;
        this.texture = new Texture("circle/" + color + ".png");
    }

    public void dispose() {
        texture.dispose();
    }

    public String getColor() {
        return color;
    }
}
