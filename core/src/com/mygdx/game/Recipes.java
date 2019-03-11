package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

public class Recipes implements Screen {

    MainGame game;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    int firstDrawn, secondDrawn, thirdDrawn;

    public Recipes(MainGame g, int first, int second, int third){
        game = g;
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_reseptit.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);



        Button menuBtn = new TextButton("BACK", mySkin, "small");
        menuBtn.pad(20);
        ((TextButton) menuBtn).getLabel().setFontScale(game.buttonSize);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goDrawnIngredients(firstDrawn,secondDrawn,thirdDrawn);
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(menuBtn);
        table.top();
        table.left();

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
