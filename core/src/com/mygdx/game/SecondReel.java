package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SecondReel {

    Texture image1,image2,image3,image4;
    ArrayList<Texture> secondReelImages;

    public SecondReel() {
        image1 = new Texture(Gdx.files.internal("makaroni.png"));
        image2 = new Texture(Gdx.files.internal("peruna.png"));
        image3 = new Texture(Gdx.files.internal("riisi.png"));
        image4 = new Texture(Gdx.files.internal("spagetti.png"));
        secondReelImages = new ArrayList<Texture>();
        secondReelImages.add(image1);
        secondReelImages.add(image2);
        secondReelImages.add(image3);
        secondReelImages.add(image4);
    }
}

