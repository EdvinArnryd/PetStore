package com.company;
import java.util.*;


public class Game {
    public Game() {
        startMenu();
    }

    private void startMenu() {
        print("Type '1' to start a new game");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        var checkRange = "1";
        do {
            var choice = checkRange.indexOf(input);
            if (choice < 0) {
                continue;
            }
            choice++;
            if (choice == 1) {
                print("How many players(1-4):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var countPlayers = scanner.nextInt();

                print("How many rounds do you want to play?(5-30):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var rounds = scanner.nextInt();

                mainMenu(countPlayers, rounds);
            }
        } while (true);
    }

    public void mainMenu(int countPlayers, int rounds) {
        var players = new ArrayList<Player>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= countPlayers; i++) {
            System.out.printf("What is your name player%d:", i);
            var playersName = scanner.nextLine();
            players.add(new Player(playersName.toUpperCase()));
        }
        var countRounds = 5;
        do {
            for (int i = 1; i <= countPlayers; i++) {
                System.out.printf("It is your turn Player%d :%s", i, players.get(i - 1).name);
                System.out.println("");
                print("Please enter your choice:");
                print("-------------------------");
                print("1:Buy animal");
                print("2:Buy food");
                print("3:Feed animal");
                print("4:Breed animal");
                print("5:Sell animal");
                print("-------------------------");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var functionNumber = scanner.nextInt();
                switch (functionNumber) {
                    case 1:
                        var buyFlag = true;
                        do {
                            Animal myNewAnimal = Store.buyAnimal();
                            var balance = players.get(i - 1).balance;
                            var className = myNewAnimal.getClass().getSimpleName();
                            int restBalance = -1;
                            switch (className) {
                                case "Dog" -> restBalance = ((Dog) myNewAnimal).checkBalance(balance);
                                case "Cat" -> restBalance = ((Cat) myNewAnimal).checkBalance(balance);
                                case "Horse" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                                case "Chicken" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                                case "Sheep" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                            }
                            if (restBalance >= 0) {
                                players.get(i - 1).addAnimal(myNewAnimal);
                                players.get(i - 1).updateBalance(restBalance);
                            } else print("Sorry!You don't have enough money to buy this animal");
                            System.out.println("Would you like to buy another animal(y/n)");
                            buyFlag = (scanner.next()).toUpperCase().equals("Y");
                        } while (buyFlag);
                        for (var player : players) {
                            System.out.println("Name: " + player.name + " Balance " + player.balance);
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds >= rounds);

    }

    private void print(String x) {
        if (!x.equals("")) {
            System.out.println(x);
        }
    }


    private void toLostHealth(Player player) {
        for (var animal : player.animals) {
            var className = animal.getClass().getSimpleName();
            switch (className) {
                case "Dog" -> ((Dog) animal).lostHealth();
                case "Cat" -> ((Cat) animal).lostHealth();
                case "Horse" -> ((Horse) animal).lostHealth();
                case "Chicken" -> ((Chicken) animal).lostHealth();
                case "Sheep" -> ((Sheep) animal).lostHealth();
            }
        }
    }


    private void toBuyAnimals(Player player) {
        Scanner scanner = new Scanner(System.in);
        var buyAnimal = true;
        do {
            Animal myNewAnimal = Store.buyAnimal();
            var balance = player.balance;
            var className = myNewAnimal.getClass().getSimpleName();
            int restBalance = -1;
            switch (className) {
                case "Dog" -> restBalance = ((Dog) myNewAnimal).checkBalance(balance);
                case "Cat" -> restBalance = ((Cat) myNewAnimal).checkBalance(balance);
                case "Horse" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                case "Chicken" -> restBalance = ((Chicken) myNewAnimal).checkBalance(balance);
                case "Sheep" -> restBalance = ((Sheep) myNewAnimal).checkBalance(balance);
            }
            if (restBalance >= 0) {
                player.addAnimal(myNewAnimal);
                player.updateBalance(restBalance);
            } else print("Sorry!You don't have enough money to buy this animal");
            System.out.println("Would you like to buy other animal(y/n)");
            buyAnimal = (scanner.next()).toUpperCase().equals("Y");
        } while (buyAnimal);
    }

}