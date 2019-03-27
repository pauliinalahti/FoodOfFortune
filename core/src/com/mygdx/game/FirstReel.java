package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstReel {
    Texture image1,image2,image3,image4,image5,image6;
    String name1, name2, name3, name4, name5, name6;
    String name1EN, name2EN, name3EN, name4EN, name5EN, name6EN;
    ArrayList<Texture> firstReelImages;
    ArrayList<String> firstReelFoodNames;
    ArrayList<String> firstReelFoodNamesEN;
    Preferences pref;


    public FirstReel() {
        firstReelImages = new ArrayList<Texture>();
        firstReelFoodNames = new ArrayList<String>();
        firstReelFoodNamesEN = new ArrayList<String>();

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

        name1 = "jauheliha";
        name2 = "kana";
        name3 = "lohi";
        name4 = "soija";
        name5 = "tofu";
        name6 = "sieni";

        firstReelFoodNames.add(name1);
        firstReelFoodNames.add(name2);
        firstReelFoodNames.add(name3);
        firstReelFoodNames.add(name4);
        firstReelFoodNames.add(name5);
        firstReelFoodNames.add(name6);

        name1EN = "Minced meat";
        name2EN ="Chicken";
        name3EN = "Salmon";
        name4EN = "Soy";
        name5EN = "Tofu";
        name6EN = "Mushroom";

        firstReelFoodNamesEN.add(name1EN);
        firstReelFoodNamesEN.add(name2EN);
        firstReelFoodNamesEN.add(name3EN);
        firstReelFoodNamesEN.add(name4EN);
        firstReelFoodNamesEN.add(name5EN);
        firstReelFoodNamesEN.add(name6EN);

    }

    public FirstReel(Preferences pref) {
        firstReelFoodNames = new ArrayList<String>();
        firstReelImages = new ArrayList<Texture>();
        ArrayList<String> ings = new ArrayList<String>(Arrays.asList("jauheliha", "kana","lohi","soija","tofu","sieni"));
        for(String s : ings) {
            if(pref.getBoolean(s)) {
                firstReelFoodNames.add(s);
                firstReelImages.add(new Texture(s+".png"));
            }
        }
        //System.out.println("new fr made: " + firstReelFoodNames.toString());
    }


    /*public int getIndexOf(String s) {
        for(int i = 0; i<firstReelImages.size(); i++) {
            if (firstReelImages.get(i).toString().equals(s+".png")) {
                return i;
            }
        }
        return 0;
    }*/
}
