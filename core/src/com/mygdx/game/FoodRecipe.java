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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;

import static com.mygdx.game.GameConstants.skin;
import static com.mygdx.game.GameConstants.skin2;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * FoodRecipe class handle drawn food recipe
 * It implements screen
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class FoodRecipe implements Screen {

    /** Create game object from Maingame class*/
    MainGame game;

    /** Create Spritebatch */
    SpriteBatch batch;

    /** Create screen's background */
    Texture background;

    /** These ints tells what ingredients are drawn from the different reels */
    int firstDrawn, secondDrawn, thirdDrawn;

    /** Create used skins */
    private Skin mySkin, mySkin2;

    /** Create stage*/
    private Stage stage;

    /** Defines layouts when showing recipe */
    GlyphLayout layoutRecipeName, layoutMethod, layoutAmount;

    /** Create recipe object*/
    Recipe recipe;

    /** These strings defines food recipe's features */
    String recipeNameTxt, methodTxt, amountTxt;

    /** Strings for left and right colum*/
    String leftCol, rightCol;

    /** Create arraylist for food recipes */
    ArrayList<String> ingredientsTxt;

    /** Button text*/
    String backTxt;

    /** Float for scaling the screen */
    float scale;

    /**
     * Credit's constructor
     *
     * @param g is MainGame object
     * @param first tells first reel's drawn number
     * @param second tells second reel's drawn number
     * @param third tells third reel's drawn number
     * @param r is Recipe object
     */
    public FoodRecipe(MainGame g, int first, int second, int third, Recipe r) {

        game = g;
        recipe = r;
        recipeNameTxt = r.name;
        methodTxt = r.method;
        amountTxt = r.amount;
        ingredientsTxt = r.ingredients;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);

        /** Set image to background texture */
        background = new Texture(Gdx.files.internal("FOF_Tausta5.8.png"));

        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;

        /** Set scaling to 2f*/
        scale = 2f;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        game.myAssetsManager.manager2.finishLoading();
        mySkin = game.myAssetsManager.manager.get(skin);
        mySkin2 = game.myAssetsManager.manager2.get(skin2);

        /** Create new GlyphLayout to handle recipe's name */
        layoutRecipeName = new GlyphLayout();
        layoutRecipeName.setText(game.recipeFont, recipeNameTxt);

        /** Create new GlyphLayout to handle recipe's text */
        layoutMethod = new GlyphLayout();
        layoutMethod.setText(game.recipeFont, methodTxt);

        /** Create new GlyphLayout to handle food ingredients amounts */
        layoutAmount = new GlyphLayout();
        layoutAmount.setText(game.recipeFont, amountTxt);

        /** Get preferences from game object */
        Preferences pref = game.getPrefs();
        /** If language is english, then button text are in english, else in finnist*/
        if(pref.getBoolean("english")){
            backTxt = "BACK";
            leftCol = "INGREDIENTS";
            rightCol = "METHOD";
        } else {
            backTxt = "TAKAISIN";
            leftCol = "AINEKSET";
            rightCol = "OHJE";
        }

        /** Create back button to go previous screen*/
        Button backBtn = new TextButton(backTxt, mySkin, "small");
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
                /** If player press back button, it change previous screen */
                game.goRecipes(firstDrawn,secondDrawn,thirdDrawn);
            }
        });

        Label label2 = new Label(amountTxt.replaceFirst(" ", "").replace(", ", "\n"), mySkin2);
        label2.setAlignment(Align.top, Align.left);
        label2.setWrap(true);
        label2.pack();
        ScrollPane sp2 = new ScrollPane(label2);


        Label label = new Label(methodTxt.replaceFirst(" ","").replace("\\n", "\n"), mySkin2);
        label.setAlignment(Align.top, Align.left);
        label.setWrap(true);
        label.pack();
        ScrollPane sp = new ScrollPane(label);

        /** Create new table, it makes possible to show recipe in the screen*/
        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().pad(30);
        table.add(backBtn).left().row();
        Label leftLb = new Label(leftCol,mySkin);
        Label rightLb = new Label(rightCol,mySkin);
        leftLb.setFontScale(scale);
        rightLb.setFontScale(scale);
        table.add(leftLb);
        table.add(rightLb).row();
        table.add(sp2).top().width(450);
        table.add(sp).grow();
        table.top();
        table.setFillParent(true);
        stage.addActor(table);
    }

    /**
     * show method shows the stage
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

        /** Put camera combined */
        batch.setProjectionMatrix(game.cameraFont.combined);

        /** Shows food recipe's name in uppercase*/
        game.recipeFont.draw(batch,
                recipeNameTxt.toUpperCase(),
                WORLDWIDTH*100/2-layoutRecipeName.width/2,
                (WORLDHEIGHT-0.3f)*100);

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

