package com.mygdx.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ThirdReel class creates third reel to our slot game
 * It includes all variables what third slot reel needs
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */

public class ThirdReel {
    // Third reel's ingredients names.
    ArrayList<String> thirdReelFoodNames = new ArrayList<String>();

    // Third reel's ingredients images.
    ArrayList<Texture> thirdReelImages = new ArrayList<Texture>();

    // Hashmap for english-finnish translations.
    HashMap<String, String> map = new HashMap<String, String>();

    // Hashmap for finnish-english translations.
    HashMap<String, String> map2 = new HashMap<String, String>();

    /**
     * Thirdreel's constructor
     *
     * @param pref remenber players language choises
     */
    public ThirdReel(Preferences pref) {
        initMap();

        // This if sentence checks languages and player's selections in setup
         // and add food ingredienst to third reel.
        if(!pref.getBoolean("english")) {
            for(String s : map.values()) {
                if(pref.getBoolean(s)) {
                    thirdReelFoodNames.add(s);
                    thirdReelImages.add(new Texture(s+".png"));
                }
            }
            // This else sentence checks languages and player's selections in setup
             //and add food ingredienst to third reel.
        } else {
            for(String s : map.keySet()) {
                if(pref.getBoolean(s)) {
                    thirdReelFoodNames.add(s);
                    thirdReelImages.add(new Texture(map.get(s)+".png"));
                }
            }
        }
    }

    /**
     * initMap method put values to third reel's hashmaps
     * Finnish and english versions
     */
    private void initMap() {
        map.put("tomato", "tomaatti");
        map.put("onion", "sipuli");
        map.put("carrot", "porkkana");
        map.put("broccoli", "parsakaali");
        map.put("bell pepper", "paprika");


        map2.put( "tomaatti", "tomato");
        map2.put( "sipuli", "onion");
        map2.put( "porkkana", "carrot");
        map2.put( "parsakaali", "broccoli");
        map2.put( "paprika", "bell pepper");
    }
}
