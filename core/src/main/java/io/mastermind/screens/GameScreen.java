package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.Main;
import io.mastermind.objects.AttemptLine;
import io.mastermind.objects.Ball;
import io.mastermind.objects.Position;

import java.util.ArrayList;

public class GameScreen implements Screen {
    final Main main;

    Texture background;

    ArrayList<Ball> balls = new ArrayList<Ball>();
    AttemptLine attemptLine = new AttemptLine();

    private DragAndDrop dragAndDrop;
    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Sprite> dropSprites;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    int dropsGathered;

    public GameScreen(final Main game) {
        this.main = game;

        balls.add(new Ball("amarelo", 40f, 40f));
        balls.add(new Ball("azul", 90f, 40f));
        balls.add(new Ball("laranja", 140f, 40f));
        balls.add(new Ball("rosa", 190f, 40f));
        balls.add(new Ball("verde", 240f, 40f));
        balls.add(new Ball("vermelho", 290f, 40f));

        dragAndDrop = new DragAndDrop();

        for (final Ball ball : balls) {
            dragAndDrop.addSource(new DragAndDrop.Source(ball.getActor()) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    Image dragImage = new Image(((Image) ball.getActor()).getDrawable());
                    float w = ball.getActor().getWidth();
                    float h = ball.getActor().getHeight();
                    dragImage.setSize(w, h);
                    payload.setDragActor(dragImage);
                    payload.setObject(ball);
                    dragAndDrop.setDragActorPosition(w / 150f, h / 150f);
                    return payload;
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                    try {
                        if (target == null) {
                            Object obj = payload.getObject();
                            if (obj instanceof Ball) {
                                Ball b = (Ball) obj;
                                Position holder = b.getHolder();
                                if (holder != null) {
                                    b.getActor().remove();
                                    holder.addActor(b.getActor());
                                    b.getActor().setPosition(holder.getX(), holder.getY());
                                } else {
                                    b.getActor().remove();
                                    main.stage.addActor(b.getActor());
                                }
                            } else {
                                Image img = (Image) payload.getDragActor();
                                if (img != null) {
                                    img.remove();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error in dragStop: " + e);
                        e.printStackTrace();
                    }
                }
            });
        }

        for (final Position position : attemptLine.getPositions()) {
            dragAndDrop.addTarget(new DragAndDrop.Target(position) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v1, int i) {
                    return true;
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v1, int i) {
                    try {
                        Object obj = payload.getObject();
                        if (obj instanceof Ball) {
                            Ball b = (Ball) obj;
                            b.getActor().remove();
                            position.addActor(b.getActor());
                            b.getActor().setPosition(position.getX(), position.getY());
                            b.setHolder(position);
                        } else {
                            Image img = (Image) payload.getDragActor();
                            if (img != null) {
                                img.remove();
                                position.addActor(img);
                                img.setPosition(45f, 45f);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error in drop: " + e);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void show() {
        background = new Texture("background/fundo-sem-texto-e-opcoes.png");

        main.stage.clear();
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
        for (Ball ball : balls) main.stage.addActor(ball.actor);
        for (Position pos : attemptLine.getPositions()) main.stage.addActor(pos);

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
        for (Ball ball : balls) {
            ball.dispose();
        }
        main.batch.dispose();
    }
}
