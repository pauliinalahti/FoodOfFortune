package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class FirstReel {
    Texture image1,image2,image3,image4,image5,image6;
    String name1, name2, name3, name4, name5, name6;
    ArrayList<Texture> firstReelImages;
    ArrayList<String> firstReelFoodNames;


    public FirstReel() {
        firstReelImages = new ArrayList<Texture>();
        firstReelFoodNames = new ArrayList<String>();

        image1 = new Texture(Gdx.files.internal("jauheliha.png"));
        image2 = new Texture(Gdx.files.internal("kana.png"));
        image3 = new Texture(Gdx.files.internal("lohi.png"));
        image4 = new Texture(Gdx.files.internal("soija.png"));
        image5 = new Texture(Gdx.files.internal("tofu.png"));
        image6 = new Texture(Gdx.files.internal("sieni.png"));

        firstReelImages.add(image1);
        firstReelImages.add(image2);
        firstReelImages.add(image3);
        firstReelImages.add(image4);
        firstReelImages.add(image5);
        firstReelImages.add(image6);

        name1 ="Jauheliha";
        name2 ="Kana";
        name3 = "Lohi";
        name4 = "Soija";
        name5 = "Tofu";
        name6 = "Sieni";

        firstReelFoodNames.add(name1);




    }
}
