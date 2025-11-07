package io.mastermind;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private Texture background;
    private Texture buttonTexture;
    private TextButton jogarButton;
    private TextButton regrasButton;
    private TextButton rankingButton;
    Sprite buttonSprite;

    final Main main;

    public MainMenuScreen(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-vazio.png");
        buttonTexture = new Texture("components/button.png");

        jogarButton = new TextButton("Jogar", main.skin);
        jogarButton.setSize(140f, 80f);
        jogarButton.setPosition(130f, 350f);
        jogarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
//                main.setScreen(new GameScreen(main));
            }
        });
        main.stage.addActor(jogarButton);

        regrasButton = new TextButton("Regras", main.skin);
        regrasButton.setSize(140f, 80f);
        regrasButton.setPosition(130f, 250f);
        regrasButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
//                main.setScreen(new GameScreen(main));
            }
        });
        main.stage.addActor(regrasButton);

        rankingButton = new TextButton("Ranking", main.skin);
        rankingButton.setSize(140f, 80f);
        rankingButton.setPosition(130f, 150f);
        rankingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
//                main.setScreen(new GameScreen(main));
            }
        });
        main.stage.addActor(rankingButton);
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw(delta);
    }

    private void input() {
//        if (Gdx.input.isTouched()) {
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//            viewport.unproject(touchPos);
////            isso aqui é bom p arrastar as bolinhas p posicao do toque
//            cursorSprite.setPosition(touchPos.x - cursorSprite.getWidth() / 2, touchPos.y - cursorSprite.getHeight() / 2);
//        }
    }

    private void logic() {
        // Game logic code goes here
    }

    private void draw(float delta) {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);

        main.viewport.apply();
        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        main.batch.begin();
        if (background != null) main.batch.draw(background, 0, 0, worldWidth, worldHeight);

        main.font.getData().setScale(.05f);
        main.font.draw(main.batch, "Mastermind", 2f, 11f);

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
        if (buttonTexture != null) buttonTexture.dispose();
    }

}
