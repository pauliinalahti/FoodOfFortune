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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import java.awt.Checkbox;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class SettingsScreen implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    GlyphLayout layoutSettings;
    String settingsText;
    String backText;
    String customText;
    String languageText;
    String onOffText;
    String musicText;
    String changeText;
    Preferences pref;
    Button backBtn;
    String chosenLanguage;
    //final ArrayList<String> optionsFI = new ArrayList<String>(Arrays.asList("jauheliha", "kana", "lohi","soija","tofu","sieni","makaroni","peruna","riisi","spagetti","tomaatti","sipuli","porkkana","parsakaali","paprika"));
    //final ArrayList<String> optionsEN = new ArrayList<String>(Arrays.asList("minced meat", "chicken", "salmon","soy","tofu","mushroom","macaroni","potato","rice","spaghetti","tomato","onion","carrot","broccoli","bell pepper"));
    //final ArrayList<String> options;
    //float volume;

    public SettingsScreen(MainGame g) {
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);
        pref = game.getPrefs();

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        if(pref.getBoolean("english")){
            backText = "BACK";
            customText = "Custom food ingredients";
            settingsText = "SETTINGS";
            languageText = "Language: ";
            onOffText = "ON";
            changeText = "Language: EN";
            chosenLanguage = "English";
            if(pref.getBoolean("music")) {
                musicText = "Music: ON";
            } else {
                musicText = "Music: OFF";
            }
            //options = optionsEN;
        } else {
            backText = "TAKAISIN";
            customText = "Ruoka-aine asetukset";
            settingsText = "ASETUKSET";
            languageText = "Kieli: ";
            onOffText = "OFF";
            changeText = "Kieli: FI";
            chosenLanguage = "Suomi";
            if(pref.getBoolean("music")) {
                musicText = "Äänet: ON";
            } else {
                musicText = "Äänet: OFF";
            }
            //options = optionsFI;
        }

        layoutSettings = new GlyphLayout();
        layoutSettings.setText(game.font2, settingsText);

        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goMainMenu();
            }
        });


        Button changeTextBtn = new TextButton(changeText, mySkin, "small");
        changeTextBtn.pad(15);
        ((TextButton) changeTextBtn).getLabel().setFontScale(game.buttonSizeSmall);
        changeTextBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPrefs().getBoolean("english")) {
                    game.getPrefs().putBoolean("english", false);
                    game.getPrefs().flush();
                } else {
                    game.getPrefs().putBoolean("english", true);
                    game.getPrefs().flush();
                }
                game.goSettingsScreen();
            }
        });

        Button customBtn = new TextButton(customText, mySkin, "small");
        customBtn.pad(20);
        ((TextButton) customBtn).getLabel().setFontScale(game.buttonSizeSmall);
        customBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goCustomReels();
            }
        });

        Button musicBtn = new TextButton(musicText, mySkin, "small");
        musicBtn.pad(20);
        ((TextButton) musicBtn).getLabel().setFontScale(game.buttonSizeSmall);
        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.getPrefs().getBoolean("music")) {
                    game.getPrefs().putBoolean("music", false);
                    game.getPrefs().flush();
                    game.backgroundMusic.pause();
                    System.out.println("music off");
                } else {
                    game.getPrefs().putBoolean("music", true);
                    game.getPrefs().flush();
                    game.backgroundMusic.play();
                    System.out.println("music on");
                }
                game.goSettingsScreen();
            }
        });


        Table table = new Table();
        table.defaults().uniform().pad(15);
        table.add(backBtn);
        //Label l = new Label("ASETUKSET", mySkin);
        //l.setFontScale(game.buttonSizeBig);
        //table.add(l).expandX();
        table.left();
        table.top();
        //table.left();
        //table.setDebug(true);

        Table table2 = new Table();
        table2.defaults().uniform().pad(15);
        table2.pad(-5);
        table2.add(changeTextBtn).row();
        table2.add(musicBtn).row();
        table2.add(customBtn);
        //Label lang = new Label(languageText  + chosenLanguage, mySkin);
        //lang.setFontScale(2f);
        //table2.add(lang).width(100);
        table2.setPosition(1f, (WORLDHEIGHT-3.1f)*100);
        table2.center();
        table2.bottom().pad(20);


        //table2.left().pad(10);
        //table2.top().pad(20);

        /*Table tableCheckBoxes = new Table();
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
                    game.goSettingsScreen();
                    System.out.println("--------");
                }
            });
            tableCheckBoxes.add(cb).width(200);
            i++;
            if (i == 3) {
                tableCheckBoxes.row();
                i=0;
            }

        }*/
        //tableCheckBoxes.setPosition(1f, (WORLDHEIGHT-6f)*100);
        //tableCheckBoxes.left().pad(30);

        table.setFillParent(true);
        table2.setFillParent(true);
        //tableCheckBoxes.setFillParent(true);
        stage.addActor(table);
        stage.addActor(table2);
        //stage.addActor(tableCheckBoxes);
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

        game.font2.draw(batch, settingsText, WORLDWIDTH*100/2-layoutSettings.width/2, (WORLDHEIGHT-0.5f)*100);

        /*game.font.draw(batch, chosenLanguage, 400, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);

        if(game.getPrefs().getBoolean("english")) {
            game.font.draw(batch, languageText, 230, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
            pref.flush();
        } else {
            game.font.draw(batch, languageText, 230, (WORLDHEIGHT-1f-0.5f)*100, 300, -1, true);
            pref.flush();
        }*/
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
