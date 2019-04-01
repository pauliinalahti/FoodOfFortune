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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
    final ArrayList<String> optionsFI = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni","makaroni","peruna","riisi","spagetti","tomaatti","sipuli","porkkana","parsakaali","paprika"));
    final ArrayList<String> optionsEN = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom","macaroni","potato","rice","spaghetti","tomato","onion","carrot","broccoli","bell pepper"));
    final ArrayList<String> options;


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
            options = optionsEN;
            backText = "BACK";
            playText = "PLAY";
            ingrediends = "INGREDIENTS";
        } else {
            options = optionsFI;
            backText = "TAKAISIN";
            playText = "PELAA";
            ingrediends = "AINEKSET";
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

        Table tableCheckBoxes = new Table();
        tableCheckBoxes.defaults().uniform().pad(30);
        int i = 0;
        for (final String opt : options) {
            final CheckBox cb = new CheckBox(opt, mySkin);
            //cb.pad(-5);
            //System.out.println(opt + ": " + pref.getBoolean(opt));
            cb.getLabel().setFontScale(game.buttonSizeSmall);
            cb.setChecked(pref.getBoolean(opt));
            cb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.graphics.setContinuousRendering(true);
                    if(pref.getBoolean(opt)){
                        pref.putBoolean(opt, false);
                    } else {
                        pref.putBoolean(opt, true);
                    }
                    pref.flush();
                    game.goCustomReels();
                    System.out.println("--------");
                }
            });
            tableCheckBoxes.add(cb).width(200);
            i++;
            if (i == 3) {
                tableCheckBoxes.row();
                i=0;
            }
        }

        tableCheckBoxes.setPosition(1f, (WORLDHEIGHT-6f)*100);

        tableCheckBoxes.setFillParent(true);
        table.setFillParent(true);
        table2.setFillParent(true);
        stage.addActor(tableCheckBoxes);
        stage.addActor(table);
        stage.addActor(table2);
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
