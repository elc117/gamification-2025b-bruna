package io.mastermind;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private Texture background;
    private Texture amarelo;
    private Texture azul;
    private Texture laranja;
    private Texture rosa;
    private Texture verde;
    private Texture vermelho;
    private Cursor customCursor;

    SpriteBatch batch;
    FitViewport viewport;

    Vector2 touchPos = new Vector2();

    @Override
    public void create() {
        background = new Texture("background/fundo-vazio.png");
        amarelo = new Texture("circle/amarelo.png");
        azul = new Texture("circle/azul.png");
        laranja = new Texture("circle/laranja.png");
        rosa = new Texture("circle/rosa.png");
        verde = new Texture("circle/verde.png");
        vermelho = new Texture("circle/vermelho.png");

        batch = new SpriteBatch();

        Pixmap orig = new Pixmap(Gdx.files.internal("cursor.png"));
        int desiredSize = 32;
        Pixmap scaled = new Pixmap(desiredSize, desiredSize, orig.getFormat());
        scaled.drawPixmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), 0, 0, desiredSize, desiredSize);
        orig.dispose();
        customCursor = Gdx.graphics.newCursor(scaled, 0, 0);
        scaled.dispose();
        Gdx.graphics.setCursor(customCursor);

        viewport = new FitViewport(8, 14);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
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

    private void draw() {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        batch.draw(background, 0, 0, worldWidth, worldHeight);

        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        if (customCursor != null) customCursor.dispose();
        amarelo.dispose();
        azul.dispose();
        laranja.dispose();
        rosa.dispose();
        verde.dispose();
        vermelho.dispose();
    }
}
