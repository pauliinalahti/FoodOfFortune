package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGame extends Game {
    public static SpriteBatch batch;
    //public static float WORLDWIDTH = 10;
    //public static float WORLDHEIGHT = 5;
    //OrthographicCamera camera;
    //OrthographicCamera cameraFont;

    //public BitmapFont font;
    //FreeTypeFontGenerator generator;

    public Viewport screenPort;
    public MyAssetsManager myAssetsManager = new MyAssetsManager();

    public SpriteBatch getBatch() {
        return batch;
    }

    public class MyAssetsManager {
        public final AssetManager manager = new AssetManager();

        public void queueAddSkin() {
            SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(com.mygdx.game.GameConstants.skinAtlas);
            manager.load(com.mygdx.game.GameConstants.skin, Skin.class, parameter);
        }
    }

    @Override
    public void create() {
        //font = new BitmapFont();
        batch = new SpriteBatch();
        /*generator = new FreeTypeFontGenerator(Gdx.files.internal("Raleway-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        cameraFont = new OrthographicCamera();*/

        screenPort = new ScreenViewport();
        this.setScreen(new MainMenu(this));
    }

    public void goMainMenu() {
        MainMenu mainMenu = new MainMenu(this);
        setScreen(mainMenu);
    }

    public void goSlotMachine() {
        SlotMachine slotMachine = new SlotMachine(this);
        setScreen(slotMachine);
    }

    public void goSettingsScreen() {
        SettingsScreen settingsScreen = new SettingsScreen(this);
        setScreen(settingsScreen);
    }

    public void goDrawnIngredients(){
        DrawnIngredients drawnIngredients = new DrawnIngredients(this);
        setScreen(drawnIngredients);
    }

    public void goRecipes(){
        Recipes recipes = new Recipes(this);
        setScreen(recipes);
    }

    @Override
    public void render() {
        //batch.setProjectionMatrix(camera.combined);
        super.render();
    }

    @Override
    public void dispose() {
        //batch.dispose();
        super.dispose();
    }
}
