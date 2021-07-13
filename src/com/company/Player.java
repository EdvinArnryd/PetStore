package com.company;
import java.util.*;

public class Player {
    public String name;
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<Food> foods = new ArrayList<>();
    public int balance  = 10000;

    public Player(String name){
        this.name = name;
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void updateBalance(int balance){
        this.balance = balance;
    }

    public void addFood(Food food){
        foods.add(food);
    }

    public void removeFood(Food food){
        foods.remove(food);
    }

}
