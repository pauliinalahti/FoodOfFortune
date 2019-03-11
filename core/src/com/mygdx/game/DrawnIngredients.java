package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

public class DrawnIngredients implements Screen {

    MainGame game;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    SpriteBatch batch;
    FirstReel firstReel = new FirstReel();
    SecondReel secondReel = new SecondReel();
    ThirdReel thirdReel = new ThirdReel();
    int firstDrawn, secondDrawn, thirdDrawn;
    Rectangle reelsRectangle;

    BitmapFont font;

    public DrawnIngredients(MainGame g, int first, int second, int third){
        game = g;
        batch = game.getBatch();
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        font = new BitmapFont();
        reelsRectangle = new Rectangle(1.26f,1.7f,2.1f,2.25f);
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_arvotutainekset.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        Button recipesBtn = new TextButton("RECIPES", mySkin, "small");
        recipesBtn.pad(20);
        ((TextButton) recipesBtn).getLabel().setFontScale(game.buttonSize);
        recipesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goRecipes(firstDrawn,secondDrawn,thirdDrawn);
            }
        });

        /*Button backBtn = new TextButton("BACK", mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });*/

        Button backBtn = new TextButton("BACK", mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        table.add(recipesBtn);
        table.top();
        table.left();

        Table table2 = new Table();
        table2.defaults().uniform().pad(30);
        table2.add(recipesBtn);
        //table2.add(backBtn);
        table2.bottom().pad(20);
        //table.setDebug(true);

        table.setFillParent(true);
        table2.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);
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
        batch.draw(firstReel.firstReelImages.get(firstDrawn),
                reelsRectangle.x, reelsRectangle.y, reelsRectangle.width,
                reelsRectangle.height);
        batch.setProjectionMatrix(game.cameraFont.combined);

        batch.draw(secondReel.secondReelImages.get(secondDrawn),
                3.6f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
        batch.draw(thirdReel.thirdReelImages.get(thirdDrawn),
                5.94f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
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
