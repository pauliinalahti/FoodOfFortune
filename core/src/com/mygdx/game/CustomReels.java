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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class CustomReels implements Screen {

    MainGame game;
    private Stage stage;
    Texture background;
    SpriteBatch batch;
    Image back;
    String backText;
    String playText;
    Preferences pref;
    Button backBtn;
    Button playBtn;
    GlyphLayout customIngrediends;
    String ingrediends;
    private Skin mySkin;
    final ArrayList<String> optionsFI1 = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni"));
    final ArrayList<String> optionsFI2 = new ArrayList<String>(Arrays.asList("makaroni","peruna","riisi","spagetti"));
    final ArrayList<String> optionsFI3 = new ArrayList<String>(Arrays.asList("tomaatti","sipuli","porkkana","parsakaali","paprika"));
    final ArrayList<String> optionsEN1 = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom"));
    final ArrayList<String> optionsEN2 = new ArrayList<String>(Arrays.asList("macaroni","potato","rice","spaghetti"));
    final ArrayList<String> optionsEN3 = new ArrayList<String>(Arrays.asList("tomato","onion","carrot","broccoli","bell pepper"));
    final ArrayList<String> options1;
    final ArrayList<String> options2;
    final ArrayList<String> options3;
    String reelText1;
    String reelText2;
    String reelText3;


    public CustomReels(MainGame g) {

        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        pref = game.getPrefs();
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

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

        customIngrediends = new GlyphLayout();
        customIngrediends.setText(game.font2, ingrediends);

        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSettingsScreen();
            }
        });
        playBtn = new TextButton(playText, mySkin, "small");
        playBtn.pad(20);
        ((TextButton) playBtn).getLabel().setFontScale(game.buttonSize);
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(15);
        table.add(backBtn);
        table.top();
        table.left();

        Table table2 = new Table();
        table2.defaults().uniform().pad(15);
        table2.add(playBtn);
        table2.bottom();
        table2.right();


        Table colA = new Table();
        Table colB = new Table();
        Table colC = new Table();

        Table cont = new Table();
        cont.defaults().pad(10F);
        cont.setFillParent(true);

        float scale = 2.7f;
        float scale2 = 2.2f;
        float cbPad = 15f;


        Label lbA = new Label(reelText1,mySkin);
        lbA.setFontScale(scale);
        colA.add(lbA).pad(10f).row();
        for (final String opt : options1) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.pad(cbPad);
            cb.getLabel().setFontScale(scale2);
            cb.getLabel().setFontScale(game.buttonSizeSmall);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options1);
                }
            });
            colA.add(cb);
            colA.row();
        }
        Label lbB = new Label(reelText2,mySkin);
        lbB.setFontScale(scale);
        colB.add(lbB).pad(10f).row();
        for (final String opt : options2) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.pad(cbPad);
            cb.getLabel().setFontScale(scale2);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options2);
                }
            });
            colB.add(cb);
            colB.row();
        }
        Label lbC = new Label(reelText3,mySkin);
        lbC.setFontScale(scale);
        colC.add(lbC).pad(10f).row();
        for (final String opt : options3) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            cb.pad(cbPad);
            cb.getLabel().setFontScale(scale2);
            cb.getLabel().setFontScale(game.buttonSizeSmall);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    handleclick(cb, opt, options3);
                }
            });
            colC.add(cb);
            colC.row();
        }

        cont.setPosition(0,-200);
        cont.add(colA).align(Align.top).expand();
        cont.add(colB).align(Align.top).expand();
        cont.add(colC).align(Align.top).expand();

        stage.addActor(cont);


        table.setFillParent(true);
        table2.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);
    }

    public void handleclick(CheckBox cb, String opt, ArrayList<String> optionList) {
        boolean checked = cb.isChecked();
        boolean changed = false;
        int j = 0;
        for(String reelOpt : optionList) {
            if (pref.getBoolean(reelOpt)) {
                j++;
            }
        }
        if(j>1 || !pref.getBoolean(opt)) {
            Gdx.graphics.setContinuousRendering(true);
            if(pref.getBoolean(opt)){
                pref.putBoolean(opt, false);
            } else {
                pref.putBoolean(opt, true);
            }
            pref.flush();
            changed = true;

        }
        if(!changed) {
            cb.setChecked(checked);
        }
        game.goCustomReels();
        System.out.println("--------");
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
        game.font2.draw(batch,
                ingrediends,
                WORLDWIDTH * 100 / 2 - customIngrediends.width / 2,
                (WORLDHEIGHT - 0.5f) * 100);
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
