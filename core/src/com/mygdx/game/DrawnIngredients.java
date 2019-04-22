package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * DrawnIngredients class handles drawn ingredients and it's screen
 * It implements screen
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class DrawnIngredients implements Screen {

    // Create Maingame object game
    MainGame game;

    // Create background for this screen
    Texture background;

    // Create Skin for this screen
    private Skin mySkin;

    // Create new stage
    private Stage stage;

    // Create spritebatch
    SpriteBatch batch;

    // Create firstreel's reel object
    FirstReel firstReel;

    // Create secondreel's reel object
    SecondReel secondReel;

    // Create thirdreel's reel object
    ThirdReel thirdReel;

    // These int tells what are drawn ingredients numbers in reels
    int firstDrawn, secondDrawn, thirdDrawn;

    // Create rectagle, helps to draw ingredients to screen
    Rectangle reelsRectangle;

    // Create glyphlayouts to handle different layouts
    GlyphLayout layoutFirst, layoutSecond, layoutThird, drawnIngrediends;

    // Create button's text
    String ingrediends, backText, recipesText;

    // Preferences to handle selected languages and food choices
    Preferences pref;

    // Create new Table
    Table screenTable;

    /**
     * DrawnIngredients's constructor
     *
     * @param g is MainGame object
     * @param first is first drawn ingredient's number
     * @param second is second drawn ingredient's number
     * @param third is third drawn ingredient's number
     */
    public DrawnIngredients(MainGame g, int first, int second, int third) {

        // Initializing the variables
        game = g;
        batch = game.getBatch();
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        reelsRectangle = new Rectangle(1.26f, 1.5f, 2.1f, 2.25f);
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_preSpin4.png"));

        pref = game.getPrefs();
        firstReel = new FirstReel(pref);
        secondReel = new SecondReel(pref);
        thirdReel = new ThirdReel(pref);


        // If preference language key is english, then buttons's text are in english
        if (pref.getBoolean("english")) {
            backText = "BACK";
            recipesText = "RECIPES";
            ingrediends = "INGREDIENTS";

            // Else used language is finnish
        } else {
            backText = "TAKAISIN";
            recipesText = "RESEPTIT";
            ingrediends = "ARVOTUT AINEKSET";
        }

        // Create three glyphlauot to handle three different layouts
        layoutFirst = new GlyphLayout();
        layoutSecond = new GlyphLayout();
        layoutThird = new GlyphLayout();

        layoutFirst.setText(game.font, firstReel.firstReelFoodNames.get(firstDrawn).toUpperCase());
        layoutSecond.setText(game.font, secondReel.secondReelFoodNames.get(secondDrawn).toUpperCase());
        layoutThird.setText(game.font, thirdReel.thirdReelFoodNames.get(thirdDrawn).toUpperCase());

        drawnIngrediends = new GlyphLayout();
        drawnIngrediends.setText(game.font2, ingrediends);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        screenTable = new Table();
        screenTable.setBackground(new TextureRegionDrawable(background));

        Button recipesBtn = new TextButton(recipesText, mySkin, "small");
        recipesBtn.pad(20);
        ((TextButton) recipesBtn).getLabel().setFontScale(game.buttonSize);
        recipesBtn.addListener(new ChangeListener() {
            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goRecipes(firstDrawn, secondDrawn, thirdDrawn);
            }
        });

        Button backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });

        // Adds buttons on table and the table on stage
        screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
        screenTable.add(recipesBtn).expand().center().bottom().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.15f, screenTable));
        screenTable.setFillParent(true);
        stage.addActor(screenTable);
    }

    /**
     * show method handle stage handling
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * render method renders the screen
     *
     * @param delta tells delta time
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();

        batch.draw(firstReel.firstReelImages.get(firstDrawn),
                WORLDWIDTH/2- 1.5f*reelsRectangle.width - 0.2f, reelsRectangle.y, reelsRectangle.width,
                reelsRectangle.height);
        batch.draw(secondReel.secondReelImages.get(secondDrawn),
                WORLDWIDTH/2-reelsRectangle.width/2, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
        batch.draw(thirdReel.thirdReelImages.get(thirdDrawn),
                WORLDWIDTH/2+reelsRectangle.width/2 + 0.2f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);

        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch,
                ingrediends,
                WORLDWIDTH * 100 / 2 - drawnIngrediends.width / 2,
                (WORLDHEIGHT - 0.3f) * 100);

        game.font.draw(batch,
                firstReel.firstReelFoodNames.get(firstDrawn).toUpperCase(),
                (WORLDWIDTH/2 * 100) - (reelsRectangle.width * 100)-20f - layoutFirst.width / 2,
                reelsRectangle.y * 100 - 20);
        game.font.draw(batch,
                secondReel.secondReelFoodNames.get(secondDrawn).toUpperCase(),
                (WORLDWIDTH/2 * 100) - layoutSecond.width / 2,
                reelsRectangle.y * 100 - 20);
        game.font.draw(batch,
                thirdReel.thirdReelFoodNames.get(thirdDrawn).toUpperCase(),
                (WORLDWIDTH/2 * 100) + (reelsRectangle.width * 100)+20f - layoutThird.width / 2,
                reelsRectangle.y * 100 - 20);

        batch.setProjectionMatrix((game.camera.combined));
        batch.end();
    }

    /**
     * resize method update screen size
     *
     * @param width tells new screen's width
     * @param height tells new screen's height
     */
    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Dispose method dispose background image, stage, game object and SpriteBatch
     * when player close the game
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        game.dispose();
        batch.dispose();
    }
}
