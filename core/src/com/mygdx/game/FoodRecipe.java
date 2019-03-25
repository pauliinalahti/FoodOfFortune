package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.GameConstants.skin;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class FoodRecipe implements Screen {


    MainGame game;
    SpriteBatch batch;
    Texture background;
    int firstDrawn, secondDrawn, thirdDrawn;

    private Skin mySkin;
    private Stage stage;
    Image back;
    GlyphLayout layoutRecipeName;
    GlyphLayout layoutMethod;
    Recipe recipe;
    String recipeNameTxt;
    String methodTxt;
    ArrayList<String> ingredientsTxt;

    public FoodRecipe(MainGame g, int first, int second, int third, Recipe r) {
        game = g;
        recipe = r;
        recipeNameTxt = r.name;
        methodTxt = r.method;
        ingredientsTxt = r.ingredients;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("recipeBg1.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(skin);

        //ScrollPane scrollPane=new ScrollPane(recipe.method, mySkin);

        layoutRecipeName = new GlyphLayout();
        layoutRecipeName.setText(game.font2, recipeNameTxt);

        layoutMethod = new GlyphLayout();
        layoutMethod.setText(game.recipeFont, methodTxt);

        Button backBtn = new TextButton("BACK", mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goRecipes(firstDrawn,secondDrawn,thirdDrawn);
            }
        });

        //Button sp = new ScrollPane(mySkin);

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
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

        batch.begin();
        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch, recipeNameTxt, WORLDWIDTH*100/2-layoutRecipeName.width/2, (WORLDHEIGHT-0.5f)*100);
        //game.recipeFont.draw(batch, "Hello World!", WORLDWIDTH*100/2-layoutRecipeName.width/2, (WORLDHEIGHT-0.5f)*100, 100, 100, true);

        float j = 1f;
        /*for(String i: ingredientsTxt) {
            game.recipeFont.draw(batch, i, 100, (WORLDHEIGHT-j)*100);
            j += 0.5f;
        }*/
        game.recipeFont.draw(batch, recipe.amount.replace(",", "\n"), 30, (WORLDHEIGHT-j-0.5f)*100, 350, -1, true);
        //ArrayList<String> amList = new  ArrayList<String>(Arrays.asList(recipe.amount.split("[,]+")));
        /*for(String a: amList) {
            int len = a.length();
            int k = (int) Math.ceil(len/12);
            //System.out.println("k: "+k);
            game.recipeFont.draw(batch, a+'\n', 50, (WORLDHEIGHT-j-0.5f)*100, 300, -1, true);
            if(k==0) {
                j += 0.25;
            } else {
                j += (0.25*k);
            }
            //System.out.println("len: "+len+", k: "+k+", j: "+j);
        }*/
        j=1f;
        //game.recipeFont.draw(batch, recipe.amount, 50, (WORLDHEIGHT-j-0.5f)*100, 350, -1, true);
        game.recipeFont.draw(batch, methodTxt.replace("\\n", "\n"), 400, (WORLDHEIGHT-j-0.5f)*100, 600, -1, true);
        /*j = 1f;
        ArrayList<String> methodList = new  ArrayList<String>(Arrays.asList(methodTxt.split("\\&")));
        for(String m: methodList) {
            game.recipeFont.draw(batch, m, 350, (WORLDHEIGHT-j-0.5f)*100, 600, 1, true);
            j += 0.5f;
        }*/




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

