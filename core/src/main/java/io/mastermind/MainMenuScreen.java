package io.mastermind;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.screens.GameScreen;
import io.mastermind.screens.RankingScreen;

public class MainMenuScreen implements Screen {
    private Texture background;

    final Main main;

    public MainMenuScreen(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-vazio.png");
        main.stage.clear();


//        TextButton regrasButton = new TextButton("Regras", main.skin);
//        regrasButton.setSize(140f, 80f);
//        regrasButton.setPosition(130f, 250f);
//        regrasButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Button clicked");
//                main.setScreen(new RuleScreen(main));
//            }
//        });
//        main.stage.addActor(regrasButton);


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

        TextButton jogarButton = new TextButton("Jogar", main.skin);
        jogarButton.setSize(140f, 80f);
        jogarButton.setPosition(130f, 350f);
        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
                main.setScreen(new GameScreen(main));
//                dispose();
            }
        });
        main.stage.addActor(jogarButton);

        TextButton rankingButton = new TextButton("Ranking", main.skin);
        rankingButton.setSize(140f, 80f);
        rankingButton.setPosition(130f, 250f);
        rankingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
                main.setScreen(new RankingScreen(main));
//                dispose();
            }
        });
        main.stage.addActor(rankingButton);

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
