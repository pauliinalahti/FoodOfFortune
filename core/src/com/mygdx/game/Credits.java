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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * Credits class introduce game development team
 * It implements screen
 *
 * @author      Pauliina lahti, Joona Neuvonen
 * @version     2019.4
 */

public class Credits implements Screen {
    MainGame game;
    SpriteBatch batch;

    /** Background's and logo's images */
    Texture background, logo;

    private Skin mySkin;
    private Stage stage;

    GlyphLayout layoutCredits, layoutTommi, layoutKristian, layoutPauliina, layoutJoona;

    /** Create button's text */
    String creditsText, backText;

    /**These four strings tells developer's names */
    String tommi = "Tommi Mäkeläinen";
    String kristian = "Kristian Levola";
    String pauliina = "Pauliina Lahti";
    String joona = "Joona Neuvonen";

    /** Preference defines current language */
    Preferences pref;

    /** Create backbutton, which leads to settings screen */
    Button backBtn;

    /**
     * Credit's constructor
     *
     * @param g is MainGame object
     */
    public Credits(MainGame g){
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        logo = new Texture(Gdx.files.internal("GGLogo11.png"));
        pref = game.getPrefs();
        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        /** If preference language is english, then button's text are in english */
        if(pref.getBoolean("english")){
            backText = "BACK";
            creditsText = "CREDITS";
            /** If preference language isn't english, then button's text are in finnish */
        } else {
            backText = "TAKAISIN";
            creditsText = "TEKIJÄT";
        }

        /** Create new GlyphLayout and defines used font and text */
        layoutCredits = new GlyphLayout();
        layoutCredits.setText(game.font2, creditsText);

        /** Create back button */
        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {

            /**
             * changed method change screen
             *
             * @param event
             * @param actor
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press setting's button, it change to settings screen */
                game.goSettingsScreen();
            }
        });

        /** Create four labels for every person */
        Label tommiM = new Label(tommi, mySkin);
        Label kristianL = new Label(kristian, mySkin);
        Label pauliinaL = new Label(pauliina, mySkin);
        Label joonaN = new Label(joona, mySkin);

        /** Defines font size*/
        tommiM.setFontScale(2);
        kristianL.setFontScale(2);
        pauliinaL.setFontScale(2);
        joonaN.setFontScale(2);

        /** Create table which allows to show background in the screen */
        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().uniform().pad(15);
        table.add(backBtn).row();
        table.left();
        table.top();

        /** Create table which allows to show person's names and logo in the screen */
        Table table2 = new Table();
        table2.defaults().uniform().pad(15);
        table2.addActor(new Image(logo));
        table2.add(tommiM).row();
        table2.add(kristianL).row();
        table2.add(pauliinaL).row();
        table2.add(joonaN);

        table.setFillParent(true);
        table2.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);
    }

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

    @Override
    public void dispose() {}
}
