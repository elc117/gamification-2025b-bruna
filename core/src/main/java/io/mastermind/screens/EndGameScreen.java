package io.mastermind.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;
import io.mastermind.objects.BallNonDragable;
import io.mastermind.objects.Try;
import io.mastermind.utils.RankingLogic;

import java.util.ArrayList;

public class EndGameScreen implements Screen {
    final Main main;
    final Boolean won;

    Try lastTry;
    ArrayList<String> sequence;

    RankingLogic ranking = new RankingLogic();

    private Texture background;
    private Texture input;

    String name = "";

    private TextButton otherButton;
    private TextButton mainMenuButton;

    public EndGameScreen(Main main, boolean won, Try lastTry) {
        this.main = main;
        this.won = won;
        this.lastTry = lastTry;
    }

    public EndGameScreen(Main main, boolean won, Try lastTry,  ArrayList<String> sequence) {
        this.main = main;
        this.won = won;
        this.lastTry = lastTry;
        this.sequence = sequence;
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-vazio.png");
        input = new Texture("components/input.png");

        String text = this.won ? "Adicionar" : "Novo jogo";
        otherButton = new TextButton(text, main.skin);
        otherButton.setSize(140f, 80f);
        otherButton.setPosition(130f, 200f);
        otherButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (won) {
                    ranking.add(name, lastTry.attemptNumber);
                    main.setScreen(new RankingScreen(main));
                } else {
                    main.setScreen(new GameScreen(main));
                }
            }
        });

        mainMenuButton = new TextButton("Voltar ao menu", main.skin);
        mainMenuButton.setSize(140f, 80f);
        mainMenuButton.setPosition(130f, 100f);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
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

        if (this.won) {
            handleInput();
        }

        main.batch.begin();
        if (background != null) main.batch.draw(background, 0, 0, worldWidth, worldHeight);

        String text = this.won ? "Você ganhou!!" : "Você perdeu!";

        main.font.getData().setScale(.05f);
        main.font.draw(main.batch, text, 2f, 11f);

        main.font.getData().setScale(.03f);
        if (this.won) {
            main.font.draw(main.batch, "Você acertou em " + lastTry.attemptNumber + " tentativa" + (lastTry.attemptNumber > 1 ? "s." : "."), 1f, 9f);
            main.font.draw(main.batch, "Adicione o seu nome no ranking!!", 1f, 8f);
            if (input != null) main.batch.draw(input, 1.5f, 6f, 5f, 1f);
            main.font.getData().setScale(.025f);
            main.font.draw(main.batch, name, 1.8f, 6.6f);
        } else {
            main.font.draw(main.batch, "A sequência correta era:", 1f, 9f);
            float x = 90f;
            float spacing = 10f;
            for (String color : sequence) {
                BallNonDragable ball = new BallNonDragable(color, 0f, 0f);
                Image img = new Image(new TextureRegionDrawable(new TextureRegion(ball.sprite.getTexture())));
                img.setSize(50f, 50f);
                img.setPosition(x, 350f);
                main.stage.addActor(img);
                img.toFront();
                x += (52f + spacing);
            }
        }

        main.batch.end();
        main.stage.act(delta);
        main.stage.addActor(otherButton);
        main.stage.addActor(mainMenuButton);

        main.stage.draw();
    }

    private void handleInput() {
        for (char key = 'A'; key <= 'Z'; key++) {
            int keyCode = Input.Keys.valueOf(String.valueOf(key));
            if (Gdx.input.isKeyJustPressed(keyCode)) {
                this.name += Character.toLowerCase(key);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.name += " ";
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && this.name.length() > 0) {
            this.name = this.name.substring(0, this.name.length() - 1);
        }
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
        if (background != null) background.dispose();
        if (input != null) input.dispose();
    }
}
