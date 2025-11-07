package io.mastermind.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BallDragable extends Ball {
    public Image actor;
    private Position holder;

    public BallDragable(String color, float x, float y) {
        super(color);
        TextureRegion tr = new TextureRegion(this.texture);
        this.actor = new Image(new TextureRegionDrawable(tr));
        this.actor.setSize(45f, 45f);
        this.actor.setPosition(x, y);
        this.actor.setUserObject(this);
    }

    public void setHolder(Position p) {
        this.holder = p;
    }

    public Position getHolder() {
        return this.holder;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setPosition(float x, float y) {
        this.actor.setPosition(x, y);
    }
}
