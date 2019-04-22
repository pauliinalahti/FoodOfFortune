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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * MainGame changes the screen and has information used by other classes.
 *
 * @author Pauliina Lahti, Joona Neuvonen
 *
 * @version 8.4.2019
 */
public class MainGame extends Game {

    // Create SpriteBatch
    public static SpriteBatch batch;

    // Create floats to handle camera
    public static float WORLDWIDTH = 10;
    public static float WORLDHEIGHT = 5;

    // Int to handle buttonsize
    public static int buttonSize = 2;
    public static float buttonSizeSmall = 1.5f;

    public static float titleSize = 5;
    public static float buttonSizeBig = 4f;

    // Create ArrayList to handle finnish and english ingredients
    final ArrayList<String> optionsFI = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni","makaroni","peruna","riisi","spagetti","tomaatti","sipuli","porkkana","parsakaali","paprika"));
    final ArrayList<String> optionsEN = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom","macaroni","potato","rice","spaghetti","tomato","onion","carrot","broccoli","bell pepper"));

    // Cameras to handle screen showing
    OrthographicCamera camera;
    OrthographicCamera cameraFont;

    // BitmapFonts to handle fonts in this screen
    public BitmapFont font;
    public BitmapFont font2;
    public BitmapFont recipeFont;
    FreeTypeFontGenerator generator;

    // Create Table
    public Table deviceScreen;

    // Create Viewport
    public Viewport screenPort;
    public MyAssetsManager myAssetsManager = new MyAssetsManager();

    // Create Music for game's backgroundmusic
    public Music backgroundMusic;

    // Music for some effetc
    public Music click;

    // Preference to handle player choices
    private Preferences preferences;

    // Scaling ints
    public int screenW;
    public int screenH;

    /**
     * SpriteBatch method return Spritebatch
     * @return batch.
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

        // Initializing variables
        deviceScreen = new Table();
        deviceScreen.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        WORLDWIDTH = 10;
        WORLDHEIGHT = 5;
        deviceScreen.defaults().uniform().pad(Value.percentHeight(0.1f, deviceScreen),Value.percentWidth(0.1f,deviceScreen), Value.percentHeight(0.1f, deviceScreen),Value.percentWidth(0.1f,deviceScreen));

        font = new BitmapFont();
        font2 = new BitmapFont();
        recipeFont = new BitmapFont();
        batch = new SpriteBatch();

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

        camera = new OrthographicCamera();
        cameraFont = new OrthographicCamera();
        camera.setToOrtho(false,WORLDWIDTH,WORLDHEIGHT);
        cameraFont.setToOrtho(false, WORLDWIDTH*100, WORLDHEIGHT*100);

        screenW = Gdx.graphics.getWidth();
        screenH = Gdx.graphics.getHeight();

        // Click and background musics are declared
        click = Gdx.audio.newMusic(Gdx.files.internal("music/click.mp3"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sunshine_Samba.mp3"));

        initPrefs();

        screenPort = new ScreenViewport();
        this.setScreen(new StartScreen(this));
    }

    /**
     * initPrefs() method
     * Initializes default preferences if the game is started for the first time.
     */
    private void initPrefs() {
        if (Gdx.app.getPreferences("My Preferences").get().isEmpty()) {
            preferences = Gdx.app.getPreferences("My Preferences");
            preferences.putBoolean("done", true);
            for (String a : optionsFI) {
                preferences.putBoolean(a, true);
            }
            for (String a : optionsEN) {
                preferences.putBoolean(a, true);
            }
            preferences.putBoolean("music", true);
            preferences.flush();
        }
    }

    /**
     * goMainMenu method handle music playing
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
     * goSlotMachine method
     * Plays the click-sound and sets the game screen to SlotMachine.
     */
    public void goSlotMachine() {
        playClick();
        SlotMachine slotMachine = new SlotMachine(this);
        setScreen(slotMachine);
    }

    /**
     * gosettingsScreen method change screen
     * Plays the click-sound and sets the game screen to SettingScreen.
     */
    public void goSettingsScreen() {
        playClick();
        SettingsScreen settingsScreen = new SettingsScreen(this);
        setScreen(settingsScreen);
    }

    /**
     * goDrawnIngredients method handle ingredients
     *
     * @param first is drawn ingredient in first reel
     * @param second is drawn ingredient in second reel
     * @param third is drawn ingredient in third reel
     */
    public void goDrawnIngredients(int first, int second, int third){
        playClick();
        DrawnIngredients drawnIngredients = new DrawnIngredients(this, first, second, third);
        setScreen(drawnIngredients);
    }

    /**
     * goRecipes method change screen to recipe screen
     *
     * @param first is drawn ingredient in first reel
     * @param second is drawn ingredient in second reel
     * @param third is drawn ingredient in third reel
     */
    public void goRecipes(int first, int second, int third){
        playClick();
        Recipes recipes = new Recipes(this, first, second, third);
        setScreen(recipes);
    }

    /**
     * goFoodRecipe method change screen
     *
     * @param first is drawn ingredient in first reel
     * @param second is drawn ingredient in second reel
     * @param third is drawn ingredient in third reel
     */
    public void goFoodRecipe(int first, int second, int third, Recipe r){
        playClick();
        FoodRecipe foodRecipe = new FoodRecipe(this, first, second, third, r);
        setScreen(foodRecipe);
    }

    /**
     * goCustomReels method change screen to customreels screen
     */
    public void goCustomReels(){
        playClick();
        CustomReels customReels = new CustomReels(this);
        setScreen(customReels);
    }

    /**
     * goCredits method change screen to credits screen
     */
    public void goCredits(){
        playClick();
        Rules rules = new Rules(this);
        setScreen(rules);
    }

    /**
     * Dispose method dispose resources when the game is terminated.
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


