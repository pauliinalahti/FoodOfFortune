package com.mygdx.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * FirstReel class creates first reel to our slot game
 * It includes all variables what second slot reel needs
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */

public class FirstReel {
    /** Second reel's ingredients names. */
    ArrayList<String> firstReelFoodNames = new ArrayList<String>();

    /** Second reel's ingredients images. */
    ArrayList<Texture> firstReelImages = new ArrayList<Texture>();

    /** Hashmap for english-finnish translations. */
    HashMap<String, String> map = new HashMap<String, String>();

    /** Hashmap for english-finnish translations. */
    HashMap<String, String> map2 = new HashMap<String, String>();


    /**
     * FirstReel's constructor
     *
     * @param pref remenber players language choises
     */
    public FirstReel(Preferences pref) {
        initMap();

        /** This if sentence checks languages and player's selections in setup
         and add food ingredienst to first reel. */
        if(!pref.getBoolean("english")) {
            for(String s : map.values()) {
                if(pref.getBoolean(s)) {
                    firstReelFoodNames.add(s);
                    firstReelImages.add(new Texture(s+".png"));
                }
            }
            /** This else sentence checks languages and player's selections in setup
             and add food ingredienst to first reel. */
        } else {
            for(String s : map.keySet()) {
                if(pref.getBoolean(s)) {
                    firstReelFoodNames.add(s);
                    firstReelImages.add(new Texture(map.get(s) + ".png"));
                }
            }
        }
    }

    /**
     * initMap method put values to first reel's hashmaps
     * Finnish and english versions
     */
    private void initMap() {
        map.put("minced meat", "jauheliha");
        map.put("chicken", "kana");
        map.put("salmon", "lohi");
        map.put("soy", "soija");
        map.put("tofu", "tofu");
        map.put("mushroom", "sieni");


        map2.put("jauheliha", "minced meat");
        map2.put( "kana", "chicken");
        map2.put( "lohi", "salmon");
        map2.put( "soija", "soy");
        map2.put("tofu", "tofu");
        map2.put( "sieni", "mushroom");
    }
}
