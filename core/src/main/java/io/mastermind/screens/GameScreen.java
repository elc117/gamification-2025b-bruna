package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;
import io.mastermind.MainMenuScreen;

public class GameScreen implements Screen {
    final Main main;

    Texture background;
    Texture amareloTexture;
    Texture azulTexture;
    Texture laranjaTexture;
    Texture rosaTexture;
    Texture verdeTexture;
    Texture vermelhoTexture;

    Sprite amareloSprite;
    Sprite azulSprite;
    Sprite laranjaSprite;
    Sprite rosaSprite;
    Sprite verdeSprite;
    Sprite vermelhoSprite;

    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    int dropsGathered;

    public GameScreen(final Main game) {
        this.main = game;

//        bucketTexture = new Texture("components/button.png");

//        bucketSprite = new Sprite(bucketTexture);
//        bucketSprite.setSize(1, 1);

//        touchPos = new Vector2();

//        bucketRectangle = new Rectangle();
//        dropRectangle = new Rectangle();

//        dropSprites = new Array<Sprite>();
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-sem-texto-e-opcoes.png");
        amareloTexture = new Texture("circle/amarelo.png");
        azulTexture = new Texture("circle/azul.png");
        laranjaTexture = new Texture("circle/laranja.png");
        rosaTexture = new Texture("circle/rosa.png");
        verdeTexture = new Texture("circle/verde.png");
        vermelhoTexture = new Texture("circle/vermelho.png");

        amareloSprite = new Sprite(amareloTexture);
        amareloSprite.setSize(0.9f, 0.9f);
        amareloSprite.setPosition(1.18f, 0.7f);

        rosaSprite = new Sprite(rosaTexture);
        rosaSprite.setSize(0.9f, 0.9f);
        rosaSprite.setPosition(2.18f, 0.7f);

        azulSprite = new Sprite(azulTexture);
        azulSprite.setSize(0.9f, 0.9f);
        azulSprite.setPosition(3.18f, 0.7f);

        laranjaSprite = new Sprite(laranjaTexture);
        laranjaSprite.setSize(0.9f, 0.9f);
        laranjaSprite.setPosition(4.18f, 0.7f);

        verdeSprite = new Sprite(verdeTexture);
        verdeSprite.setSize(0.9f, 0.9f);
        verdeSprite.setPosition(5.18f, 0.7f);

        vermelhoSprite = new Sprite(vermelhoTexture);
        vermelhoSprite.setSize(0.9f, 0.9f);
        vermelhoSprite.setPosition(6.18f, 0.7f);

        main.stage.clear();
        // start the playback of the background music
        // when the screen is shown
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
//            main.viewport.unproject(touchPos);
//            bucketSprite.setCenterX(touchPos.x);
//        }
    }

    private void logic() {
//        float worldWidth = main.viewport.getWorldWidth();
//        float worldHeight = main.viewport.getWorldHeight();
//        float bucketWidth = bucketSprite.getWidth();
//        float bucketHeight = bucketSprite.getHeight();
//        float delta = Gdx.graphics.getDeltaTime();
//
//        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
//        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);
//
//        for (int i = dropSprites.size - 1; i >= 0; i--) {
//            Sprite dropSprite = dropSprites.get(i);
//            float dropWidth = dropSprite.getWidth();
//            float dropHeight = dropSprite.getHeight();
//
//            dropSprite.translateY(-2f * delta);
//            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);
//
//            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
//            else if (bucketRectangle.overlaps(dropRectangle)) {
//                dropsGathered++;
//                dropSprites.removeIndex(i);
//            }
//        }
//
//        dropTimer += delta;
//        if (dropTimer > 1f) {
//            dropTimer = 0;
//            createDroplet();
//        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);
        main.viewport.apply();
        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.batch.begin();

        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        main.batch.draw(background, 0, 0, worldWidth, worldHeight);

        TextButton button = new TextButton("Confirmar", main.skin);
        button.setSize(90f, 50f);
        button.setPosition(270f, 100f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button clicked");
//                main.setScreen(new MainMenuScreen(main));
            }
        });
        main.stage.addActor(button);
        amareloSprite.draw(main.batch);
        azulSprite.draw(main.batch);
        laranjaSprite.draw(main.batch);
        rosaSprite.draw(main.batch);
        verdeSprite.draw(main.batch);
        vermelhoSprite.draw(main.batch);
//        bucketSprite.draw(main.batch);
//
//        main.font.draw(main.batch, "Drops collected: " + dropsGathered, 0, worldHeight);
//
//        for (Sprite dropSprite : dropSprites) {
//            dropSprite.draw(main.batch);
//        }

        main.batch.end();
        main.stage.act(delta);
        main.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        main.viewport.update(width, height, true);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        background.dispose();
        amareloTexture.dispose();
        azulTexture.dispose();
        laranjaTexture.dispose();
        rosaTexture.dispose();
        verdeTexture.dispose();
        vermelhoTexture.dispose();
//        dropTexture.dispose();
//        bucketTexture.dispose();
        main.batch.dispose();
    }
}
