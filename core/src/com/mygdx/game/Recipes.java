package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class Recipes implements Screen {

    MainGame game;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    SpriteBatch batch;
    int firstDrawn, secondDrawn, thirdDrawn;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    FirstReel firstReel = new FirstReel();
    SecondReel secondReel = new SecondReel();
    String firstFood, secondFood;
    GlyphLayout recipestext;
    String recipesTxt = "RESEPTIT";
    ArrayList<String> recipesVertical;
    ArrayList<Recipe> recipeMatches;

    public Recipes(MainGame g, int first, int second, int third){
        game = g;
        batch = game.getBatch();
        firstFood = firstReel.firstReelFoodNames.get(first);
        secondFood = secondReel.secondReelFoodNames.get(second);
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("foodbackground2.jpg"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        recipesVertical = new ArrayList<String>();

        recipestext = new GlyphLayout();
        recipestext.setText(game.font2, recipesTxt);


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
        // table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);


        /*
        // Tulostetaan sopivat reseptit
        System.out.println("Ehdotetut reseptit:");
        for(Recipe r:recipeMatches) {
            System.out.println(r.name);
        }
        System.out.println("///////////////");*/

        File file = new File("recipefile.txt");
        try {
            Locale loc = new Locale("fi", "FI");
            Scanner sc = new Scanner(new FileInputStream(file), "UTF-8");
            sc.useLocale(loc);
            int pad = 20;
            while (sc.hasNextLine()) {
                if(sc.findInLine("nimi:")!=null) {
                    String recName = sc.nextLine();
                    sc.findInLine("ainekset:");
                    String str = sc.nextLine();
                    ArrayList<String> items = new  ArrayList<String>(Arrays.asList(str.split("[, ?.@]+")));
                    sc.findInLine("ohje:");
                    String recMethod = sc.nextLine();
                    Recipe newRec = new Recipe(recName.replaceAll(" ",""), (ArrayList<String>) items,recMethod.replaceAll(" ",""));
                    recipes.add(newRec);
                    // System.out.println(recName+":"+items.toString()+":"+recMethod);
                }
                else {
                    sc.nextLine();
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        recipeMatches = new ArrayList<Recipe>();
        for (Recipe r:recipes) {
            if(r.ingredients.contains(secondFood.toLowerCase()) && r.ingredients.contains(firstFood.toLowerCase())) {
                recipeMatches.add(r);
                System.out.println("Lisätty:");
                System.out.println(r.name+":"+r.ingredients.toString()+":"+r.method);
            }
        }

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
        game.font2.draw(batch, recipestext, (WORLDWIDTH*100/2)-recipestext.width/2, (WORLDHEIGHT-0.5f)*100);
        float foodY = 1.5f;
        for (Recipe r: recipeMatches) {
            game.font2.draw(batch, r.name, (WORLDWIDTH*100/3)-recipestext.width/2, (WORLDHEIGHT-foodY)*100);
            foodY += 0.5f;
        }
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