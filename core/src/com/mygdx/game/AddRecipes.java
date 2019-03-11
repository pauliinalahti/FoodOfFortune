package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

public class AddRecipes {

    public AddRecipes(){
    }

    public ArrayList<Recipe> AddAllRecipes(ArrayList<Recipe> recipes) {
        recipes.add(new Recipe("pasta carbonara",  new ArrayList<String>( Arrays.asList("spagetti","pekoni","sipuli")),"kyl sä osaat"));
        recipes.add(new Recipe("makaronilaatikko",  new ArrayList<String>( Arrays.asList("makaroni", "jauheliha", "sipuli")),"kyl sä osaat"));
        recipes.add(new Recipe("nakit ja muussi",  new ArrayList<String>( Arrays.asList("nakkeja", "peruna")),"kyl sä osaat"));
        recipes.add(new Recipe("kalakeitto",  new ArrayList<String>( Arrays.asList("peruna", "lohi", "maito")),"kyl sä osaat"));
        recipes.add(new Recipe("pasta bolognese",  new ArrayList<String>( Arrays.asList("spagetti", "tomaatti", "jauheliha")),"kyl sä osaat"));
        return recipes;
    }
}
