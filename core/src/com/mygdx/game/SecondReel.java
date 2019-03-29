package com.mygdx.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

public class SecondReel {

    /*
    Texture image1,image2,image3,image4;
    String name1, name2, name3, name4;
    String name1EN, name2EN, name3EN, name4EN;
    ArrayList<String> secondReelFoodNamesEN;*/

    ArrayList<String> secondReelFoodNames;
    ArrayList<Texture> secondReelImages;
    HashMap<String, String> map = new HashMap<String, String>();

    public SecondReel(Preferences pref) {
        initMap();
        secondReelFoodNames = new ArrayList<String>();
        secondReelImages = new ArrayList<Texture>();
        if(!pref.getBoolean("english")) {
            //ArrayList<String> ings = new ArrayList<String>(Arrays.asList("makaroni", "peruna","riisi","spagetti"));
            for(String s : map.values()) {
                if(pref.getBoolean(s)) {
                    secondReelFoodNames.add(s);
                    secondReelImages.add(new Texture(s+".png"));
                }
            }
        } else {
            //ArrayList<String> ings = new ArrayList<String>(Arrays.asList("macaroni", "potato","rice","spaghetti"));
            for(String s : map.keySet()) {
                if(pref.getBoolean(s)) {
                    secondReelFoodNames.add(s);
                    secondReelImages.add(new Texture(map.get(s)+".png"));
                }
            }
        }
    }

    private void initMap() {
        map.put("macaroni", "makaroni");
        map.put("potato", "peruna");
        map.put("rice", "riisi");
        map.put("spaghetti", "spagetti");
    }

    /*
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
    }*/
}

