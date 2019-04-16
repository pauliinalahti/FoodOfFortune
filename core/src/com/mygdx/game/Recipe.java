package com.mygdx.game;
import java.util.ArrayList;

/**
 * Recipe class handles recipes
 *
 * @author      Pauliina Lahti, Joona Neuvonen
 * @version     2019.4
 */
public class Recipe {

    /** Food ingredients list */
    ArrayList<String> ingredients;

    /** Food recipe's name*/
    String name;

    /** Food recipe's text */
    String method;

    /** Amount tells what ingredients one recipe needs*/
    String amount;

    /**
     * Recipe's constructor
     *
     * @param name gives name to recipe
     * @param ingredients tell what ingredients recipe needs
     * @param method tells how to make that food
     */
    public Recipe(String name, ArrayList<String> ingredients, String method) {
        this.name = name;
        this.ingredients = ingredients;
        this.amount = "amount";
        this.method = method;
    }

    /**
     * Recipe's constructor
     *
     * @param am add food ingredients to amount string
     */
    public void  addAmount(String am) {
        this.amount = am;
    }
}

