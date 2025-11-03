package com.example.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.example.game.MyGdxGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("gamificacao");
        config.setWindowedMode(800, 480);
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}
gdxVersion=1.11.0
javaVersion=17
org.gradle.jvmargs=-Xmx2g

