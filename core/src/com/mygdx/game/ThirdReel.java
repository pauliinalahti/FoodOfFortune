package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ThirdReel {
    Texture image1,image2,image3,image4,image5;
    String name1, name2, name3, name4, name5;
    ArrayList<Texture> thirdReelImages;
    ArrayList<String> thirdReelFoodNames;

    public ThirdReel() {
        thirdReelImages = new ArrayList<Texture>();
        thirdReelFoodNames = new ArrayList<String>();

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

        name1 ="Tomaatti";
        name2 ="Sipuli";
        name3 = "Porkkana";
        name4 = "Parsakaali";
        name5 = "Paprika";

        thirdReelFoodNames.add(name1);
        thirdReelFoodNames.add(name2);
        thirdReelFoodNames.add(name3);
        thirdReelFoodNames.add(name4);
        thirdReelFoodNames.add(name5);
    }
}
