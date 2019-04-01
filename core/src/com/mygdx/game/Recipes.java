package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.math.Rectangle;

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
    final ArrayList<String> optionsFI = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni","makaroni","peruna","riisi","spagetti","tomaatti","sipuli","porkkana","parsakaali","paprika"));
    final ArrayList<String> optionsEN = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom","macaroni","potato","rice","spaghetti","tomato","onion","carrot","broccoli","bell pepper"));
    final ArrayList<String> options;

    FirstReel firstReel;
    SecondReel secondReel;

    String firstFood, secondFood;
    GlyphLayout recipestext;
    String recipesTxt;
    String backText;
    ArrayList<String> recipesVertical;
    ArrayList<Recipe> recipeMatches;
    int pad = 30;
    FileHandle file;
    Preferences pref;


    public Recipes(MainGame g, int first, int second, int third){
        game = g;
        batch = game.getBatch();

        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.4.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        recipesVertical = new ArrayList<String>();

        pref = game.getPrefs();
        firstReel = new FirstReel(pref);
        secondReel = new SecondReel(pref);

        if(pref.getBoolean("english")) {
            backText = "BACK";
            recipesTxt = "RECIPES";
            options = optionsEN;
            firstFood = firstReel.firstReelFoodNames.get(first);
            secondFood = secondReel.secondReelFoodNames.get(second);
        } else {
            backText = "TAKAISIN";
            recipesTxt = "RESEPTIT";
            options = optionsFI;
            firstFood = firstReel.firstReelFoodNames.get(first);
            secondFood = secondReel.secondReelFoodNames.get(second);
        }

        recipestext = new GlyphLayout();
        recipestext.setText(game.font2, recipesTxt);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        Button backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goDrawnIngredients(firstDrawn,secondDrawn,thirdDrawn);
            }
        });


        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        table.top();
        table.left();
        // table.setDebug(true);
        table.setFillParent(true);
        stage.addActor(table);

        if(!pref.getBoolean("english")) {
            file = Gdx.files.internal("recipefile.txt");

            String text = file.readString();
            Scanner sc = new Scanner(text);
            Locale loc = new Locale("fi", "FI");
            sc.useLocale(loc);
            int pad = 20;
            while (sc.hasNextLine()) {
                if(sc.findInLine("nimi:")!=null) {
                    String recName = sc.nextLine();
                    sc.findInLine("ainekset:");
                    String str = sc.nextLine();
                    ArrayList<String> items = new  ArrayList<String>(Arrays.asList(str.split("[, ?.@]+")));
                    sc.findInLine("ainemaarat:");
                    String amount = sc.nextLine();
                    sc.findInLine("ohje:");
                    String recMethod = sc.nextLine();
                    //System.out.println(recName+":"+items.toString()+":"+amount+":"+recMethod);
                    Recipe newRec = new Recipe(recName, (ArrayList<String>) items, recMethod);
                    newRec.addAmount(amount);
                    recipes.add(newRec);
                }
                else {
                    sc.nextLine();
                }
            }
            sc.close();
        } else {
            file = Gdx.files.internal("recipefileEN.txt");
            //System.out.println(firstFood + secondFood);

            String text = file.readString();
            Scanner sc = new Scanner(text);
            Locale loc = new Locale("fi", "FI");
            sc.useLocale(loc);
            int pad = 20;
            while (sc.hasNextLine()) {
                if(sc.findInLine("Name:")!=null) {
                    String recName = sc.nextLine();
                    sc.findInLine("Ingredients:");
                    String str = sc.nextLine();
                    ArrayList<String> items = new  ArrayList<String>(Arrays.asList(str.split("[, ?.@]+")));
                    sc.findInLine("ingredients:");
                    String amount = sc.nextLine();
                    sc.findInLine("Help:");
                    String recMethod = sc.nextLine();
                    //System.out.println(recName+":"+items.toString()+":"+amount+":"+recMethod);
                    Recipe newRec = new Recipe(recName, (ArrayList<String>) items, recMethod);
                    newRec.addAmount(amount);
                    recipes.add(newRec);
                    //System.out.println(newRec.name + newRec.ingredients + newRec.method + newRec.amount);
                }
                else {
                    sc.nextLine();
                }
            }
            sc.close();
        }


        Table table2 = new Table();
        recipeMatches = new ArrayList<Recipe>();
        for (final Recipe r:recipes) {
            if(r.ingredients.contains(secondFood.toLowerCase()) && r.ingredients.contains(firstFood.toLowerCase())) {
                boolean saakoLisata = true;
                for(String ingr : r.ingredients) {
                    if(!pref.getBoolean(ingr) && options.contains(ingr)) {
                        System.out.println(ingr + " bannattu, " + r.name + " ei saa lisätä!");
                        saakoLisata = false;
                    }
                }
                if (saakoLisata) {
                    recipeMatches.add(r);
                    System.out.println(r.name + " lisätty");
                    //r.name.replace("ä","a");
                    //r.name.replace("ö","o");
                    //Button recipeBtn = new TextButton(r.name.replace("ä","a"), mySkin, "small");
                    Button recipeBtn = new TextButton(r.name, mySkin, "small");
                    recipeBtn.pad(15);
                    ((TextButton) recipeBtn).getLabel().setFontScale(game.buttonSizeSmall);
                    recipeBtn.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.goFoodRecipe(firstDrawn,secondDrawn,thirdDrawn, r);
                        }
                    });
                    table2.add(recipeBtn);
                    table2.row();
                    pad += 5;

                }
            }
        }
        table2.setFillParent(true);
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

        /*if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            FoodRecipe foodRecipe = new FoodRecipe(game, firstDrawn,secondDrawn,thirdDrawn, r);
            game.setScreen(foodRecipe);
        }*/

        batch.begin();
        //batch.draw(rect, rect.x, rect.y, rect.width, rect.height);
        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch, recipestext, (WORLDWIDTH*100/2)-recipestext.width/2, (WORLDHEIGHT-0.3f)*100);
        float foodY = 1.5f;
        /*for (Recipe r: recipeMatches) {
            game.font2.draw(batch, r.name, (WORLDWIDTH*100/3)-recipestext.width/2, (WORLDHEIGHT-foodY)*100);
            foodY += 0.5f;
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

