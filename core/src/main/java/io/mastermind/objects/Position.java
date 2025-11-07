package io.mastermind.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class Position extends Window {

    private static final WindowStyle windowStyle;

    static {
        windowStyle = new WindowStyle();
    }

    public Position() {
        super("", windowStyle);
        setSize(800, 600);
        setClip(false);
        setTransform(true);
    }
}
