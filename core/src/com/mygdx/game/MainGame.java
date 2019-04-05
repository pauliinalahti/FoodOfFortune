package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Preferences;

public class MainGame extends Game {
    public static SpriteBatch batch;
    public static float WORLDWIDTH = 10;
    public static float WORLDHEIGHT = 5;
    public static int buttonSize = 3;
    public static float buttonSizeSmall = 2f;
    public static float titleSize = 5;
    public static float buttonSizeBig = 4f;

    OrthographicCamera camera;
    OrthographicCamera cameraFont;

    public BitmapFont font;
    public BitmapFont font2;
    public BitmapFont recipeFont;
    FreeTypeFontGenerator generator;

    public Viewport screenPort;
    public MyAssetsManager myAssetsManager = new MyAssetsManager();
    public Music backgroundMusic;
    public Music click;
    private Preferences preferences;

    public SpriteBatch getBatch() {
        return batch;
    }

    public class MyAssetsManager {
        public final AssetManager manager = new AssetManager();
        public final AssetManager manager2 = new AssetManager();

        public void queueAddSkin() {
            SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(com.mygdx.game.GameConstants.skinAtlas);
            manager.load(com.mygdx.game.GameConstants.skin, Skin.class, parameter);

            SkinLoader.SkinParameter parameter2 = new SkinLoader.SkinParameter(com.mygdx.game.GameConstants.skinAtlas2);
            manager2.load(com.mygdx.game.GameConstants.skin2, Skin.class, parameter2);

        }
    }

    public Preferences getPrefs() {
        if (preferences == null)
            preferences = Gdx.app.getPreferences("My Preferences");
        return preferences;
    }

    @Override
    public void create() {
        font = new BitmapFont();
        font2 = new BitmapFont();
        recipeFont = new BitmapFont();
        batch = new SpriteBatch();
        click = Gdx.audio.newMusic(Gdx.files.internal("music/click.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sunshine_Samba.mp3"));
        if(getPrefs().getBoolean("music"))  {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
        generator = new FreeTypeFontGenerator(Gdx.files.internal("VarelaRound-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        //parameter.borderColor = Color.BLACK;
        parameter.color.add(Color.BLACK);
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 45;
        //parameter2.borderColor = Color.BLACK;
        //parameter2.color.add(Color.BLACK);
        parameter2.borderWidth = 4;
        font2 = generator.generateFont(parameter2);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 32;
        //parameter3.borderColor = Color.BLACK;
        //parameter3.color.add(Color.WHITE);
        parameter3.borderWidth = 3;
        recipeFont = generator.generateFont(parameter3);

        camera = new OrthographicCamera();
        cameraFont = new OrthographicCamera();
        camera.setToOrtho(false,WORLDWIDTH,WORLDHEIGHT);
        cameraFont.setToOrtho(false, WORLDWIDTH*100, WORLDHEIGHT*100);

        screenPort = new ScreenViewport();
        this.setScreen(new MainMenu(this));
    }

    public void goMainMenu() {
        playClick();
        MainMenu mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }


    public void goSlotMachine() {
        playClick();
        SlotMachine slotMachine = new SlotMachine(this);
        setScreen(slotMachine);
    }

    public void goSettingsScreen() {
        playClick();
        SettingsScreen settingsScreen = new SettingsScreen(this);
        setScreen(settingsScreen);
    }

    public void goDrawnIngredients(int first, int second, int third){
        playClick();
        DrawnIngredients drawnIngredients = new DrawnIngredients(this, first, second, third);
        setScreen(drawnIngredients);
    }

    public void goRecipes(int first, int second, int third){
        playClick();
        Recipes recipes = new Recipes(this, first, second, third);
        setScreen(recipes);
    }

    public void goFoodRecipe(int first, int second, int third, Recipe r){
        playClick();
        FoodRecipe foodRecipe = new FoodRecipe(this, first, second, third, r);
        setScreen(foodRecipe);
    }

    public void goCustomReels(){
        playClick();
        CustomReels customReels = new CustomReels(this);
        setScreen(customReels);
    }

    public void goCredits(){
        playClick();
        Credits credits = new Credits(this);
        setScreen(credits);
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
        backgroundMusic.dispose();
    }

    private void playClick() {
        if(preferences.getBoolean("music")) {
            click.play();
        }
    }
}
