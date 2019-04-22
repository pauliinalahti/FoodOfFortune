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


/**
 * StartScreen class handles starting screen before actual game start
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class StartScreen implements Screen {

    // Create Maingame object game
    MainGame game;

    // Create SpriteBatch batch
    SpriteBatch batch;

    // Create background for this screen
    Texture background;

    // Create Skin to handle Screen's skin
    private Skin mySkin;

    // Create new Stage
    private Stage stage;

    // TextureRegion telss thats time Frame
    private TextureRegion currentFrame;

    // StateTime at the beginning is 0.0f
    private float stateTime = 0.0f;

    /** Floats for scaling the screen */
    private float worldWidth, worldHeight;

    // Ints for cols and rows
    private int FRAME_COLS1 = 4;
    private int FRAME_ROWS1 = 6;

    // Create OrthographicCamera camera for rendering the screen
    private OrthographicCamera camera;

    // Starting screen's music
    public Music start;

    // Boolean for firstTime
    boolean firstTime;

    // Background's image
    Texture ggSheet;

    // New animation for starting screen
    Animation<TextureRegion> firstReelSheet;

    // New TextureRegion to handle different frames in animation
    TextureRegion [] frames;

    /**
     * StartScreen's constructor
     *
     * @param g is Maingame object
     */
    public StartScreen(MainGame g) {
        // Initializing the variables
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

        // Create new table for showing image in the screen
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

    /**
     * spriteSheet method split texture to 2D Array
     *
     * @param reelRoll is texture in screen
     * @param cols tells how many cols are in the texture
     * @param rows telss how many rows are in the tecxture
     */
    private void spriteSheet(Texture reelRoll, int cols, int rows){
        TextureRegion[][] tmp = TextureRegion.split(reelRoll,
                reelRoll.getWidth() / cols,
                reelRoll.getHeight() / rows);
        frames = transformTo1D(tmp, cols, rows);
    }

    /**
     * TextureTegion[] transformto1D method chande 2D Array to 1D
     *
     * @param tmp is 2d Array which includes texture
     * @param cols tells how many cols are in the 2D array
     * @param rows tells how many rows are in the 2D Array
     * @return frames is 1D frames
     */
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

    /**
     * render method renders the screen
     *
     * @param delta tells delta time
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(game.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // StateTime increasing continuously
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = firstReelSheet.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, 3, 1, 4, 3);
        batch.end();

        // If firstime is true, it shows the screen
        if (firstTime) {
            start.play();
            firstTime = false;
        }

        // If StateTime is over 7.0, screen change to mainmenu screen
        if (stateTime >= 7.0) {
            game.goMainMenu();
        }
    }

    /**
     * sleep method sleep screen short time
     */
    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
        }
    }

    @Override
    public void show() {
    }

    /**
     * resize method update screen size
     *
     * @param width tells new screen's width
     * @param height tells new screen's height
     */
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

    /**
     * dispose method dispose mySkin, batch, backgrounds, music and stage.
     */
    @Override
    public void dispose() {
        mySkin.dispose();
        start.dispose();
        ggSheet.dispose();
        stage.dispose();
        batch.dispose();
        background.dispose();
    }
}

