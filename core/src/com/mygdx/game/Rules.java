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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * Rules class introduce game's rules in own screen
 * It implements screen
 *
 * @author      Pauliina lahti, Joona Neuvonen
 * @version     2019.4
 */

public class Rules implements Screen {

    // Create MainGame object to handle maingame variables
    MainGame game;

    // Create SpriteBatch
    SpriteBatch batch;

    // Background's and logo's images
    Texture background, logo;

    // Create Skin to handle screen skin
    private Skin mySkin;

    // Create stage to handle screen objects
    private Stage stage;

    // New ScreenTable
    Table screenTable;

    // Create new GlyphLayout handle texts scaling
    GlyphLayout layoutCredits;

    // Create button's text
    String creditsText, backText, rulesTxt;

    // Preference defines current language
    Preferences pref;

    // Create backbutton, which leads to settings screen
    Button backBtn;

    /**
     * Credit's constructor
     *
     * @param g is MainGame object
     */
    public Rules(MainGame g){

        //Initializing variables
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        logo = new Texture(Gdx.files.internal("GGLogo11.png"));
        pref = game.getPrefs();
        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        // If preference language is english, rules
        if(pref.getBoolean("english")){
            backText = "BACK";
            creditsText = "HOW TO PLAY";
            rulesTxt = "Choose which ingredients are available\nin Custom food ingredients.\n\n In Slotmachine the game draws ingredients and presents you \nthe recipes regarding the first two wheels.\n \n Roll, cook and enjoy!";
            // If preference language isn't english, then button's text are in finnish
        } else {
            backText = "TAKAISIN";
            creditsText = "KUINKA PELATA";
            rulesTxt = "Valitse ruoka-aines asetuksista pois ne ainekset,\njoita resepteissä ei sallita.\n\n Pelinäkymässä arvotaan ruoka-ainekset,\nja näytetään reseptejä käyttäjän suositusten \n sekä kahden ensimmäisen rullan perusteella.\n\n Pelaa, kokkaa ja nauti!";
        }

        // Create new Table and adds the background
        screenTable = new Table();
        screenTable.setFillParent(true);
        screenTable.setBackground(new TextureRegionDrawable(background));

        // Create new GlyphLayout and defines used font and text
        layoutCredits = new GlyphLayout();
        layoutCredits.setText(game.font2, creditsText);

        // Create back button
        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {

            /**
             * changed Method change screen
             * The new screen depends on what choices player have done
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // If player press setting's button, it change to settings screen
                game.goSettingsScreen();
            }
        });

        // Create label for rules
        Label rules = new Label(rulesTxt, mySkin);

        // Defines font sizes and alignments
        rules.setFontScale(2);
        rules.setAlignment(Align.center);

        // Adds the buttons and rules on table, and puts the table on stage
        screenTable.add(backBtn).top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
        screenTable.add(rules).center().expand().row();
        stage.addActor(screenTable);

        Table logoTable = new Table();
        logoTable.setFillParent(true);
        logoTable.add(new Image(logo)).expand().bottom().right().size(Value.percentWidth(0.25f, screenTable), Value.percentHeight(0.25f, screenTable)).row();
        stage.addActor(logoTable);
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
        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch, creditsText, WORLDWIDTH*100/2-layoutCredits.width/2, (WORLDHEIGHT-0.3f)*100);
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
    public void resize(int width, int height) {game.screenPort.update(width, height, true);}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    /**
     * dispose method dispose mySkin, batch, backgrounds and stage.
     */
    @Override
    public void dispose() {
        background.dispose();
        logo.dispose();
        game.dispose();
        batch.dispose();
        mySkin.dispose();
        stage.dispose();
    }
}

