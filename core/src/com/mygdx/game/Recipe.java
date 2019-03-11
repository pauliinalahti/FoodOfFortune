package com.mygdx.game;
import java.util.ArrayList;

public class Recipe {
    ArrayList<String> ingredients;
    String name;
    String method;

    public Recipe(String name, ArrayList<String> ingredients, String method) {
        this.name = name;
        this.ingredients = ingredients;
        this.method = method;
    }
}