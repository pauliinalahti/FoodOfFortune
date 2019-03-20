package com.mygdx.game;
import java.util.ArrayList;

public class Recipe {
    ArrayList<String> ingredients;
    String name;
    String method;
    String amount;

    public Recipe(String name, ArrayList<String> ingredients, String method) {
        this.name = name;
        this.ingredients = ingredients;
        this.amount = "joopajoo";
        this.method = method;
    }
    public void  addAmount(String am) {
        this.amount = am;
    }
}

