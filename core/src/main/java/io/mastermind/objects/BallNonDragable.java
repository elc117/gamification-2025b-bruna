package io.mastermind.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BallNonDragable extends Ball {
    Sprite sprite;

    public BallNonDragable(String color, float x, float y) {
        super(color);
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(0.9f, 0.9f);
        this.sprite.setPosition(x, y);
    }
}
