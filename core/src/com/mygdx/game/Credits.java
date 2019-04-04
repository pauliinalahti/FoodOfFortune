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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class Credits implements Screen {

    MainGame game;
    SpriteBatch batch;
    Texture background;
    Texture logo;

    private Skin mySkin;
    private Stage stage;
    Image back;
    GlyphLayout layoutCredits, layoutTommi, layoutKristian, layoutPauliina, layoutJoona;
    String creditsText;
    String backText;
    String tommi = "Tommi Mäkeläinen";
    String kristian = "Kristian Levola";
    String pauliina = "Pauliina Lahti";
    String joona = "Joona Neuvonen";

    Preferences pref;
    Button backBtn;

    public Credits(MainGame g){
        game = g;
        batch = game.getBatch();
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("FOF_Tausta5.7.png"));
        logo = new Texture(Gdx.files.internal("GGLogo11.png"));
        //back = new Image(background);
        //back.setScaling(Scaling.fit);
        //back.setFillParent(true);
        //stage.addActor(back);
        pref = game.getPrefs();

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        if(pref.getBoolean("english")){
            backText = "BACK";
            creditsText = "CREDITS";
        } else {
            backText = "TAKAISIN";
            creditsText = "TEKIJÄT";
        }

        layoutCredits = new GlyphLayout();
        layoutCredits.setText(game.font2, creditsText);

        /*layoutTommi = new GlyphLayout();
        layoutTommi.setText(game.font, tommi);

        layoutKristian = new GlyphLayout();
        layoutKristian.setText(game.font, kristian);

        layoutPauliina = new GlyphLayout();
        layoutPauliina.setText(game.font, pauliina);

        layoutJoona = new GlyphLayout();
        layoutJoona.setText(game.font, joona);*/

        backBtn = new TextButton(backText, mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSettingsScreen();
            }
        });

        Label tommiM = new Label(tommi, mySkin);
        Label kristianL = new Label(kristian, mySkin);
        Label pauliinaL = new Label(pauliina, mySkin);
        Label joonaN = new Label(joona, mySkin);

        tommiM.setFontScale(2);
        kristianL.setFontScale(2);
        pauliinaL.setFontScale(2);
        joonaN.setFontScale(2);

        Table table = new Table();
        table.setBackground(new TextureRegionDrawable(background));
        table.defaults().uniform().pad(15);
        table.add(backBtn).row();
        table.left();
        table.top();
        //table.setDebug(true);

        Table table2 = new Table();
        table2.defaults().uniform().pad(15);
        table2.addActor(new Image(logo));
        table2.add(tommiM).row();
        table2.add(kristianL).row();
        table2.add(pauliinaL).row();
        table2.add(joonaN);


        table.setFillParent(true);
        table2.setFillParent(true);
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

        game.font2.draw(batch, creditsText, WORLDWIDTH*100/2-layoutCredits.width/2, (WORLDHEIGHT-0.3f)*100);

        /*game.font.draw(batch, tommi, WORLDWIDTH*100/2-layoutTommi.width/2, (WORLDHEIGHT-1f-1.5f)*100, 300, -1, true);
        game.font.draw(batch, kristian, WORLDWIDTH*100/2-layoutKristian.width/2, (WORLDHEIGHT-1f-2f)*100, 300, -1, true);
        game.font.draw(batch, pauliina, WORLDWIDTH*100/2-layoutPauliina.width/2, (WORLDHEIGHT-1f-2.5f)*100, 300, -1, true);
        game.font.draw(batch, joona, WORLDWIDTH*100/2-layoutJoona.width/2, (WORLDHEIGHT-1f-3f)*100, 300, -1, true);*/


        batch.setProjectionMatrix((game.camera.combined));

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
