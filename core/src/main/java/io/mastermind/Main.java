package io.mastermind;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private Cursor customCursor;

    SpriteBatch batch;
    FitViewport viewport;
    BitmapFont font;
    Stage stage;

    Vector2 touchPos = new Vector2();

    @Override
    public void create() {
        batch = new SpriteBatch();

        viewport = new FitViewport(8, 14);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Pixmap orig = new Pixmap(Gdx.files.internal("cursor.png"));
        int desiredSize = 32;
        Pixmap scaled = new Pixmap(desiredSize, desiredSize, orig.getFormat());
        scaled.drawPixmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), 0, 0, desiredSize, desiredSize);
        orig.dispose();
        customCursor = Gdx.graphics.newCursor(scaled, 0, 0);
        scaled.dispose();
        Gdx.graphics.setCursor(customCursor);

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        // Agora que o viewport existe, podemos usar suas dimensões
        font.getData().setScale(viewport.getWorldHeight() / (float) Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        input();
        logic();
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


    @Override
    public void dispose() {
        font.dispose();
        if (customCursor != null) customCursor.dispose();
        if (batch != null) batch.dispose();
        if (stage != null) stage.dispose();
    }
}
