package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class MainMenu implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    String playText;
    String settingsText;


    public MainMenu(MainGame g) {
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("mainMenu2.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        Preferences pref = Gdx.app.getPreferences("My Preferences");
        if(pref.getBoolean("english")){
            settingsText = "SETTINGS";
            playText = "PLAY";
        } else {
            settingsText = "ASETUKSET";
            playText = "PELAA";
        }


        Button startBtn = new TextButton(playText, mySkin, "small");startBtn.pad(20);
        startBtn.right().pad(20);
        //startBtn.setScale(WORLDWIDTH, WORLDHEIGHT);
        //startBtn.pad(-10f);
        //startBtn.setSize(WORLDWIDTH/7, WORLDHEIGHT/7);
        ((TextButton) startBtn).getLabel().setFontScale(game.buttonSize);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });

        Button settingsBtn = new TextButton(settingsText, mySkin, "small");
        settingsBtn.pad(20);
        ((TextButton) settingsBtn).getLabel().setFontScale(game.buttonSize);
        settingsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSettingsScreen();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(startBtn);
        table.add(settingsBtn);
        table.bottom().pad(30);
        //table.setDebug(true);

        table.setFillParent(true);
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
