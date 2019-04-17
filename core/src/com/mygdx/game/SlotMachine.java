package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class SlotMachine implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    Texture myTexture;

    Texture firstReelRoll;
    Texture secondReelRoll;
    Texture thirdReelRoll;
    Animation<TextureRegion> firstReelSheet;
    Animation<TextureRegion> secondReelSheet;
    Animation<TextureRegion> thirdReelSheet;
    private int FRAME_COLS1 = 3;
    private int FRAME_ROWS1 = 2;
    private int FRAME_COLS2 = 3;
    private int FRAME_ROWS2 = 1;
    private int FRAME_COLS3 = 5;
    private int FRAME_ROWS3 = 1;
    TextureRegion [] frames;

    TextureRegion myTextureRegion;
    TextureRegionDrawable myTexRegionDrawable;
    private Skin mySkin;
    private Stage stage;
    Image back;

    FirstReel firstReel;
    SecondReel secondReel;
    ThirdReel thirdReel;
    Rectangle reelsRectangle;
    Button playBtn;
    Button customBtn;
    ImageButton buttonSound;
    Table table2;
    Table screenTable;
    public Music handleMusic;
    public Music reelMusic;
    public Music reelStop;
    Music ready;

    public ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private int firstReelTime, secondReelTime, thirdReelTime, i;
    private int drawnNumberFirstReel, drawnNumberSecondReel, drawnNumberThirdReeL;
    boolean play = false;
    boolean startImages = true;
    Preferences pref;

    String backText;
    String playText;
    String customText;

    public SlotMachine(MainGame g){
        game = g;
        batch = game.getBatch();
        firstReelRoll = new Texture(Gdx.files.internal("firstReelRoll.png"));
        secondReelRoll = new Texture(Gdx.files.internal("secondReelSheet.png"));
        thirdReelRoll = new Texture(Gdx.files.internal("thirdReelSheet.png"));
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.9.png"));
        /*back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);*/
        //handleMusic = Gdx.audio.newMusic(Gdx.files.internal("lever3.mp3"));
        handleMusic = Gdx.audio.newMusic(Gdx.files.internal("music/lever3.mp3"));
        //reelMusic = Gdx.audio.newMusic(Gdx.files.internal("reel.mp3"));
        reelMusic = Gdx.audio.newMusic(Gdx.files.internal("music/spinning.mp3"));
        //reelStop = Gdx.audio.newMusic(Gdx.files.internal("reelStop.mp3"));
        reelStop = Gdx.audio.newMusic(Gdx.files.internal("music/reelStops2.mp3"));
        ready = Gdx.audio.newMusic(Gdx.files.internal("music/lastReelStops.mp3"));
        reelsRectangle = new Rectangle(1.26f,1.5f,2.1f,2.25f);
        /**AddRecipes recipeControl = new AddRecipes();*/


        pref = game.getPrefs();
        if(pref.getBoolean("english")){
            backText = "BACK";
            playText = "PLAY";
            customText = "Custom reels";
        } else {
            backText = "TAKAISIN";
            playText = "PELAA";
            customText = "Muokkaa ruoka-aineksia";
        }

        firstReel = new FirstReel(pref);
        secondReel = new SecondReel(pref);
        thirdReel = new ThirdReel(pref);
        System.out.println("eka reel: " + firstReel.firstReelFoodNames.toString());
        System.out.println("toka reel: " + secondReel.secondReelFoodNames.toString());
        System.out.println("kolmas reel: " + thirdReel.thirdReelFoodNames.toString());

        firstReelTime = 15;
        secondReelTime = 30;
        thirdReelTime = 50;
        //firstReel.updateReel(game.getPrefs());
        drawnNumberFirstReel = random(firstReel.firstReelFoodNames.size());
        drawnNumberSecondReel = random(secondReel.secondReelFoodNames.size());
        drawnNumberThirdReeL = random(thirdReel.thirdReelFoodNames.size());
        i = 0;

        /**recipes = recipeControl.AddAllRecipes(recipes);*/

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        //testbutton
        /*Button testBtn = new TextButton("TEST", mySkin, "small");
        testBtn.pad(20);
        ((TextButton) testBtn).getLabel().setFontScale(game.buttonSize);
        testBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goDrawnIngredients(0,0,0);
            }
        });*/

        screenTable = new Table();
        screenTable.setBackground(new TextureRegionDrawable(background));

        final Button backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goMainMenu();
            }
        });

        customBtn = new TextButton(customText, mySkin, "small");
        customBtn.pad(20);
        ((TextButton) customBtn).getLabel().setFontScale(game.buttonSizeSmall);
        customBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goCustomReels();
            }
        });


        myTexture = new Texture(Gdx.files.internal("handleUp.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        playBtn = new ImageButton(myTexRegionDrawable);
        //playBtn.getImage().setFillParent(true);
        //playBtn.setSize(WORLDWIDTH/15,WORLDHEIGHT/8);



        //playBtn.pad(50,0,110,-20);
        playBtn.pad(0,200,0,-50);
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //playBtn.invalidate();
                if(pref.getBoolean("music")){
                handleMusic.play();
                }
                //table2.removeActor(playBtn);
                screenTable.removeActor(playBtn);
                screenTable.removeActor(customBtn);
                screenTable.removeActor(backBtn);
                screenTable.clearChildren();
                //screenTable = new Table();
                //screenTable.setBackground(new TextureRegionDrawable(background));
                myTexture = new Texture(Gdx.files.internal("handleDown.png"));
                myTextureRegion = new TextureRegion(myTexture);
                myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
                playBtn = new ImageButton(myTexRegionDrawable);
                //playBtn.setScale(200);
                playBtn.pad(0,200,0,-50);
                //table2.add(playBtn);
                //table2.setFillParent(true);
                screenTable.defaults().pad(game.screenW/150);
                screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
                screenTable.add(playBtn).expand().center().right().size(Value.percentHeight(0.5f, screenTable));
                screenTable.add().pad(game.screenW/100).row();
                screenTable.add(customBtn).expand().center().bottom().size(Value.percentWidth(0.4f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
                screenTable.add().pad(game.screenW/100);
                //screenTable.setFillParent(true);
                startImages = false;
                play = true;
                i = 0;
            }
        });

        screenTable.defaults().pad(game.screenW/150);
        screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.10f, screenTable)).row();
        screenTable.add(playBtn).expand().center().right().size(Value.percentHeight(0.5f, screenTable));
        screenTable.add().pad(game.screenW/100).row();
        screenTable.add(customBtn).expand().center().bottom().size(Value.percentWidth(0.4f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        screenTable.add().pad(game.screenW/100);

        /*Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        //table.add(testBtn);
        table.top();
        table.left();
        //table.setDebug(true);

        table2 = new Table();
        table2.add(playBtn);
        table2.right().pad(15);
        table2.bottom().pad(100);

        Table table3 = new Table();
        table3.defaults().uniform().pad(30);
        table3.add(customBtn);
        table3.bottom().pad(20);

        table.setFillParent(true);
        table2.setFillParent(true);
        table3.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);
        stage.addActor(table3);*/

        screenTable.setFillParent(true);
        stage.addActor(screenTable);
        //screenTable.setDebug(true);

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
    private void spriteSheet(Texture reelRoll, int cols, int rows){

        TextureRegion[][] tmp = TextureRegion.split(reelRoll,
                reelRoll.getWidth() / cols,
                reelRoll.getHeight() / rows);

        frames = transformTo1D(tmp, cols, rows);

    }
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


    public int random(int numberOfImages){
        Random rand = new Random();
        int n = rand.nextInt(numberOfImages);
        return n;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(game.camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame1 = firstReelSheet.getKeyFrame(stateTime, true);
        currentFrame2 = secondReelSheet.getKeyFrame(stateTime, true);
        currentFrame3 = thirdReelSheet.getKeyFrame(stateTime, true);

        stage.act();
        stage.draw();

        float firstReelx = WORLDWIDTH/2- 1.5f*reelsRectangle.width - 0.2f;
        float secondReelx = WORLDWIDTH/2-reelsRectangle.width/2;
        float thirdReelx = WORLDWIDTH/2+reelsRectangle.width/2 + 0.2f;

        if(play) {
            if(pref.getBoolean("music")){
            playEffects(i);
            }
            if (i < firstReelTime) {
                batch.begin();
                batch.draw(currentFrame1, firstReelx, reelsRectangle.y, reelsRectangle.width,
                        reelsRectangle.height);
                sleep();
                batch.end();
            } else {
                batch.begin();
                batch.draw(firstReel.firstReelImages.get(drawnNumberFirstReel), firstReelx,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }
            if (i < secondReelTime) {
                batch.begin();
                batch.draw(currentFrame2, secondReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                sleep();
                batch.end();
            } else {
                batch.begin();
                batch.draw(secondReel.secondReelImages.get(drawnNumberSecondReel), secondReelx,
                        reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                batch.end();
            }

            if (i < thirdReelTime) {
                batch.begin();
                batch.draw(currentFrame3, thirdReelx, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
                sleep();
                batch.end();
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

            if(i >= thirdReelTime) {
                reelMusic.stop();
                try {
                    Thread.sleep(700);
                } catch (Exception e) {
                }
                if(pref.getBoolean("music")){
                    ready.play();
                }
                System.out.println("arvotut numerot: "+drawnNumberFirstReel + drawnNumberSecondReel + drawnNumberThirdReeL);
                game.goDrawnIngredients(drawnNumberFirstReel,drawnNumberSecondReel,
                        drawnNumberThirdReeL);
            }
        }
    }
    private void sleep(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
        }
    }

    private void playEffects(int i) {
        if(i == 1) {
            reelMusic.play();
        }
        else if(i == firstReelTime || i==secondReelTime || i==thirdReelTime) {
            //reelMusic.stop();
            reelStop.setLooping(false);
            reelStop.play();
        } else if(i == secondReelTime-1 || i==thirdReelTime-1) {
            reelStop.stop();
        }
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
