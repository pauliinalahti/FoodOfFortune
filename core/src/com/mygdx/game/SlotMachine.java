package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

import java.util.Random;

public class SlotMachine implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    Texture image;
    private Skin mySkin;
    private Stage stage;
    Image back;

    FirstReel firstReel;
    SecondReel secondReel;
    ThirdReel thirdReel;
    Rectangle reelsRectangle;
    private int firstReelTime, secondReelTime, thirdReelTime, i;
    private int drawnNumberFirstReel, drawnNumberSecondReel, drawnNumberThirdReeL;

    public SlotMachine(MainGame g){
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_slotmachine.png"));
        image = new Texture(Gdx.files.internal("banaani.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        reelsRectangle = new Rectangle(4/5f,6/5,2,2.3f);

        firstReel = new FirstReel();
        secondReel = new SecondReel();
        thirdReel = new ThirdReel();
        firstReelTime = 7;
        secondReelTime = 11;
        thirdReelTime = 22;
        drawnNumberFirstReel = random(firstReel.firstReelImages.size());
        drawnNumberSecondReel = random(secondReel.secondReelImages.size());
        drawnNumberThirdReeL = random(thirdReel.thirdReelImages.size());
        i = 0;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        Button menuBtn = new TextButton("MENU", mySkin, "small");
        menuBtn.pad(20);
        ((TextButton) menuBtn).getLabel().setFontScale(game.buttonSize);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goMainMenu();
            }
        });

        Button playBtn = new TextButton("PLAY", mySkin, "small");
        playBtn.pad(20);
        //playBtn.rotateBy(90);
        ((TextButton) menuBtn).getLabel().setFontScale(game.buttonSize);
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goDrawnIngredients();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(menuBtn);
        table.top();
        table.left();
        //table.setDebug(true);

        Table table2 = new Table();
        table2.defaults().uniform().pad(30);
        table2.add(playBtn);
        table2.left().pad(10);
        table2.bottom().pad(30);

        table.setFillParent(true);
        stage.addActor(table);
        table2.setFillParent(true);
        stage.addActor(table2);
    }

    public int random(int numberOfImages){
        Random rand = new Random();
        int n = rand.nextInt(numberOfImages);
        return n;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(game.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if (i < firstReelTime) {
            batch.begin();
            batch.draw(firstReel.firstReelImages.get(random(firstReel.firstReelImages.size())), reelsRectangle.x, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            /*batch.draw(secondReel.secondReelImages.get(random(secondReel.secondReelImages.size())),3,reelsRectangle.y,
                    reelsRectangle.width,reelsRectangle.height);
            batch.draw(thirdReel.thirdReelImages.get(random(thirdReel.thirdReelImages.size())),5,reelsRectangle.y,
                    reelsRectangle.width,reelsRectangle.height);*/
            batch.end();
        } else {
            batch.begin();
            batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel), reelsRectangle.x, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            batch.end();
        }
        if (i < secondReelTime) {
            batch.begin();
            batch.draw(secondReel.secondReelImages.get(random(secondReel.secondReelImages.size())), 3, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            batch.end();
        } else {
            batch.begin();
            batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel), 3, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            batch.end();
        }

        if (i < secondReelTime) {
            batch.begin();
            batch.draw(thirdReel.thirdReelImages.get(random(thirdReel.thirdReelImages.size())), 5, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            batch.end();
        } else {
            batch.begin();
            batch.draw(thirdReel.thirdReelImages.get(drawnNumberThirdReeL), 5, reelsRectangle.y,
                    reelsRectangle.width, reelsRectangle.height);
            batch.end();
        }
        i++;
        try {
            Thread.sleep(150);
        } catch (Exception e) {}


        //Spacella avautuu arvotut ainekset screen
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.goDrawnIngredients();
        }

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
