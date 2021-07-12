package com.company;

public abstract class Animal {
    private String name;
    private Gender gender;
    protected int initialPrice = 0;
    public String food;
    public double healthGrowthRef;
    public double totalFeedQuantity;
    public int lostHealth;
    public int addedHealth;
    protected int healthPercent = 100;
    private boolean isAlive;
    private int maxAge;

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

    public abstract void breed(Animal animal);

    public String getName(){
        return name;
    }
}
