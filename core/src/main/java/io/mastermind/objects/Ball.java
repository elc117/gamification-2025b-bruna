package io.mastermind.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ball {
    String color;
    Texture texture;
    public Sprite sprite;

    public Ball(String color, float x, float y) {
        this.color = color;
        this.texture = new Texture("circle/" + color + ".png");
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(0.9f, 0.9f);
        this.sprite.setPosition(x, y);
    }
}
