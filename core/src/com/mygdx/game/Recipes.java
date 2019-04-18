package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class Recipes implements Screen {

    MainGame game;
    Texture background;

    /** Create table for screen elements */
    Table screenTable;
    private Skin mySkin;
    private Stage stage;
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
    String noRecipes;
    String editText;
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
        recipesVertical = new ArrayList<String>();

        pref = game.getPrefs();
        firstReel = new FirstReel(pref);
        secondReel = new SecondReel(pref);

        /** Create new Table and adds the background */
        screenTable = new Table();
        screenTable.setFillParent(true);
        //screenTable.setBackground(new TextureRegionDrawable(background));

        if(pref.getBoolean("english")) {
            backText = "BACK";
            recipesTxt = "RECIPES";
            noRecipes = "No recipes found.\nPlease, update ingredient options\nand play again.";
            editText = "Update preferences";
            options = optionsEN;
            firstFood = firstReel.firstReelFoodNames.get(first);
            secondFood = secondReel.secondReelFoodNames.get(second);
        } else {
            backText = "TAKAISIN";
            recipesTxt = "RESEPTIT";
            noRecipes = "Haulla ei löytynyt yhtään reseptiä.\nLaajenna hakukriteereitäsi.";
            editText = "Päivitä hakukriteerejä";
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

        Button editBtn = new TextButton(editText, mySkin, "small");
        editBtn.pad(20);
        ((TextButton) editBtn).getLabel().setFontScale(game.buttonSize);
        editBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goCustomReels();
            }
        });

        Table bgtable = new Table();
        bgtable.setFillParent(true);
        bgtable.setBackground(new TextureRegionDrawable(background));
        bgtable.add(backBtn).left().size(Value.percentWidth(0.2f, bgtable), Value.percentHeight(0.13f, bgtable)).row();
        bgtable.top();
        bgtable.left();
        stage.addActor(bgtable);

        if(!pref.getBoolean("english")) {
            file = Gdx.files.internal("recipefile.txt");
            String text = file.readString();
            Scanner sc = new Scanner(text);
            Locale loc = new Locale("fi", "FI");
            sc.useLocale(loc);
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
            String text = file.readString();
            Scanner sc = new Scanner(text);
            Locale loc = new Locale("fi", "FI");
            sc.useLocale(loc);
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
                    Recipe newRec = new Recipe(recName, (ArrayList<String>) items, recMethod);
                    newRec.addAmount(amount);
                    recipes.add(newRec);
                }
                else {
                    sc.nextLine();
                }
            }
            sc.close();
        }
        recipeMatches = new ArrayList<Recipe>();
        for (final Recipe r:recipes) {
            if(r.ingredients.contains(secondFood.toLowerCase()) && r.ingredients.contains(firstFood.toLowerCase())) {
                boolean canBeAdded = true;
                for(String ingr : r.ingredients) {
                    if(!pref.getBoolean(ingr) && options.contains(ingr)) {
                        canBeAdded = false;
                    }
                }
                if (canBeAdded && recipeMatches.size()<6) {
                    recipeMatches.add(r);
                    Button recipeBtn = new TextButton(r.name, mySkin, "small");
                    recipeBtn.pad(15);
                    ((TextButton) recipeBtn).getLabel().setFontScale(game.buttonSizeSmall);
                    recipeBtn.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.goFoodRecipe(firstDrawn,secondDrawn,thirdDrawn, r);
                        }
                    });
                    screenTable.add(recipeBtn).pad(6).size(Value.percentWidth(0.45f, screenTable), Value.percentHeight(0.08f, screenTable)).row();
                    screenTable.row();
                    pad += 5;
                }
            }
        }
        if (recipeMatches.isEmpty()) {
            screenTable.add(editBtn).expand().center().bottom().size(Value.percentWidth(0.4f, screenTable), Value.percentHeight(0.15f, screenTable));
        }
        stage.addActor(screenTable);
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
        game.font2.draw(batch, recipestext, (WORLDWIDTH*100/2)-recipestext.width/2, (WORLDHEIGHT-0.3f)*100);
        if (recipeMatches.isEmpty()) {
            game.font.draw(batch, noRecipes, (WORLDWIDTH*70/2)-recipestext.width/2, (WORLDHEIGHT-2f)*100);
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

