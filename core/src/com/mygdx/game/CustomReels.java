package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

/**
 * CustomReels class includes all necessaries to handle screen
 * where player can custom reels
 * CustomReels implements Screen
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class CustomReels implements Screen {

    /** Create MainGame object game to handle maingame variables*/
    MainGame game;

    /** Create new Stage to handle stages*/
    private Stage stage;

    /** Background for this screen */
    Texture background;

    /** Create new SpriteBatch*/
    SpriteBatch batch;

    /** New image to back screen*/
    Image back;

    /** String names for buttons */
    String backText, playText;

    /** Preference to handle players language choices*/
    Preferences pref;

    /** Create two buttons in to the screen */
    Button backBtn, playBtn;

    /** Create GlyphLAyout to handle font size*/
    GlyphLayout customIngrediends;

    /** String for ingredients handling*/
    String ingrediends;

    /** Create Skin to handle screen's skin*/
    private Skin mySkin;

    /** Create ArrayList to handle finnish ingredients in first reel */
    final ArrayList<String> optionsFI1 = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni"));

    /**Create ArrayList to handle finnish ingredients in second reel */
    final ArrayList<String> optionsFI2 = new ArrayList<String>(Arrays.asList("makaroni","peruna","riisi","spagetti"));

    /** Create ArrayList to handle finnish ingredients in third reel*/
    final ArrayList<String> optionsFI3 = new ArrayList<String>(Arrays.asList("tomaatti","sipuli","porkkana","parsakaali","paprika"));

    /**Create ArrayList to handle english ingredients in first reel */
    final ArrayList<String> optionsEN1 = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom"));

    /** Create ArrayList to handle english ingredients in second reel */
    final ArrayList<String> optionsEN2 = new ArrayList<String>(Arrays.asList("macaroni","potato","rice","spaghetti"));

    /** Create ArrayList to handle english ingredient in third reel */
    final ArrayList<String> optionsEN3 = new ArrayList<String>(Arrays.asList("tomato","onion","carrot","broccoli","bell pepper"));

    /** Create Arraylist to handle options */
    final ArrayList<String> options1, options2, options3;

    /** Create texts to the reels*/
    String reelText1, reelText2, reelText3;

    /** Create FirstReel object */
    FirstReel reel1;

    /** Create SecondReel object */
    SecondReel reel2;

    /** Create ThirdReel object */
    ThirdReel reel3;

    /** Create a new Table */
    Table screenTable;

    /**
     * CustomReels constructor
     *
     * @param g is MainGame object
     */
    public CustomReels(MainGame g) {

        /** Initializing the variables */
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        pref = game.getPrefs();

        /** Set image to background texture */
        reel1 = new FirstReel(pref);
        reel2 = new SecondReel(pref);
        reel3 = new ThirdReel(pref);


        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        /** If preference language key is english, then game use english ArrayList and
         * english language */
        if(pref.getBoolean("english")){
            options1 = optionsEN1;
            options2 = optionsEN2;
            options3 = optionsEN3;
            backText = "BACK";
            playText = "PLAY";
            ingrediends = "INGREDIENTS";
            reelText1 = "1st Reel";
            reelText2 = "2nd Reel";
            reelText3 = "3rd Reel";

        /** Else used language is finnish */
        } else {
            options1 = optionsFI1;
            options2 = optionsFI2;
            options3 = optionsFI3;
            backText = "TAKAISIN";
            playText = "PELAA";
            ingrediends = "AINEKSET";
            reelText1 = "1. Rulla";
            reelText2 = "2. Rulla";
            reelText3 = "3. Rulla";
        }

        /** Create table for buttons and adds background*/
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        screenTable = new Table();
        screenTable.setFillParent(true);
        screenTable.setBackground(new TextureRegionDrawable(background));

        customIngrediends = new GlyphLayout();
        customIngrediends.setText(game.font2, ingrediends);

        // Creates back button
        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.setTransform(true);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            /**
             * changed Method change screen when player press buttons
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press setting button, it goes to settings screen */
                game.goSettingsScreen();
            }
        });
        // Creates play button
        playBtn = new TextButton(playText, mySkin, "small");
        backBtn.setTransform(true);
        ((TextButton) playBtn).getLabel().setFontScale(game.buttonSize);
        playBtn.addListener(new ChangeListener() {
            /**
             * changed Method change screen when player press buttons
             *
             * @param event enable that actor can do defined moves
             * @param actor do the defined moves
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** If player press slotmachine button, it goes to slotmachine screen */
                game.goSlotMachine();
            }
        });

        /** Create three tables to handle screen's showing parts*/
        Table colA = new Table();
        Table colB = new Table();
        Table colC = new Table();

        /** Set scaling to 2.2f */
        float scale2 = 2.2f;

        /** Scaling variable*/
        float cbPad = 15f;

        /** Check first reel's ingredients choices*/
        for (final String opt : options1) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.pad(cbPad);
            cb.getLabel().setFontScale(scale2);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {

                /**
                 * changed Method change first reels ingredients choices
                 *
                 * @param event enable that actor can do defined moves
                 * @param actor do the defined moves
                 */
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options1, 1);
                }
            });
            colA.add(cb).size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        }

        /** Check second reel's ingredients choices */
        for (final String opt : options2) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.getLabel().setFontScale(scale2);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                /**
                 * changed Method change second reels ingredients choices
                 *
                 * @param event enable that actor can do defined moves
                 * @param actor do the defined moves
                 */
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options2, 2);
                }
            });
            colB.add(cb).size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        }

        /** Check third reel's ingredients choices*/
        for (final String opt : options3) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.getLabel().setFontScale(scale2);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {

                /**
                 * changed Method change third reels ingredients choices
                 *
                 * @param event enable that actor can do defined moves
                 * @param actor do the defined moves
                 */
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options3, 3);
                }
            });
            colC.add(cb).size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.1f, screenTable)).row();
        }

        // Adding buttons and ingredient tables to screen table
        screenTable.add(backBtn).expand().top().left().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.13f, screenTable)).row();
        screenTable.add(colA).align(Align.top).expand().size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.40f, screenTable));
        screenTable.add(colB).align(Align.top).expand().size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.40f, screenTable));
        screenTable.add(colC).align(Align.top).expand().size(Value.percentWidth(0.3f, screenTable), Value.percentHeight(0.40f, screenTable)).row();
        screenTable.add();
        screenTable.add(playBtn).expand().center().size(Value.percentWidth(0.2f, screenTable), Value.percentHeight(0.15f, screenTable));

        // Adding table to stage
        stage.addActor(screenTable);
    }

    /**
     * handleclick Method checks what ingredients player have chosen
     *
     * @param cb is checkbox that checks what ingredients are on/off
     * @param opt is ingredients options
     * @param optionList list of ingredient choices
     * @param reelNumber tells what reel is talking about
     */
    public void handleclick(CheckBox cb, String opt, ArrayList<String> optionList, int reelNumber) {
        String translation;
        boolean checked = cb.isChecked();
        boolean changed = false;

        /** Create int variable to check that player chose atleast one ingredient */
        int j = 0;
        for(String reelOpt : optionList) {
            if (pref.getBoolean(reelOpt)) {
                j++;
            }
        }
        /** This if else sentence checks what ingredients player have chosen */
        if(j>1 || !pref.getBoolean(opt)) {
            Gdx.graphics.setContinuousRendering(true);
            if (pref.getBoolean("english")) {
                if(pref.getBoolean(opt)){
                    pref.putBoolean(opt, false);
                    if (reelNumber == 1) {
                        translation = reel1.map.get(opt);
                    } else if(reelNumber == 2) {
                        translation = reel2.map.get(opt);
                    } else {
                        translation = reel3.map.get(opt);
                    }
                    pref.putBoolean(translation, false);
                } else {
                    pref.putBoolean(opt, true);
                    if (reelNumber == 1) {
                        translation = reel1.map.get(opt);
                    } else if(reelNumber == 2) {
                        translation = reel2.map.get(opt);
                    } else {
                        translation = reel3.map.get(opt);
                    }
                    pref.putBoolean(translation, true);
                }
            } else {
                if(pref.getBoolean(opt)){
                    pref.putBoolean(opt, false);
                    if (reelNumber == 1) {
                        translation = reel1.map2.get(opt);
                    } else if(reelNumber == 2) {
                        translation = reel2.map2.get(opt);
                    } else {
                        translation = reel3.map2.get(opt);
                    }
                    pref.putBoolean(translation, false);
                } else {
                    pref.putBoolean(opt, true);
                    if (reelNumber == 1) {
                        translation = reel1.map2.get(opt);
                    } else if(reelNumber == 2) {
                        translation = reel2.map2.get(opt);
                    } else {
                        translation = reel3.map2.get(opt);
                    }
                    pref.putBoolean(translation, true);
                }
            }
            pref.flush();
            changed = true;
        }
        if(!changed) {
            cb.setChecked(checked);
        }
        /** Go back to customreels screen */
        game.goCustomReels();
    }

    /**
     * show method shows the stage
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

        /** Set camera combined */
        batch.setProjectionMatrix(game.cameraFont.combined);

        /** Shows food recipe's name in uppercase*/
        game.font2.draw(batch,
                ingrediends,
                WORLDWIDTH * 100 / 2 - customIngrediends.width / 2,
                (WORLDHEIGHT - 0.3f) * 100);
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
    public void hide() {Gdx.input.setInputProcessor(null);}

    /**
     * Dispose method dispose background image, game object, stage, spriteBatch
     * when player close the game
     */
    @Override
    public void dispose() {
        background.dispose();
        //stage.dispose();
        //game.dispose();
        //stage.dispose();
    }
}
