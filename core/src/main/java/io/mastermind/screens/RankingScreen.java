package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;
import io.mastermind.objects.Ranking;
import io.mastermind.utils.RankingLogic;

import java.util.List;

public class RankingScreen implements Screen {
    private Texture background;
    final Main main;

    RankingLogic rankingLogic;

    public RankingScreen(final Main game) {
        this.main = game;
        rankingLogic = new RankingLogic();
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
        main.font.draw(main.batch, "Ranking", 2.5f, 11f);

        main.font.getData().setScale(.02f);
        List<Ranking> ranking = rankingLogic.getRanking();
        Float y = 8.5f;
        for (Ranking position : ranking) {
            String str = position.getName() + " - " + position.getQuantity() + " tentativas em " + position.getDate();
            main.font.draw(main.batch, str, 1f, y);
            y += 0.5f;
        }

        TextButton button = new TextButton("Voltar", main.skin);
        button.setSize(100f, 40f);
        button.setPosition(150f, 100f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
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
