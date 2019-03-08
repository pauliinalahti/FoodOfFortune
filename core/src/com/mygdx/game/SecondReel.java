package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SecondReel {

    Texture image1,image2,image3;
    ArrayList<Texture> secondReelImages;

    public SecondReel() {
        image1 = new Texture(Gdx.files.internal("banaani.png"));
        image2 = new Texture(Gdx.files.internal("apple.png"));
        //image3 = new Texture(Gdx.files.internal("pear.png"));
        secondReelImages = new ArrayList<Texture>();
        secondReelImages.add(image1);
        secondReelImages.add(image2);
       // secondReelImages.add(image3);
    }
}

