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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class SettingsScreen implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    GlyphLayout layoutSettings;
    String settingsText;
    String backText;
    String languageText;
    String onOffText;
    String changeText;
    Preferences pref;
    Button backBtn;
    String chosenLanguage;
    final ArrayList<String> options = new ArrayList<String>(Arrays.asList("jauheliha", "peruna", "paprika"));

    public SettingsScreen(MainGame g) {
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("backgroundBasic.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        pref = game.getPrefs();


        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        if(pref.getBoolean("english")){
            backText = "BACK";
            settingsText = "SETTINGS";
            languageText = "Language: ";
            onOffText = "ON";
            changeText = "Change";
            chosenLanguage = "English";
        } else {
            backText = "TAKAISIN";
            settingsText = "ASETUKSET";
            languageText = "Kieli: ";
            onOffText = "OFF";
            changeText = "Vaihda";
            chosenLanguage = "Suomi";
        }
        /*
        if (pref.getBoolean("firstTime")) {
            System.out.println("here");
            game.getPrefs().putBoolean("firstTime", true);
            game.getPrefs().putBoolean("jauheliha", true);
            game.getPrefs().putBoolean("peruna", true);
            game.getPrefs().flush();
        }
        */

        layoutSettings = new GlyphLayout();
        layoutSettings.setText(game.font2, settingsText);

        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goMainMenu();
            }
        });


        Button changeTextBtn = new TextButton(changeText, mySkin, "small");
        changeTextBtn.pad(15);
        ((TextButton) changeTextBtn).getLabel().setFontScale(game.buttonSize);
        changeTextBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPrefs().getBoolean("english")) {
                    game.getPrefs().putBoolean("english", false);
                    game.getPrefs().flush();
                    System.out.println("en");
                } else {
                    game.getPrefs().putBoolean("english", true);
                    game.getPrefs().flush();
                    System.out.println("fi");
                }
                game.goSettingsScreen();
            }
        });


        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        table.top();
        table.left();
        //table.setDebug(true);

        Table table2 = new Table();
        table2.defaults().uniform().pad(30);
        table2.add(changeTextBtn);
        table2.setPosition(1f, (WORLDHEIGHT-3.1f)*100);
        //table2.center();
        table2.left().pad(10);
        //table2.top().pad(20);

        Table tableCheckBoxes = new Table();
        tableCheckBoxes.defaults().uniform().pad(30);
        for (final String opt : options) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.getLabel().setFontScale(game.buttonSizeSmall);
            if(game.getPrefs().getBoolean(opt)){
                System.out.println("here2");
                Gdx.graphics.setContinuousRendering(true);
            }
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.graphics.setContinuousRendering(cb.isChecked());
                    if(game.getPrefs().getBoolean(opt)){
                        System.out.println("moro");
                        game.getPrefs().putBoolean(opt, false);
                    } else {
                        game.getPrefs().putBoolean(opt, true);
                    }
                    game.getPrefs().flush();
                    game.goSettingsScreen();
                }
            });
            tableCheckBoxes.add(cb);
            tableCheckBoxes.row();
        }
        tableCheckBoxes.setPosition(1f, (WORLDHEIGHT-6f)*100);
        //tableCheckBoxes.left().pad(30);

        table.setFillParent(true);
        table2.setFillParent(true);
        tableCheckBoxes.setFillParent(true);
        stage.addActor(table2);
        stage.addActor(table);
        stage.addActor(tableCheckBoxes);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        batch.begin();
        batch.setProjectionMatrix(game.cameraFont.combined);

        game.font2.draw(batch, settingsText, WORLDWIDTH*100/2-layoutSettings.width/2, (WORLDHEIGHT-0.5f)*100);

        game.font.draw(batch, chosenLanguage, 400, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);

        if(game.getPrefs().getBoolean("english")) {
            game.font.draw(batch, languageText, 230, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
            pref.flush();
        } else {
            game.font.draw(batch, languageText, 230, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
            pref.flush();
        }
        batch.setProjectionMatrix((game.camera.combined));

        batch.end();

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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}
