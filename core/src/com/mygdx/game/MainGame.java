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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * MainGame changes the screen and has information used by other classes.
 * @author Pauliina Lahti
 * @version 8.4.2019
 */
public class MainGame extends Game {

    public static SpriteBatch batch;
    public static float WORLDWIDTH = 10;
    public static float WORLDHEIGHT = 5;
    public static int buttonSize = 2;
    public static float buttonSizeSmall = 1.5f;
    public static float titleSize = 5;
    public static float buttonSizeBig = 4f;
    final ArrayList<String> optionsFI = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni","makaroni","peruna","riisi","spagetti","tomaatti","sipuli","porkkana","parsakaali","paprika"));
    final ArrayList<String> optionsEN = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom","macaroni","potato","rice","spaghetti","tomato","onion","carrot","broccoli","bell pepper"));

    OrthographicCamera camera;
    OrthographicCamera cameraFont;

    public BitmapFont font;
    public BitmapFont font2;
    public BitmapFont recipeFont;
    FreeTypeFontGenerator generator;

    public Table deviceScreen;
    public Viewport screenPort;
    public MyAssetsManager myAssetsManager = new MyAssetsManager();
    public Music backgroundMusic;
    public Music click;

    private Preferences preferences;
    public int screenW;
    public int screenH;

    /**
     * Return the batch.
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * MyAssetsManager loads the skins used in the game.
     */
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

    /**
     * Return the preferences the game uses
     * @return preferences is returned preference object
     */
    public Preferences getPrefs() {
        if (preferences == null)
            preferences = Gdx.app.getPreferences("My Preferences");
        return preferences;
    }

    /**
     * create()-method is called once when class is created, in which objects properties are declared.
     */
    @Override
    public void create() {
        deviceScreen = new Table();
        deviceScreen.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //WORLDHEIGHT = Gdx.graphics.getHeight();
        //WORLDWIDTH = Gdx.graphics.getWidth();
        WORLDWIDTH = 10;
        WORLDHEIGHT = 5;
        deviceScreen.defaults().uniform().pad(Value.percentHeight(0.1f, deviceScreen),Value.percentWidth(0.1f,deviceScreen), Value.percentHeight(0.1f, deviceScreen),Value.percentWidth(0.1f,deviceScreen));

        /**
         * Creates fonts named font, font2 ja recipefont, and changes their properties.
         */
        font = new BitmapFont();
        font2 = new BitmapFont();
        recipeFont = new BitmapFont();
        batch = new SpriteBatch();
        /*click = Gdx.audio.newMusic(Gdx.files.internal("music/click.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sunshine_Samba.mp3"));
        if(getPrefs().getBoolean("music"))  {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }*/
        generator = new FreeTypeFontGenerator(Gdx.files.internal("VarelaRound-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color.add(Color.BLACK);
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 45;
        parameter2.borderWidth = 4;
        font2 = generator.generateFont(parameter2);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter3.size = 32;
        parameter3.borderWidth = 3;
        recipeFont = generator.generateFont(parameter3);

        /**
         * Creates game camera and sets its position.
         */
        camera = new OrthographicCamera();
        cameraFont = new OrthographicCamera();
        camera.setToOrtho(false,WORLDWIDTH,WORLDHEIGHT);
        cameraFont.setToOrtho(false, WORLDWIDTH*100, WORLDHEIGHT*100);

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        /**
         * Click and background musics are declared.
         */
        click = Gdx.audio.newMusic(Gdx.files.internal("music/click.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sunshine_Samba.mp3"));

        initPrefs();

        /**
         * Changes the game screen to start screen.
         */
        screenPort = new ScreenViewport();

        //this.setScreen(new MainMenu(this));

        this.setScreen(new StartScreen(this));
    }

    private void initPrefs() {
        //map = preferences.get();
        if (Gdx.app.getPreferences("My Preferences").get().isEmpty()) {
            preferences = Gdx.app.getPreferences("My Preferences");
            preferences.putBoolean("done", true);
            //ArrayList<String> aineetFI = rcps.optionsFI;
            //ArrayList<String> aineetEN = rcps.optionsEN;
            for (String a : optionsFI) {
                preferences.putBoolean(a, true);
            }
            for (String a : optionsEN) {
                preferences.putBoolean(a, true);
            }
            preferences.flush();
        }
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
        }
    }

    /**
     * Plays the click-sound and background music, and sets the game screen to MainMenu.
     */
    public void goMainMenu() {
        playClick();
        if(getPrefs().getBoolean("music"))  {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
        MainMenu mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }

    /**
     * Plays the click-sound and sets the game screen to SlotMachine.
     */
    public void goSlotMachine() {
        playClick();
        SlotMachine slotMachine = new SlotMachine(this);
        setScreen(slotMachine);
    }

    /**
     * Plays the click-sound and sets the game screen to SettingScreen.
     */
    public void goSettingsScreen() {
        playClick();
        SettingsScreen settingsScreen = new SettingsScreen(this);
        setScreen(settingsScreen);
    }

    /**
     * Plays the click-sound and sets the game screen to DrawnIngredients.
     */
    public void goDrawnIngredients(int first, int second, int third){
        playClick();
        DrawnIngredients drawnIngredients = new DrawnIngredients(this, first, second, third);
        setScreen(drawnIngredients);
    }

    /**
     * Plays the click-sound and sets the game screen to Recipes.
     */
    public void goRecipes(int first, int second, int third){
        playClick();
        Recipes recipes = new Recipes(this, first, second, third);
        setScreen(recipes);
    }

    /**
     * Plays the click-sound and sets the game screen to FoodRecipe.
     */
    public void goFoodRecipe(int first, int second, int third, Recipe r){
        playClick();
        FoodRecipe foodRecipe = new FoodRecipe(this, first, second, third, r);
        setScreen(foodRecipe);
    }

    /**
     * Plays the click-sound and sets the game screen to CustomReels.
     */
    public void goCustomReels(){
        playClick();
        CustomReels customReels = new CustomReels(this);
        setScreen(customReels);
    }

    /**
     * Plays the click-sound and sets the game screen to Credits.
     */
    public void goCredits(){
        playClick();
        Credits credits = new Credits(this);
        setScreen(credits);
    }

    /**
     * Disposes the resources when the game is terminated.
     */
    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
        backgroundMusic.dispose();
    }

    /**
     * playClick method plays gamesounds when player clicks the play button
     */
    private void playClick() {
        if(preferences.getBoolean("music")) {
            click.play();
        }
    }
}

