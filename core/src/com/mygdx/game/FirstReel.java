package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class FirstReel {
    Texture image1,image2,image3;
    ArrayList<Texture> firstReelImages;

    public FirstReel() {
        image1 = new Texture(Gdx.files.internal("banaani.png"));
        image2 = new Texture(Gdx.files.internal("apple.png"));
        image3 = new Texture(Gdx.files.internal("pear.png"));
        firstReelImages = new ArrayList<Texture>();
        firstReelImages.add(image1);
        firstReelImages.add(image2);
        firstReelImages.add(image3);
    }
}
