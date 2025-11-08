package io.mastermind.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.mastermind.GameLogic;
import io.mastermind.Main;
import io.mastermind.objects.*;

import java.util.ArrayList;

public class GameScreen implements Screen {
    final Main main;

    ShapeRenderer shapeRenderer;
    Texture background;
    TextButton button;

    ArrayList<BallDragable> balls = new ArrayList<BallDragable>();
    AttemptLine attemptLine = new AttemptLine();

    GameLogic logic = new GameLogic();

    private DragAndDrop dragAndDrop;

    public GameScreen(final Main game) {
        this.main = game;

        balls.add(new BallDragable("amarelo", 40f, 40f));
        balls.add(new BallDragable("azul", 90f, 40f));
        balls.add(new BallDragable("laranja", 140f, 40f));
        balls.add(new BallDragable("rosa", 190f, 40f));
        balls.add(new BallDragable("verde", 240f, 40f));
        balls.add(new BallDragable("vermelho", 290f, 40f));

        dragAndDrop = new DragAndDrop();
        shapeRenderer = new ShapeRenderer();

        for (final BallDragable ball : balls) {
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
                                BallDragable b = (BallDragable) obj;
                                Position holder = b.getHolder();
                                if (holder != null) {
                                    b.getActor().remove();
                                    holder.setColor(b.getColor());
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
                        if (obj instanceof BallDragable) {
                            BallDragable b = (BallDragable) obj;
                            b.getActor().remove();
                            position.addActor(b.getActor());
                            position.setColor(b.getColor());
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
        button = new TextButton("Confirmar", main.skin);

        button.setSize(90f, 50f);
        button.setPosition(270f, 100f);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(attemptLine.getColors());
                System.out.println(attemptLine.isFull());
                if (!attemptLine.isFull()) {
                    System.err.println("Please fill all positions before confirming.");
                    return;
                }
                Try attempt = logic.processAttempt(attemptLine);
                System.out.println("Processed attempt: " + attempt);
                if (attempt == null) {
                    System.err.println("processAttempt returned null - attempt invalid or internal error.");
                    return;
                }

                if (attempt.isWinningTry) {
                    endGame(true);
                } else if (logic.tries.size() > 8) {
                    endGame(false);
                } else {
                    drawAttempt(attempt);
                    clearAttemptLine();
                }
            }
        });

        main.stage.clear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(39f / 255f, 44f / 255f, 50f / 255f, 1f);
        main.viewport.apply();
        main.batch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.batch.begin();

        float worldWidth = main.viewport.getWorldWidth();
        float worldHeight = main.viewport.getWorldHeight();

        main.batch.draw(background, 0, 0, worldWidth, worldHeight);

        main.stage.addActor(button);
        for (BallDragable ball : balls) main.stage.addActor(ball.getActor());
        for (Position pos : attemptLine.getPositions()) main.stage.addActor(pos);

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
        for (BallDragable ball : balls) {
            ball.dispose();
        }
        main.batch.dispose();
    }

    public void drawAttempt(Try attempt) {
        System.out.println("Drawing attempt: " + attempt.getColors());
        float startX = 52f;
        float spacingX = 55.5f;
        float spacingY = 62.5f;
        float startY = 614f;

        int i = 0;
        for (String color : attempt.getColors()) {
            BallNonDragable ball = new BallNonDragable(color, 0f, 0f);
            Image img = new Image(new TextureRegionDrawable(new TextureRegion(ball.sprite.getTexture())));
            img.setSize(45f, 45f);
            img.setPosition(startX + i * spacingX, startY - (attempt.attemptNumber - 1) * spacingY);
            main.stage.addActor(img);
            img.toFront();
            i++;
        }

        Texture blackTexture = new Texture(1, 1, Pixmap.Format.RGBA8888);
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(Color.BLACK);
        p.fill();
        blackTexture.draw(p, 0, 0);
        p.dispose();
        startX = 280f;
        spacingX = 20f;
        for (int j = 0; j < attempt.correctPositionsAndColors; j++) {
            Image img = new Image(new TextureRegionDrawable(new TextureRegion(blackTexture)));
            img.setSize(10f, 10f);
            img.setPosition(startX + j * spacingX, (startY - (attempt.attemptNumber - 1) * spacingY) + 15f);
            System.out.println("Placing black peg "+ j +" at: " + (startX + j * spacingX) + ", " + ((startY - (attempt.attemptNumber - 1) * spacingY) + 15f));
            main.stage.addActor(img);
            img.toFront();
        }

        Texture whiteTexture = new Texture(1, 1, Pixmap.Format.RGBA8888);
        Pixmap m = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        m.setColor(Color.WHITE);
        m.fill();
        whiteTexture.draw(m, 0, 0);
        m.dispose();

        for (int k = attempt.correctPositionsAndColors; k < attempt.correctColorsOnly + attempt.correctPositionsAndColors; k++) {
            Image img = new Image(new TextureRegionDrawable(new TextureRegion(whiteTexture)));
            img.setSize(10f, 10f);
            img.setPosition(startX + k * spacingX, (startY - (attempt.attemptNumber - 1) * spacingY) + 15f);
            System.out.println("Placing white peg "+ k +" at: " + (startX + k * spacingX) + ", " + ((startY - (attempt.attemptNumber - 1) * spacingY) + 15f));
            main.stage.addActor(img);
            img.toFront();
        }
    }

    public void clearAttemptLine() {
        System.out.println("Clearing attempt line");
        for (Position pos : attemptLine.getPositions()) {
            pos.clear();
        }
        //        TODO: validar isso aqui
//        balls.add(new BallDragable("amarelo", 40f, 40f));
//        balls.add(new BallDragable("azul", 90f, 40f));
//        balls.add(new BallDragable("laranja", 140f, 40f));
//        balls.add(new BallDragable("rosa", 190f, 40f));
//        balls.add(new BallDragable("verde", 240f, 40f));
//        balls.add(new BallDragable("vermelho", 290f, 40f));
        for (BallDragable ball : balls) {
            ball.setHolder(null);
        }
    }

    public void endGame(boolean won) {
        System.out.println("Game " + (won ? "won!" : "lost."));
        // TODO: implement game end logic (e.g., show message, return to menu, etc.)
    }
}
