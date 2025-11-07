package io.mastermind.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;

public class GameScreen implements Screen {
    final Main main;

    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    int dropsGathered;

    public GameScreen(final Main game) {
        this.main = game;

        // load the images for the background, bucket and droplet
        // Use existing assets under assets/ directory
        backgroundTexture = new Texture("background/fundo-completo.png");
        bucketTexture = new Texture("components/button.png");
        dropTexture = new Texture("circle/azul.png");

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);

        touchPos = new Vector2();

        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        dropSprites = new Array<Sprite>();
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input() {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            main.viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();
        float delta = Gdx.graphics.getDeltaTime();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);
            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) dropSprites.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) {
                dropsGathered++;
                dropSprites.removeIndex(i);
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createDroplet();
        }
    }

    private void draw() {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);
        main.viewport.apply();
        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.batch.begin();

        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        main.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(main.batch);

        main.font.draw(main.batch, "Drops collected: " + dropsGathered, 0, worldHeight);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(main.batch);
        }

        main.batch.end();
    }

    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0F, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
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
        backgroundTexture.dispose();
        dropTexture.dispose();
        bucketTexture.dispose();
        main.batch.dispose();
    }
}
