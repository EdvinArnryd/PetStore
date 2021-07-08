package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Horse extends Animal{
    protected int healthPercent = 100;
    public boolean isAlive;
    public int maxAge;
    public Food Grass;
    private int initialPrice = 4000;

    public Horse(String name,String gender,int currentAge){
        super(name,gender,currentAge);
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public int calculateAmount(int quantity){
        return initialPrice * quantity;
    }

    public void eat(Food food){

    }

    public void die(){

    }

    public boolean isLiving(){
        return true;
    }

    public void breed(Animal animal){

    }

}