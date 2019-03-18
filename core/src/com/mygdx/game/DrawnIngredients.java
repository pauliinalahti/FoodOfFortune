package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

import static com.mygdx.game.MainGame.WORLDHEIGHT;
import static com.mygdx.game.MainGame.WORLDWIDTH;

public class DrawnIngredients implements Screen {

    MainGame game;
    Texture background;

    private Skin mySkin;
    private Stage stage;
    Image back;
    SpriteBatch batch;
    FirstReel firstReel = new FirstReel();
    SecondReel secondReel = new SecondReel();
    ThirdReel thirdReel = new ThirdReel();
    int firstDrawn, secondDrawn, thirdDrawn;
    Rectangle reelsRectangle;
    GlyphLayout layoutFirst, layoutSecond, layoutThird, drawnIngrediends;
    String ingrediends = "ARVOTUT AINEKSET";


    public DrawnIngredients(MainGame g, int first, int second, int third){
        game = g;
        batch = game.getBatch();
        firstDrawn = first;
        secondDrawn = second;
        thirdDrawn = third;
        reelsRectangle = new Rectangle(1.26f,1.7f,2.1f,2.25f);
        stage = new Stage(game.screenPort);
        background = new Texture(Gdx.files.internal("foodbackground2.jpg"));
        back = new Image(background);
        back.setScaling(Scaling.fit);
        back.setFillParent(true);
        stage.addActor(back);

        drawnIngrediends = new GlyphLayout();
        drawnIngrediends.setText(game.font2, ingrediends);

        layoutFirst = new GlyphLayout();
        layoutFirst.setText(game.font, firstReel.firstReelFoodNames.get(firstDrawn));

        layoutSecond = new GlyphLayout();
        layoutSecond.setText(game.font, secondReel.secondReelFoodNames.get(secondDrawn));

        layoutThird = new GlyphLayout();
        layoutThird.setText(game.font, thirdReel.thirdReelFoodNames.get(thirdDrawn));

        game.myAssetsManager.queueAddSkin();
        game.myAssetsManager.manager.finishLoading();
        mySkin = game.myAssetsManager.manager.get(GameConstants.skin);

        Button recipesBtn = new TextButton("RECIPES", mySkin, "small");
        recipesBtn.pad(20);
        ((TextButton) recipesBtn).getLabel().setFontScale(game.buttonSize);
        recipesBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goRecipes(firstDrawn,secondDrawn,thirdDrawn);
            }
        });

        Button backBtn = new TextButton("BACK", mySkin, "small");
        backBtn.pad(20);
        ((TextButton) backBtn).getLabel().setFontScale(game.buttonSize);
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goSlotMachine();
            }
        });

        Table table = new Table();
        table.defaults().uniform().pad(30);
        table.add(backBtn);
        table.add(recipesBtn);
        table.top();
        table.left();

        Table table2 = new Table();
        table2.defaults().uniform().pad(30);
        table2.add(recipesBtn);
        table2.bottom().pad(20);
        //table.setDebug(true);

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

        batch.draw(firstReel.firstReelImages.get(firstDrawn),
                reelsRectangle.x, reelsRectangle.y, reelsRectangle.width,
                reelsRectangle.height);
        batch.draw(secondReel.secondReelImages.get(secondDrawn),
                3.6f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);
        batch.draw(thirdReel.thirdReelImages.get(thirdDrawn),
                5.94f, reelsRectangle.y, reelsRectangle.width, reelsRectangle.height);

        batch.setProjectionMatrix(game.cameraFont.combined);
        game.font2.draw(batch, ingrediends, WORLDWIDTH*100/2-drawnIngrediends.width/2, (WORLDHEIGHT-0.5f)*100);
        game.font.draw(batch, firstReel.firstReelFoodNames.get(firstDrawn), (reelsRectangle.x*100)+(reelsRectangle.width/2*100)-layoutFirst.width/2, reelsRectangle.y*100- 20);
        game.font.draw(batch, secondReel.secondReelFoodNames.get(secondDrawn), (3.6f*100)+(reelsRectangle.width/2*100)-layoutSecond.width/2, reelsRectangle.y*100- 20);
        game.font.draw(batch, thirdReel.thirdReelFoodNames.get(thirdDrawn), (5.94f*100)+(reelsRectangle.width/2*100)-layoutThird.width/2, reelsRectangle.y*100- 20);
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
