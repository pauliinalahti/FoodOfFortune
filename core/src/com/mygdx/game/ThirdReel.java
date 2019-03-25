package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ThirdReel {
    Texture image1,image2,image3,image4,image5;
    String name1, name2, name3, name4, name5;
    String name1EN, name2EN, name3EN, name4EN, name5EN;
    ArrayList<Texture> thirdReelImages;
    ArrayList<String> thirdReelFoodNames;
    ArrayList<String> thirdReelFoodNamesEN;

    public ThirdReel() {
        thirdReelImages = new ArrayList<Texture>();
        thirdReelFoodNames = new ArrayList<String>();
        thirdReelFoodNamesEN = new ArrayList<String>();

        image1 = new Texture(Gdx.files.internal("tomaatti.png"));
        image2 = new Texture(Gdx.files.internal("sipuli.png"));
        image3 = new Texture(Gdx.files.internal("porkkana.png"));
        image4 = new Texture(Gdx.files.internal("parsakaali.png"));
        image5 = new Texture(Gdx.files.internal("paprika.png"));

        thirdReelImages.add(image1);
        thirdReelImages.add(image2);
        thirdReelImages.add(image3);
        thirdReelImages.add(image4);
        thirdReelImages.add(image5);

        name1 = "Tomaatti";
        name2 = "Sipuli";
        name3 = "Porkkana";
        name4 = "Parsakaali";
        name5 = "Paprika";

        thirdReelFoodNames.add(name1);
        thirdReelFoodNames.add(name2);
        thirdReelFoodNames.add(name3);
        thirdReelFoodNames.add(name4);
        thirdReelFoodNames.add(name5);

        name1EN = "Tomato";
        name2EN = "Onion";
        name3EN = "Carrot";
        name4EN = "Broccoli";
        name5EN = "Pepper";

        thirdReelFoodNamesEN.add(name1EN);
        thirdReelFoodNamesEN.add(name2EN);
        thirdReelFoodNamesEN.add(name3EN);
        thirdReelFoodNamesEN.add(name4EN);
        thirdReelFoodNamesEN.add(name5EN);
    }
}
