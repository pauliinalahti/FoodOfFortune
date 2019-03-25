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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

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

    public SettingsScreen(MainGame g) {
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("backgroundBasic.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        //System.out.println(pref.getBoolean("english"));
        //pref.flush();

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        pref = Gdx.app.getPreferences("My Preferences");
        if(pref.getBoolean("english")){
            backText = "BACK";
            settingsText = "SETTINGS";
            languageText = "Language: ";
            onOffText = "ON";
            changeText = "Change";
        } else {
            backText = "TAKAISIN";
            settingsText = "ASETUKSET";
            languageText = "Kieli: ";
            onOffText = "OFF";
            changeText = "Vaihda";
        }

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


        Button testBtn = new TextButton(changeText, mySkin, "small");
        testBtn.pad(10);
        ((TextButton) testBtn).getLabel().setFontScale(game.buttonSizeSmall);
        testBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.app.getPreferences("My Preferences").getBoolean("english")) {
                    Gdx.app.getPreferences("My Preferences").putBoolean("english", false);
                } else {
                    Gdx.app.getPreferences("My Preferences").putBoolean("english", true);
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
        table2.add(testBtn);
        table2.setPosition(1f, (WORLDHEIGHT-1f-3f)*100);
        //table2.center();
        table2.left().pad(5);
        //table2.top().pad(20);

        table.setFillParent(true);
        table2.setFillParent(true);
        stage.addActor(table2);
        stage.addActor(table);


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

        game.recipeFont.draw(batch, languageText, 280, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
        if(Gdx.app.getPreferences("My Preferences").getBoolean("english")) {
            game.recipeFont.draw(batch, "English", 450, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
            pref.flush();
        } else {
            game.recipeFont.draw(batch, "Suomi", 450, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
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
