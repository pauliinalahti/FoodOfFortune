package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * SettingScreen class contains all necessaries for building our game's
 * settings screen. SettingScreen implement Screen
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */

public class SettingsScreen implements Screen {

    /** Create Maingame object game */
    MainGame game;

    /** Create Spritebatch */
    SpriteBatch batch;

    /** Setting's background*/
    Texture background;

    /** Setting's used skin */
    private Skin mySkin;

    /** Settings's used stage */
    private Stage stage;

    /** Layoutsettings for settings */
    GlyphLayout layoutSettings;

    /** Strings below are buttons texts*/
    String settingsText;
    String backText;
    String customText;
    String languageText;
    String onOffText;
    String musicText;
    String changeText;
    String creditsText;
    String chosenLanguage;
    String moreTxt;

    /** Pref tells what language is on */
    Preferences pref;

    /** Create backbutton */
    Button backBtn;

    /** Create table */
    Table screenTable;

    /**
     * SettingScreen's constructor
     *
     * @param g is MainGame object
     */
    public SettingsScreen(MainGame g) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        game = g;
        batch = game.getBatch();
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        pref = game.getPrefs();

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        /** If preference's key is english, then button text are in english */
        if(pref.getBoolean("english")){
            backText = "BACK";
            customText = "Custom food ingredients";
            settingsText = "SETTINGS";
            languageText = "Language: ";
            creditsText = "Credits";
            onOffText = "ON";
            changeText = "Language: EN";
            chosenLanguage = "English";
            moreTxt = "FAQ";

            /** If music on is selected, then text: "on", else "off" */
            if(pref.getBoolean("music")) {
                musicText = "Music: ON";
            } else {
                musicText = "Music: OFF";
            }
            /** If langugage is finnish, then button texts are in finnish */
        } else {
            backText = "TAKAISIN";
            customText = "Ruoka-aine asetukset";
            settingsText = "ASETUKSET";
            languageText = "Kieli: ";
            creditsText = "Tekijät";
            onOffText = "OFF";
            changeText = "Kieli: FI";
            chosenLanguage = "Suomi";
            moreTxt = "Tietoa meistä";
            if(pref.getBoolean("music")) {
                musicText = "Äänet: ON";
            } else {
                musicText = "Äänet: OFF";
            }
        }
        /** Create table for buttons and adds background*/
        screenTable = new Table();
        screenTable.setFillParent(true);
        screenTable.setBackground(new TextureRegionDrawable(background));

        /** Create new Glyphlayout to handle setting's text*/
        layoutSettings = new GlyphLayout();
        layoutSettings.setText(game.font2, settingsText);

        /** Create back button */
        Button backBtn = new TextButton(backText, mySkin, "small");
        backBtn.setTransform(true);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {

            /**
             * change method change screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press back button, it chance screen to main menu */
                game.goMainMenu();
            }
        });

        /** Create languagebutton button */
        Button languageBtn = new TextButton(changeText, mySkin, "small");
        languageBtn.setTransform(true);
        ((TextButton) languageBtn).getLabel().setFontScale(game.buttonSizeSmall);
        languageBtn.addListener(new ChangeListener() {

            /**
             * change method change screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press language button, it chance language */
                if(game.getPrefs().getBoolean("english")) {
                    game.getPrefs().putBoolean("english", false);
                    game.getPrefs().flush();
                } else {
                    game.getPrefs().putBoolean("english", true);
                    game.getPrefs().flush();
                }
                /** It change to settings screen, depends on what language is on */
                game.goSettingsScreen();
            }
        });

        /** Create custombutton */
        Button customBtn = new TextButton(customText, mySkin, "small");
        customBtn.setTransform(true);
        ((TextButton) customBtn).getLabel().setFontScale(game.buttonSizeSmall);
        customBtn.addListener(new ChangeListener() {

            /**
             * change method change screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press custom reels button, it change to customreels screen */
                game.goCustomReels();
            }
        });

        /** Create credits button */
        Button creditsBtn = new TextButton(creditsText, mySkin, "small");
        //creditsBtn.pad(20);
        creditsBtn.setTransform(true);
        ((TextButton) creditsBtn).getLabel().setFontScale(game.buttonSizeSmall);
        creditsBtn.addListener(new ChangeListener() {

            /**
             * changed method change the screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press credits button, it change to credits screen */
                game.goCredits();
            }
        });

        /** Create music button */
        Button musicBtn = new TextButton(musicText, mySkin, "small");
        //musicBtn.pad(20);
        musicBtn.setTransform(true);
        ((TextButton) musicBtn).getLabel().setFontScale(game.buttonSizeSmall);
        musicBtn.addListener(new ChangeListener() {

            /**
             * changed method change the screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPrefs().getBoolean("music")) {
                    game.getPrefs().putBoolean("music", false);
                    game.getPrefs().flush();
                    game.backgroundMusic.pause();
                    System.out.println("music off");
                } else {
                    game.getPrefs().putBoolean("music", true);
                    game.getPrefs().flush();
                    game.backgroundMusic.play();
                    System.out.println("music on");
                }
                /** Go to settings screen depends on is music on or off*/
                game.goSettingsScreen();
            }
        });


        Button moreBtn = new TextButton(moreTxt, mySkin, "small");
        moreBtn.setTransform(true);
        ((TextButton) moreBtn).getLabel().setFontScale(game.buttonSizeSmall);
        moreBtn.addListener(new ChangeListener() {
            /**
             * changed method change the screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If preference language is english, then clicking website button
                 * leads to our english website */
                if(game.getPrefs().getBoolean("english")) {
                    Gdx.net.openURI("http://18tiko2E.projects.tamk.fi/indexEN.html");
                    /** If preference language is finnish, then clicking website button
                     * leads to our finnish website */
                } else {
                    Gdx.net.openURI("http://18tiko2E.projects.tamk.fi/index.html");
                }

            }
        });
        int div = 150;
        screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.15f, screenTable)).row();
        screenTable.add(languageBtn).pad(game.screenW/div).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(musicBtn).pad(game.screenW/div).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(customBtn).pad(game.screenW/div).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(creditsBtn).pad(game.screenW/div).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.11f, screenTable)).row();
        screenTable.add(moreBtn).pad(game.screenW/div).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.11f, screenTable)).row();
        screenTable.add().expand();
        stage.addActor(screenTable);
    }

    /**
     * show method handles stage
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
        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch, settingsText, WORLDWIDTH * 100 / 2 - layoutSettings.width / 2, (WORLDHEIGHT - 0.3f) * 100);
        batch.setProjectionMatrix((game.camera.combined));
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
     * Dispose method dispose background image, stage, game object and mySkin
     * when player close the game
     */
    @Override
    public void dispose() {
        background.dispose();
        //stage.dispose();
        //game.dispose();
        //mySkin.dispose();

    }
}

