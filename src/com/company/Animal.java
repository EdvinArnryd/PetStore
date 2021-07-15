package com.company;

public abstract class Animal {
    private String name;
    private Gender gender;
    protected int initialPrice = 0;
    public String[] edibleFood;
    public double healthGrowthRef;
    public double totalFeedQuantity;
    public int lostHealth;
    public int addedHealth;
    protected int healthPercent = 100;
    private int maxAge;
    public int breedQuantity;
    public boolean isAlive;

    public Animal(String name, String gender) {
        this.name = name;
        this.gender = Gender.valueOf(gender.toUpperCase());
    }

    public void printField(){
        System.out.print("Name: " + name +" Gender: " + gender + " ");
    }

    public abstract void eat(Food food,double quantity);

    public abstract void lostHealth();

    public abstract void die();

    public abstract boolean isLiving();

    public static Animal breed(String gender) {
        return null;
    }

    public String getGender(){
        return String.valueOf(gender);
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public String getName(){
        return name;
    }
}
