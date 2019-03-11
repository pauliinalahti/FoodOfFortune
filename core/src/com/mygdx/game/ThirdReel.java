package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ThirdReel {
    Texture image1,image2,image3,image4,image5,image6,image7;
    ArrayList<Texture> thirdReelImages;

    public ThirdReel() {
        image1 = new Texture(Gdx.files.internal("tomaatti.png"));
        image2 = new Texture(Gdx.files.internal("sipuli.png"));
        image3 = new Texture(Gdx.files.internal("porkkana.png"));
        image4 = new Texture(Gdx.files.internal("parsakaali.png"));
        image5 = new Texture(Gdx.files.internal("paprika.png"));
        thirdReelImages = new ArrayList<Texture>();
        thirdReelImages.add(image1);
        thirdReelImages.add(image2);
        thirdReelImages.add(image3);
        thirdReelImages.add(image4);
        thirdReelImages.add(image5);
    }
}
