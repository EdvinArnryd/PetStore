package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Chicken extends Animal{
    protected int healthPercent = 100;
    public boolean isAlive;
    public int maxAge;
    public Food Corn;
    private int initialPrice = 2000;

    public Chicken(String name,String gender,int currentAge){
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