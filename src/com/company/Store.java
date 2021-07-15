package com.company;
import java.util.*;

public class Store {

    static Scanner scanner = new Scanner(System.in);

    public static Animal buyAnimal(Player player){
        var animalType = Dialogs.askAnimalType("buy");
        System.out.println("What is your animals name:");
        var animalName = scanner.nextLine();
        var nameExist = InputCheck.isAnimalNameExist(player,animalType,animalName);
        while (nameExist){
            System.out.println("What is your animals name:");
            animalName = scanner.nextLine();
            nameExist = InputCheck.isAnimalNameExist(player,animalType,animalName);
        }

        System.out.println("What is your animals gender(M/F):");
        var animalGender = scanner.nextLine();
        if (animalGender.toUpperCase().equals("M")){
            animalGender = "MALE";
        }
        else if (animalGender.toUpperCase().equals("F")){
            animalGender = "FEMALE";
        }
        return switch (animalType){
            case "Dog"      -> (new Dog(animalName,animalGender));
            case "Horse"    -> (new Horse(animalName,animalGender));
            case "Cat"      -> (new Cat(animalName,animalGender));
            case "Chicken"  -> (new Chicken(animalName,animalGender));
            case "Sheep"   -> (new Sheep(animalName,animalGender));
            default     -> null;
        };
    }

    public static Food buyFood(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What food do you want to buy?");
        System.out.println("1:Meat 2:Grass 3:Corn");
        var choice = scanner.nextLine();
        System.out.println("Enter quantity");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        var buyQuantity = scanner.nextInt();
        return switch (choice){
            case "1"    -> (new Meat("Meat",buyQuantity));
            case "2"    -> (new Grass("Grass",buyQuantity));
            case "3"    -> (new Corn("Corn",buyQuantity));
            default     -> null;
        };
    }

}