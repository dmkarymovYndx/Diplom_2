package ru.praktikum.yandex.classes.order;

import java.util.ArrayList;

public class OrderDataClient {

    private ArrayList<String> ingredients;

    public OrderDataClient(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

}
