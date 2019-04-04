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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.GameConstants.skin;
import static com.mygdx.game.GameConstants.skin2;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class FoodRecipe implements Screen {


    MainGame game;
    SpriteBatch batch;
    Texture background;
    int firstDrawn, secondDrawn, thirdDrawn;

    private Skin mySkin;
    private Skin mySkin2;
    private Stage stage;
    Image back;
    GlyphLayout layoutRecipeName;
    GlyphLayout layoutMethod;
    GlyphLayout layoutAmount;
    Recipe recipe;
    String recipeNameTxt;
    String methodTxt;
    String amountTxt;
    String leftCol;
    String rightCol;
    ArrayList<String> ingredientsTxt;
    String backTxt;
    float scale;

    public FoodRecipe(MainGame g, int first, int second, int third, Recipe r) {
        game = g;
        recipe = r;
        recipeNameTxt = r.name;
        methodTxt = r.method;
        amountTxt = r.amount;
        ingredientsTxt = r.ingredients;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.8.png"));
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        scale = 2f;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        game.myAssetsManager.manager2.finishLoading();
        mySkin = game.myAssetsManager.manager.get(skin);
        mySkin2 = game.myAssetsManager.manager2.get(skin2);

        layoutRecipeName = new GlyphLayout();
        layoutRecipeName.setText(game.recipeFont, recipeNameTxt);

        layoutMethod = new GlyphLayout();
        layoutMethod.setText(game.recipeFont, methodTxt);

        layoutAmount = new GlyphLayout();
        layoutAmount.setText(game.recipeFont, amountTxt);

        Preferences pref = game.getPrefs();
        if(pref.getBoolean("english")){
            backTxt = "BACK";
            leftCol = "INGREDIENTS";
            rightCol = "METHOD";
        } else {
            backTxt = "TAKAISIN";
            leftCol = "AINEKSET";
            rightCol = "OHJE";
        }

        Button backBtn = new TextButton(backTxt, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        batch.setProjectionMatrix(game.cameraFont.combined);
        game.recipeFont.draw(batch,
                recipeNameTxt.toUpperCase(),
                WORLDWIDTH*100/2-layoutRecipeName.width/2,
                (WORLDHEIGHT-0.3f)*100);

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

