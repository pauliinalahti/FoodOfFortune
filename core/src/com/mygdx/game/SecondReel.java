package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class SecondReel {

    Texture image1,image2,image3,image4;
    String name1, name2, name3, name4;
    String name1EN, name2EN, name3EN, name4EN;
    ArrayList<Texture> secondReelImages;
    ArrayList<String> secondReelFoodNames;
    ArrayList<String> secondReelFoodNamesEN;

    public SecondReel() {
        secondReelImages = new ArrayList<Texture>();
        secondReelFoodNames = new ArrayList<String>();
        secondReelFoodNamesEN = new ArrayList<String>();

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

        name1EN ="Macaroni";
        name2EN ="Potato";
        name3EN = "Rice";
        name4EN = "Spaghetti";

        secondReelFoodNamesEN.add(name1EN);
        secondReelFoodNamesEN.add(name2EN);
        secondReelFoodNamesEN.add(name3EN);
        secondReelFoodNamesEN.add(name4EN);
    }
}

