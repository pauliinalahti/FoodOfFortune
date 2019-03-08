package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ThirdReel {
    Texture image1,image2,image3;
    ArrayList<Texture> thirdReelImages;

    public ThirdReel() {
        image1 = new Texture(Gdx.files.internal("banaani.png"));
        image2 = new Texture(Gdx.files.internal("apple.png"));
        image3 = new Texture(Gdx.files.internal("pear.png"));
        thirdReelImages = new ArrayList<Texture>();
        thirdReelImages.add(image1);
        thirdReelImages.add(image2);
        thirdReelImages.add(image3);
    }
}
