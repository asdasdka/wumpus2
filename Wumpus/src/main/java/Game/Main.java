package Game;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static database.AdatbazisKezelo database;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Wumpus World Menu:");
            System.out.println("1. Start Game");
            System.out.println("2. Load from Database");
            System.out.println("3. Toplist");
            System.out.println("4. Quit");

            System.out.print("Enter your choice (1 to 4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    database.adatbazisbolBeolvas();
                    break;
                case 3:
                    database.toplistaMegjelenit();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void startGame() {
        System.out.println("Starting Wumpus World Game...");
        WumpusWorld wumpusWorld = new WumpusWorld(new Scanner(System.in), System.out);
    }
}