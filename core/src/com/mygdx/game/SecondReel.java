package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SecondReel {

    Texture image1,image2,image3,image4;
    String name1, name2, name3, name4;
    ArrayList<Texture> secondReelImages;
    ArrayList<String> secondReelFoodNames;

    public SecondReel() {
        secondReelImages = new ArrayList<Texture>();
        secondReelFoodNames = new ArrayList<String>();

        image1 = new Texture(Gdx.files.internal("makaroni.png"));
        image2 = new Texture(Gdx.files.internal("peruna.png"));
        image3 = new Texture(Gdx.files.internal("riisi.png"));
        image4 = new Texture(Gdx.files.internal("spagetti.png"));

        secondReelImages.add(image1);
        secondReelImages.add(image2);
        secondReelImages.add(image3);
        secondReelImages.add(image4);

        name1 ="Makaroni";
        name2 ="Peruna";
        name3 = "Riisi";
        name4 = "Spagetti";

        secondReelFoodNames.add(name1);
        secondReelFoodNames.add(name2);
        secondReelFoodNames.add(name3);
        secondReelFoodNames.add(name4);
    }
}

