package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * MainMenu class contains all necessaries for building our game's
 * main menu screen. Mainmenu implement Screen
 *
 * @author      Pauliina lahti, Joona Neuvonen
 * @version     2019.4
 */

public class MainMenu implements Screen {

    // Create MainGame object
    MainGame game;

    // Create SpriteBatch
    SpriteBatch batch;

    // Main menu's background
    Texture background;

    // Main menu's used skin
    private Skin mySkin;

    // Main menu's used stage
    private Stage stage;

    // These Strings are mainmenus button's texts
    String playText, settingsText, quitText;

    /**
     * MainMenu's constructor
     *
     * @param g is MainGame object
     */
    public MainMenu(MainGame g) {

        // Initializing variables
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.2.png"));
        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);
        Preferences pref = game.getPrefs();

        // If preference is english, then button text are in english
        if(pref.getBoolean("english")){
            settingsText = "SETTINGS";
            playText = "PLAY";
            quitText = "QUIT";

            // If preference is something else than english, then button text in finnish
        } else {
            settingsText = "ASETUKSET";
            playText = "PELAA";
            quitText = "LOPETA";
        }

        // Create start button
        Button startBtn = new TextButton(playText, mySkin, "small");
        startBtn.pad(30);
        ((TextButton) startBtn).getLabel().setFontScale(game.buttonSize);
        startBtn.addListener(new ChangeListener() {

            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // If player press start, it goes to slot machine screen
                game.goSlotMachine();
            }
        });

        // Create settings button
        Button settingsBtn = new TextButton(settingsText, mySkin, "small");
        settingsBtn.pad(30);
        ((TextButton) settingsBtn).getLabel().setFontScale(game.buttonSize);
        settingsBtn.addListener(new ChangeListener() {
            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // If player press settinfs, it goes to settings screen
                game.goSettingsScreen();
            }
        });

        /** Create quit button */
        Button quitBtn = new TextButton(quitText, mySkin, "small");
        quitBtn.pad(30);
        ((TextButton) quitBtn).getLabel().setFontScale(game.buttonSize);
        quitBtn.addListener(new ChangeListener() {
            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // If player press quit, it close the game
                Gdx.app.exit();
                System.exit(-1);
            }
        });

        // Creating table, which make possible to add buttons to screen
        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().uniform().pad(15);
        table.add(quitBtn);
        table.add(startBtn);
        table.add(settingsBtn);
        table.bottom().pad(30);
        table.setFillParent(true);
        stage.addActor(table);

    }
    /**
     * show method handle stage handling
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * render method renders the screen
     *
     * @param delta tells delta time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();
        batch.end();
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Dispose method dispose background image, stages, game object and batch
     * when player close the game
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        game.dispose();
        batch.dispose();
    }
}

