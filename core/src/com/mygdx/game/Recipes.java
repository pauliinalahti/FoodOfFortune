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

import java.util.ArrayList;
import java.util.Arrays;

public class Recipes implements Screen {

    MainGame game;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    int firstDrawn, secondDrawn, thirdDrawn;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    FirstReel firstReel = new FirstReel();
    SecondReel secondReel = new SecondReel();
    String firstFood, secondFood;

    public Recipes(MainGame g, int first, int second, int third){
        game = g;
        firstFood = firstReel.firstReelFoodNames.get(first);
        secondFood = secondReel.secondReelFoodNames.get(second);
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

        recipes.add(new Recipe("pasta carbonara",  new ArrayList<String>( Arrays.asList("spagetti","pekoni","sipuli")),"kyl sä osaat"));
        recipes.add(new Recipe("makaronilaatikko",  new ArrayList<String>( Arrays.asList("makaroni", "jauheliha", "sipuli")),"kyl sä osaat"));
        recipes.add(new Recipe("nakit ja muussi",  new ArrayList<String>( Arrays.asList("nakkeja", "peruna")),"kyl sä osaat"));
        recipes.add(new Recipe("kalakeitto",  new ArrayList<String>( Arrays.asList("peruna", "lohi", "maito")),"kyl sä osaat"));
        recipes.add(new Recipe("pasta bolognese",  new ArrayList<String>( Arrays.asList("spagetti", "tomaatti", "jauheliha")),"kyl sä osaat"));

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

        Recipe temp;
        ArrayList<Recipe> recipeMatches = new ArrayList<Recipe>();
        for (Recipe r:recipes) {
            if(r.ingredients.contains(firstFood.toLowerCase()) && r.ingredients.contains(secondFood.toLowerCase())) {
                recipeMatches.add(r);
            }
        }
        // Tulostetaan sopivat reseptit
        System.out.println("Ehdotetut reseptit:");
        for(Recipe r:recipeMatches) {
            System.out.println(r.name);
        }
        System.out.println("///////////////");
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
