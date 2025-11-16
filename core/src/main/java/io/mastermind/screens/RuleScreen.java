package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;

public class RuleScreen implements Screen {
    private Texture background;
    final Main main;

    public RuleScreen(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-vazio.png");
        main.stage.clear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);

        main.viewport.apply();
        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        main.batch.begin();
        if (background != null) main.batch.draw(background, 0, 0, worldWidth, worldHeight);

        main.font.getData().setScale(.05f);
        main.font.draw(main.batch, "Regras", 3f, 11f);

        main.font.getData().setScale(.02f);
        main.font.draw(main.batch, "Objetivo:", 1f, 8.5f);
        main.font.draw(main.batch, "Descobrir a sequência em até 8 tentativas.", 1f, 8f);
        main.font.draw(main.batch, "Existem 6 cores possíveis.", 1f, 7.5f);
        main.font.draw(main.batch, "Cada tentativa usa 4 cores.", 1f, 7f);
        main.font.draw(main.batch, "Feedback:", 1f, 6.5f);
        main.font.draw(main.batch, "- Roxo: cor correta na posição correta.", 1f, 6f);
        main.font.draw(main.batch, "- Branco: cor correta na posição incorreta.", 1f, 5.5f);
        main.font.draw(main.batch, "Boa sorte!", 1f, 5f);

        TextButton button = new TextButton("Voltar", main.skin);
        button.setSize(100f, 40f);
        button.setPosition(150f, 100f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
            }
        });
        main.stage.addActor(button);

        main.batch.end();
        main.stage.act(delta);
        main.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
