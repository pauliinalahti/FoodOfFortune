package com.mygdx.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * SecondReel class creates second reel to our slot game
 * It includes all variables what second slot reel needs
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */

public class SecondReel {
    /** Second reel's ingredients names. */
    ArrayList<String> secondReelFoodNames = new ArrayList<String>();

    /** Second reel's ingredients images. */
    ArrayList<Texture> secondReelImages = new ArrayList<Texture>();

    /** Hashmap for english-finnish translations. */
    HashMap<String, String> map = new HashMap<String, String>();

    /** Hashmap for finnish-english translations. */
    HashMap<String, String> map2 = new HashMap<String, String>();


    /**
     * SecondReel's constructor
     *
     * @param pref remenber players language choises
     */
    public SecondReel(Preferences pref) {
        initMap();

        /** This if sentence checks languages and player's selections in setup
         and add food ingredienst to second reel. */
        if(!pref.getBoolean("english")) {
            for(String s : map.values()) {
                if(pref.getBoolean(s)) {
                    secondReelFoodNames.add(s);
                    secondReelImages.add(new Texture(s+".png"));
                }
            }

            /** This else sentence checks languages and player's selections in setup
             and add food ingredienst to second reel. */
        } else {
            for(String s : map.keySet()) {
                if(pref.getBoolean(s)) {
                    secondReelFoodNames.add(s);
                    secondReelImages.add(new Texture(map.get(s)+".png"));
                }
            }
        }
    }

    /**
     * initMap method put values to second reel's hashmaps
     * Finnish and english versions
     */
    private void initMap() {
        map.put("macaroni", "makaroni");
        map.put("potato", "peruna");
        map.put("rice", "riisi");
        map.put("spaghetti", "spagetti");


        map2.put( "makaroni", "macaroni");
        map2.put( "peruna", "potato");
        map2.put( "riisi", "rice");
        map2.put( "spagetti", "spaghetti");
    }

}

