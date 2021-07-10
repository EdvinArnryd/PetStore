package com.company;
import java.util.*;

public class Store {

    static Scanner scanner = new Scanner(System.in);

    public static Animal buyAnimal(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What animal do you want to buy?");
        System.out.println("1:Dog 2:Cat 3:Horse 4:Chicken 5:Sheep");
        var choice = scanner.nextLine();
        System.out.println("What is your animals name:");
        var animalName = scanner.nextLine();
        System.out.println("What is your animals gender(M/F):");
        var animalGender = scanner.nextLine();
        if (animalGender.toUpperCase().equals("M")){
            animalGender = "MALE";
        }
        else if (animalGender.toUpperCase().equals("F")){
            animalGender = "FEMALE";
        }
        System.out.println("What is your animals current age:");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        var animalCurrentAge = scanner.nextInt();
        return switch (choice){
            case "1"    -> (new Dog(animalName,animalGender,animalCurrentAge));
            case "2"    -> (new Cat(animalName,animalGender,animalCurrentAge));
            case "3"    -> (new Horse(animalName,animalGender,animalCurrentAge));
            case "4"    -> (new Chicken(animalName,animalGender,animalCurrentAge));
            case "5"    -> (new Sheep(animalName,animalGender,animalCurrentAge));
            default     -> null;
        };

    }

}