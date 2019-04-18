package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class StartScreen implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    private TextureRegion currentFrame;
    private float stateTime = 0.0f;
    private int FRAME_COLS1 = 4;
    private int FRAME_ROWS1 = 6;
    private float worldWidth;
    private float worldHeight;
    private OrthographicCamera camera;
    public Music start;
    boolean firstTime;

    Texture ggSheet;
    Animation<TextureRegion> firstReelSheet;
    TextureRegion [] frames;


    public StartScreen(MainGame g) {

        start = Gdx.audio.newMusic(Gdx.files.internal("music/start.mp3"));
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        worldWidth = stage.getWidth();
        worldHeight = stage.getHeight();
        background = new Texture(Gdx.files.internal("galaxy3.png"));

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);
        Preferences pref = game.getPrefs();

        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.setFillParent(true);
        stage.addActor(table);

        ggSheet = new Texture(Gdx.files.internal("ggames4.png"));
        spriteSheet(ggSheet, FRAME_COLS1, FRAME_ROWS1);
        firstReelSheet = new Animation<TextureRegion>(1/10f, frames);
        firstTime = true;
        sleep(1000);
    }

    private void spriteSheet(Texture reelRoll, int cols, int rows){
        TextureRegion[][] tmp = TextureRegion.split(reelRoll,
                reelRoll.getWidth() / cols,
                reelRoll.getHeight() / rows);
        frames = transformTo1D(tmp, cols, rows);
    }

    private TextureRegion[] transformTo1D(TextureRegion [][] tmp, int cols, int rows) {
        TextureRegion [] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(i==5 && j == 3) {
                    frames[index++] = tmp[i][j-1];
                } else {
                    frames[index++] = tmp[i][j];
                }
            }
        }
        return frames;
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(game.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = firstReelSheet.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, 3, 1, 4, 3);
        batch.end();

        if (firstTime) {
            start.play();
            firstTime = false;
        }
        if (stateTime >= 7.0) {
            game.goMainMenu();
        }
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width, height, true);
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
    }
}
