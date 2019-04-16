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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.Arrays;

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

        /** Creating batch from MainGame's class */
        batch = game.getBatch();

        /** Create new stage */
        //stage = new Stage(game.screenPort);

        /** Set texture to background */
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));

        /** get preference's value from game object*/
        pref = game.getPrefs();

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        /** If preference's key is english, then button text are is english */
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
        screenTable = new Table();
        //screenTable.setFillParent(true);
        screenTable.setBackground(new TextureRegionDrawable(background));
        //screenTable.top().left();
        //screenTable.pad(Value.percentHeight(0.1f, screenTable),Value.percentWidth(0.1f,screenTable), Value.percentHeight(0.1f, screenTable),Value.percentWidth(0.1f,screenTable));

        /** Create new Glyphlayout to handle setting's text*/
        layoutSettings = new GlyphLayout();
        layoutSettings.setText(game.font2, settingsText);

        /** Create back button */
        Button backBtn = new TextButton(backText, mySkin, "small");
        //backBtn.pad(20);
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
        //languageBtn.pad(15);
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
                /** It cahnge to setting screen, depends on what language is on */
                game.goSettingsScreen();
            }
        });

        /** Create custombutton */
        Button customBtn = new TextButton(customText, mySkin, "small");
        //customBtn.pad(20);
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
        //musicBtn.pad(20);
        moreBtn.setTransform(true);
        ((TextButton) moreBtn).getLabel().setFontScale(game.buttonSizeSmall);
        moreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI("http://18tiko2E.projects.tamk.fi/fof.html");
            }
        });

        screenTable.defaults().pad(game.screenW/150);
        screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.15f, screenTable)).row();
        screenTable.add(languageBtn).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(musicBtn).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(customBtn).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add(creditsBtn).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.11f, screenTable)).row();
        screenTable.add(moreBtn).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.11f, screenTable)).row();
        screenTable.bottom().pad(Value.percentHeight(0.05f, screenTable));

        /** Create nyt table, which makes possible to show buttons in screen */
        /*
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().uniform().pad(15);
        table.add(backBtn);
        //Label l = new Label("ASETUKSET", mySkin);
        //l.setFontScale(game.buttonSizeBig);
        //table.add(l).expandX();
        table.left();
        table.top();
        table.setDebug(true);*/
        /*
        Table table2 = new Table();
        table2.defaults().uniform().pad(15);
        table2.pad(-5);
        table2.add(languageBtn).row();
        table2.add(musicBtn).row();
        table2.add(customBtn).row();
        table2.add(creditsBtn);
        table2.setPosition(1f, (WORLDHEIGHT-3.1f)*100);
        table2.center();
        table2.bottom().pad(20);*/

        /*Table tableCheckBoxes = new Table();
        tableCheckBoxes.defaults().uniform().pad(30);
        int i = 0;
        for (final String opt : options) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            //cb.pad(-5);
            //System.out.println(opt + ": " + pref.getBoolean(opt));
            cb.getLabel().setFontScale(game.buttonSizeSmall);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.graphics.setContinuousRendering(true);
                    if(pref.getBoolean(opt)){
                        pref.putBoolean(opt, false);
                    } else {
                        pref.putBoolean(opt, true);
                    }
                    pref.flush();
                    game.goSettingsScreen();
                    System.out.println("--------");
                }
            });
            tableCheckBoxes.add(cb).width(200);
            i++;
            if (i == 3) {
                tableCheckBoxes.row();
                i=0;
            }

        }*/


        screenTable.setFillParent(true);
        stage.addActor(screenTable);
        //tableCheckBoxes.setFillParent(true);
        //stage.addActor(table);
        //stage.addActor(table2);
    }

    /**
     * show method handles stage
     *
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
     * Dispose method dispose background image and stages
     * when player close the game
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}
