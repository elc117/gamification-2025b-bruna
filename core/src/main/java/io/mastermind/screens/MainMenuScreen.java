package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;

public class MainMenuScreen implements Screen {
    private Texture background;

    final Main main;

    private TextButton jogarButton;
    private TextButton rankingButton;
    private TextButton rulesButton;

    public MainMenuScreen(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-vazio.png");

        jogarButton = new TextButton("Jogar", main.skin);
        jogarButton.setSize(140f, 80f);
        jogarButton.setPosition(130f, 350f);
        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
            }
        });

        rankingButton = new TextButton("Ranking", main.skin);
        rankingButton.setSize(140f, 80f);
        rankingButton.setPosition(130f, 250f);
        rankingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new RankingScreen(main));
            }
        });

        rulesButton = new TextButton("Regras", main.skin);
        rulesButton.setSize(140f, 80f);
        rulesButton.setPosition(130f, 150f);
        rulesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new RuleScreen(main));
            }
        });

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
        main.font.draw(main.batch, "Mastermind", 2f, 11f);

        main.stage.addActor(jogarButton);
        main.stage.addActor(rankingButton);
        main.stage.addActor(rulesButton);

        main.batch.end();
        main.stage.act(delta);
        main.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        main.viewport.update(width, height, true);
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
        if (background != null) background.dispose();
    }

}
