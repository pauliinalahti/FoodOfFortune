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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;

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
    Recipe r;
    String recipeNameTxt;
    String methodTxt;
    ArrayList<String> ingredientsTxt;

    public FoodRecipe(MainGame g, int first, int second, int third, Recipe r) {
        game = g;
        this.r = r;
        recipeNameTxt = r.name;
        methodTxt = r.method;
        ingredientsTxt = r.ingredients;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("foodbackground2.jpg"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

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
        float j = 1f;
        /*for(String i: ingredientsTxt) {
            game.recipeFont.draw(batch, i, 100, (WORLDHEIGHT-j)*100);
            j += 0.5f;
        }*/
        game.recipeFont.draw(batch, methodTxt, 100, (WORLDHEIGHT-j-0.5f)*100);
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
