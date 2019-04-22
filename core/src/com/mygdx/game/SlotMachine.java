package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.util.Random;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * SlotMachine class handle the slot machine's mechanics
 * It implements screen
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class SlotMachine implements Screen {

    // Create Maingame object game to handle mainmenus variables
    MainGame game;

    // Create new Spritebatch batch
    SpriteBatch batch;

    // Create background for this screen
    Texture background;

    // Create new texture to handle texture's
    Texture myTexture;

    // Create three textures to handle slot reels spinning animation
    Texture firstReelRoll;
    Texture secondReelRoll;
    Texture thirdReelRoll;

    // Create three Animations to hanlde those reels animations
    Animation<TextureRegion> firstReelSheet;
    Animation<TextureRegion> secondReelSheet;
    Animation<TextureRegion> thirdReelSheet;

    // Int variable to handle first reel animation's cols
    private int FRAME_COLS1 = 3;

    // Int variable to handle first reel animation's rows
    private int FRAME_ROWS1 = 2;

    // Int variable to handle second reel animation's cols
    private int FRAME_COLS2 = 3;

    // Int variable to handle second reel animation's rows
    private int FRAME_ROWS2 = 1;

    // Int variable to handle third reel animation's cols
    private int FRAME_COLS3 = 5;

    // Int variable to handle third reel animation's rows
    private int FRAME_ROWS3 = 1;

    // Create TextureRegion to handle animations
    TextureRegion [] frames;

    // Create TextureRegion to handle animations
    TextureRegion myTextureRegion;

    // Create TextureRegionDrawable
    TextureRegionDrawable myTexRegionDrawable;

    // Create Skin to handle Screen's skin
    private Skin mySkin;

    // Create new Stage
    private Stage stage;

    // Create FirstReel object
    FirstReel firstReel;

    // Create SecondReel object
    SecondReel secondReel;

    // Create ThirdReel object
    ThirdReel thirdReel;

    // Create new Rectangle to handle reels rectangles
    Rectangle reelsRectangle;

    // Create buttons to handle screen changing
    Button playBtn, customBtn;

    // New Table to handle screen layering
    Table screenTable;

    // Musics for this screen
    public Music handleMusic, reelMusic, reelStop;

    // Music for ready status
    Music ready;

    // Ínt variables to handle how long reels rolls
    private int firstReelTime, secondReelTime, thirdReelTime, i;

    // Int variables to handle drawn numbers = ingredients
    private int drawnNumberFirstReel, drawnNumberSecondReel, drawnNumberThirdReeL;

    // Create boolean to check playing status
    boolean play = false;

    // Create boolean to handle startImages showing
    boolean startImages = true;

    // Create prefence to handle players choices in settings
    Preferences pref;

    // Create texts for the buttons
    String backText, playText, customText;

    /**
     * SlotMachine's constructor
     *
     * @param g is Maingame object
     */
    public SlotMachine(MainGame g){

        // Initializing the variables
        game = g;
        batch = game.getBatch();
        firstReelRoll = new Texture(Gdx.files.internal("firstReelRoll.png"));
        secondReelRoll = new Texture(Gdx.files.internal("secondReelSheet.png"));
        thirdReelRoll = new Texture(Gdx.files.internal("thirdReelSheet.png"));
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_preSpin2.png"));
        handleMusic = Gdx.audio.newMusic(Gdx.files.internal("music/lever3.mp3"));
        reelMusic = Gdx.audio.newMusic(Gdx.files.internal("music/spinning.mp3"));
        reelStop = Gdx.audio.newMusic(Gdx.files.internal("music/reelStops2.mp3"));
        ready = Gdx.audio.newMusic(Gdx.files.internal("music/lastReelStops.mp3"));
        reelsRectangle = new Rectangle(1.26f,1.5f,2.1f,2.25f);
        pref = game.getPrefs();

        // If pref key is english, then used language is english
        if(pref.getBoolean("english")){
            backText = "BACK";
            playText = "PLAY";
            customText = "Custom reels";
        // Else it is finnish
        } else {
            backText = "TAKAISIN";
            playText = "PELAA";
            customText = "Muokkaa ruoka-aineksia";
        }

        firstReel = new FirstReel(pref);
        secondReel = new SecondReel(pref);
        thirdReel = new ThirdReel(pref);

        firstReelTime = 15;
        secondReelTime = 30;
        thirdReelTime = 50;

        // Draw ingredients for different reels
        drawnNumberFirstReel = random(firstReel.firstReelFoodNames.size());
        drawnNumberSecondReel = random(secondReel.secondReelFoodNames.size());
        drawnNumberThirdReeL = random(thirdReel.thirdReelFoodNames.size());

        i = 0;

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        screenTable = new Table();
        screenTable.setBackground(new TextureRegionDrawable(background));

        // Create new button that player can go back to mainmenu
        final Button backBtn = new TextButton(backText, mySkin, "small");
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
                // If player press go main menu button, it change screen to mainmenu
                game.goMainMenu();
            }
        });

        // Create new customBtn to handle button that goes to settings
        customBtn = new TextButton(customText, mySkin, "small");
        customBtn.pad(20);
        ((TextButton) customBtn).getLabel().setFontScale(game.buttonSizeSmall);
        customBtn.addListener(new ChangeListener() {
            /**
             * changed Method change third reels ingredients choices
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // If player press button, it change screen to customreels
                game.goCustomReels();
            }
        });

        myTexture = new Texture(Gdx.files.internal("handleUp.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);

        // Create button
        playBtn = new ImageButton(myTexRegionDrawable);
        playBtn.pad(0,200,0,-50);
        playBtn.addListener(new ChangeListener() {
            /**
             * changed Method change screen
             * The new screen depends on what choices player have done
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(pref.getBoolean("music")){
                    handleMusic.play();
                }
                screenTable.removeActor(playBtn);
                screenTable.removeActor(customBtn);
                screenTable.removeActor(backBtn);
                screenTable.clearChildren();
                myTexture = new Texture(Gdx.files.internal("handleDown.png"));
                myTextureRegion = new TextureRegion(myTexture);
                myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
                playBtn = new ImageButton(myTexRegionDrawable);
                playBtn.pad(0,200,0,-50);
                screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
                screenTable.add(playBtn).expand().center().right().size(Value.percentHeight(0.5f, screenTable));
                screenTable.add().pad(game.screenW/100).row();
                screenTable.add(customBtn).expand().center().bottom().size(Value.percentWidth(0.4f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
                screenTable.add().pad(game.screenW/100);
                startImages = false;
                play = true;
                i = 0;
            }
        });

        screenTable.add(backBtn).left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
        screenTable.add(playBtn).expand().center().right().size(Value.percentHeight(0.5f, screenTable));
        screenTable.add().pad(game.screenW/100).row();
        screenTable.add(customBtn).expand().center().bottom().size(Value.percentWidth(0.4f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add().pad(game.screenW/100);
        screenTable.setFillParent(true);
        stage.addActor(screenTable);

        //First reel spritesheet
        spriteSheet(firstReelRoll, FRAME_COLS1, FRAME_ROWS1);
        firstReelSheet = new Animation<TextureRegion>(1/10f, frames);

        //Second reel spritesheet
        spriteSheet(secondReelRoll, FRAME_COLS2, FRAME_ROWS2);
        secondReelSheet = new Animation<TextureRegion>(1/10f, frames);

        //Third reel spritesheet
        spriteSheet(thirdReelRoll, FRAME_COLS3, FRAME_ROWS3);
        thirdReelSheet = new Animation<TextureRegion>(1/10f, frames);
    }

    /**
     * spriteSheet method split texture to 2D Array
     *
     * @param reelRoll is texture in screen
     * @param cols tells how many cols are in the texture
     * @param rows telss how many rows are in the tecxture
     */
    private void spriteSheet(Texture reelRoll, int cols, int rows){
        TextureRegion[][] tmp = TextureRegion.split(reelRoll,
                reelRoll.getWidth() / cols,
                reelRoll.getHeight() / rows);
        frames = transformTo1D(tmp, cols, rows);
    }

    /**
     * TextureTegion[] transformto1D method chande 2D Array to 1D
     *
     * @param tmp is 2d Array which includes texture
     * @param cols tells how many cols are in the 2D array
     * @param rows tells how many rows are in the 2D Array
     * @return frames in 1D format
     */
    private TextureRegion[] transformTo1D( TextureRegion [][] tmp, int cols, int rows) {
        TextureRegion [] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }

    private TextureRegion currentFrame1;
    private TextureRegion currentFrame2;
    private TextureRegion currentFrame3;
    private float stateTime = 0.0f;

    /**
     * random method gives random number related to
     * how many ingredients are in the reels
     *
     * @param numberOfImages tells number of images in the reel
     * @return n is random number
     */
    public int random(int numberOfImages){
        Random rand = new Random();
        int n = rand.nextInt(numberOfImages);
        return n;
    }

    /**
     * show method handle stages
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
        batch.setProjectionMatrix(game.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();

        //CurrentFrames handle frames in different slot reels
        currentFrame1 = firstReelSheet.getKeyFrame(stateTime, true);
        currentFrame2 = secondReelSheet.getKeyFrame(stateTime, true);
        currentFrame3 = thirdReelSheet.getKeyFrame(stateTime, true);

        stage.act();
        stage.draw();

        // Handle slot reels places in the screen
        float firstReelx = WORLDWIDTH/2- 1.5f*reelsRectangle.width - 0.2f;
        float secondReelx = WORLDWIDTH/2-reelsRectangle.width/2;
        float thirdReelx = WORLDWIDTH/2+reelsRectangle.width/2 + 0.2f;

        // If play boolean is true
        if(play) {
            // If pref key is music, then game plays music
            if(pref.getBoolean("music")){
                playEffects(i);
            }
            // If i is less than FirstReeltime then game draws ingredients in the reel
            if (i < firstReelTime) {
                batch.begin();
                batch.draw(currentFrame1, firstReelx, reelsRectangle.y, reelsRectangle.width,
                        reelsRectangle.height);
                sleep();
                batch.end();
            // Else first reel shows what ingredient have drawn
            } else {
                batch.begin();
                batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel), firstReelx,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            // If i is less than SecondReeltime then game draws ingredients in the reel
            if (i < secondReelTime) {
                batch.begin();
                batch.draw(currentFrame2, secondReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                sleep();
                batch.end();
                // Else second reel shows what ingredient have drawn
            } else {
                batch.begin();
                batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel), secondReelx,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            // If i is less than ThirdReeltime then game draws ingredients in the reel
            if (i < thirdReelTime) {
                batch.begin();
                batch.draw(currentFrame3, thirdReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                sleep();
                batch.end();
                // Else third reel shows what ingredient have drawn
            } else {
                batch.begin();
                batch.draw(thirdReel.thirdReelImages.get(drawnNumberThirdReeL), thirdReelx,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            try {
                Thread.sleep(60);
            } catch (Exception e) {
            }
            if (i == thirdReelTime) {
                play = false;
            }
            i++;
        // If play boolean is false, the game render drawn ingredients in the screen
        } else {
            batch.begin();
            batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel),
                    firstReelx, reelsRectangle.y, reelsRectangle.width,
                    reelsRectangle.height);
            batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel),
                    secondReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.draw(thirdReel.thirdReelImages.get(drawnNumberThirdReeL),
                    thirdReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
            batch.end();
            // If all reels have finished drawing, then reel music stops
            if(i >= thirdReelTime) {
                reelMusic.stop();
                try {
                    Thread.sleep(700);
                } catch (Exception e) {
                }
                if(pref.getBoolean("music")){
                    ready.play();
                }
                // When game have sleep a little moment, it change screen to drawingredients screen
                game.goDrawnIngredients(drawnNumberFirstReel,drawnNumberSecondReel,
                        drawnNumberThirdReeL);
            }
        }
    }

    /**
     * sleep method sleep screen short time
     */
    private void sleep(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
    }

    /**
     * playEffects method handle effect showing in the screen
     * It play music in different situations
     *
     * @param i tells how long slot have spinned
     */
    private void playEffects(int i) {
        // If i=1 then play reelmusic
        if(i == 1) {
            reelMusic.play();
        }
        // Else play reelstop music
        else if(i == firstReelTime || i==secondReelTime || i==thirdReelTime) {
            reelStop.setLooping(false);
            reelStop.play();
        } else if(i == secondReelTime-1 || i==thirdReelTime-1) {
            reelStop.stop();
        }
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * dispose method dispose mySkin, batch, backgrounds, musics and stage.
     */
    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        firstReelRoll.dispose();
        secondReelRoll.dispose();
        game.dispose();
        batch.dispose();
        myTexture.dispose();
        ready.dispose();
        handleMusic.dispose();
        reelMusic.dispose();
        reelStop.dispose();
        mySkin.dispose();
    }
}

