package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private int firstReelTime, secondReelTime, thirdReelTime, i;
    private int drawnNumberFirstReel, drawnNumberSecondReel, drawnNumberThirdReeL;
    boolean play = false;
    boolean startImages = true;

    String backText;
    String playText;

    public SlotMachine(MainGame g){
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("bg2.png"));
        image = new Texture(Gdx.files.internal("banaani.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        reelsRectangle = new Rectangle(1.26f,1.155f,2.1f,2.25f);
        AddRecipes recipeControl = new AddRecipes();

        Preferences pref = Gdx.app.getPreferences("My Preferences");
        if(pref.getBoolean("english")){
            backText = "BACK";
            playText = "PLAY";
        } else {
            backText = "TAKAISIN";
            playText = "PELAA";
        }

        firstReel = new FirstReel();
        secondReel = new SecondReel();
        thirdReel = new ThirdReel();
        firstReelTime = 9;
        secondReelTime = 15;
        thirdReelTime = 22;
        drawnNumberFirstReel = random(firstReel.firstReelImages.size());
        drawnNumberSecondReel = random(secondReel.secondReelImages.size());
        drawnNumberThirdReeL = random(thirdReel.thirdReelImages.size());
        i = 0;

        recipes = recipeControl.AddAllRecipes(recipes);

        /*for (Recipe r: recipes){
            System.out.println(r.name);
        }*/

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        //testbutton
        Button testBtn = new TextButton("TEST", mySkin, "small");
        testBtn.pad(20);
        ((TextButton) testBtn).getLabel().setFontScale(game.buttonSize);
        testBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goDrawnIngredients(0,0,0);
            }
        });

        Button backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goMainMenu();
            }
        });

        Button playBtn = new TextButton(playText, mySkin, "small");
        playBtn.pad(20);
        ((TextButton) playBtn).getLabel().setFontScale(game.buttonSize);
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startImages = false;
                play = true;
                i = 0;

                //game.goDrawnIngredients();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        table.add(testBtn);
        table.top();
        table.left();
        //table.setDebug(true);

        Table table2 = new Table();
        table2.defaults().uniform().pad(30);
        table2.add(playBtn);
        table2.right().pad(10);
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
        if(startImages) {
            batch.begin();
            batch.draw(firstReel.firstReelImages.get(1),
                    reelsRectangle.x, reelsRectangle.y, reelsRectangle.width,
                    reelsRectangle.height);
            batch.draw(secondReel.secondReelImages.get(1),
                    3.6f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.draw(thirdReel.thirdReelImages.get(1),
                    5.94f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.end();
        }
        if(play) {
            if (i < firstReelTime) {
                batch.begin();
                batch.draw(firstReel.firstReelImages.get(random(firstReel.firstReelImages.size())),
                        reelsRectangle.x, reelsRectangle.y, reelsRectangle.width,
                        reelsRectangle.height);
                batch.end();
            } else {
                batch.begin();
                batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel), reelsRectangle.x,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            if (i < secondReelTime) {
                batch.begin();
                batch.draw(secondReel.secondReelImages.get(random(secondReel.secondReelImages.size())),
                        3.6f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            } else {
                batch.begin();
                batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel), 3.6f,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }

            if (i < secondReelTime) {
                batch.begin();
                batch.draw(thirdReel.thirdReelImages.get(random(thirdReel.thirdReelImages.size())),
                        5.94f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            } else {
                batch.begin();
                batch.draw(thirdReel.thirdReelImages.get(drawnNumberThirdReeL), 5.94f,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            i++;

            try {
                Thread.sleep(160);
            } catch (Exception e) {
            }
            if (i == thirdReelTime) {
                play = false;

            }
        } else {
            batch.begin();
            batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel),
                    reelsRectangle.x, reelsRectangle.y, reelsRectangle.width,
                    reelsRectangle.height);
            batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel),
                    3.6f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.draw(thirdReel.thirdReelImages.get(drawnNumberThirdReeL),
                    5.94f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.end();

            if(i == thirdReelTime) {
                try {
                    Thread.sleep(700);
                } catch (Exception e) {
                }

                game.goDrawnIngredients(drawnNumberFirstReel,drawnNumberSecondReel,
                        drawnNumberThirdReeL);

            }

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
