package org.example;

import java.util.Scanner;

public class WumpusWorld {

    private static int size;
    private static char[][] world;
    private static int heroX, heroY;
    private static int spawnX, spawnY;
    private static int goldX, goldY;
    private static boolean hasGold = false;
    private static String name;

    public WumpusWorld() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        name = scanner.nextLine();

        System.out.println("Please set the map's size (N x N, excluding walls): ");
        size = scanner.nextInt();

        initializeWorld();
        printWorld();

        while (true) {
            System.out.println("Enter your move (W/A/S/D to move, Q to quit): ");
            char move = scanner.next().charAt(0);

            if (move == 'Q' || move == 'q') {
                System.out.println("Game over. Thanks for playing!");
                break;
            }

            performMove(move);
            printWorld();
        }
    }

    private static void initializeWorld() {
        // A pálya mérete a falak közötti területtel
        int worldSize = size + 2;
        world = new char[worldSize][worldSize];

        // Initialize the world with walls and empty cells
        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                if (i == 0 || i == worldSize - 1 || j == 0 || j == worldSize - 1) {
                    world[i][j] = 'W'; // Walls
                } else {
                    world[i][j] = ' ';
                }
            }
        }

        // Place the hero in an empty random position
        do {
            heroX = (int) (Math.random() * (size)) + 1;
            heroY = (int) (Math.random() * (size)) + 1;
        } while (world[heroX][heroY] == 'W');

        world[heroX][heroY] = 'H';
        spawnX = heroX;
        spawnY = heroY;

        // Place the Wumpus in an empty random position
        int wumpusX, wumpusY;
        do {
            wumpusX = (int) (Math.random() * (size)) + 1;
            wumpusY = (int) (Math.random() * (size)) + 1;
        } while (world[wumpusX][wumpusY] == 'W' || (wumpusX == heroX && wumpusY == heroY));

        world[wumpusX][wumpusY] = 'W';

        // Place the gold in an empty random position
        int goldX, goldY;
        do {
            goldX = (int) (Math.random() * (size)) + 1;
            goldY = (int) (Math.random() * (size)) + 1;
        } while (world[goldX][goldY] == 'W' || (goldX == heroX && goldY == heroY));

        world[goldX][goldY] = 'G';
    }

    private static void printWorld() {
        // Print the current state of the world
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void performMove(char move) {
        // Move the hero based on user input
        world[heroX][heroY] = ' '; // Clear the current position

        switch (move) {
            case 'W':
            case 'w':
                if (heroX > 1 && world[heroX - 1][heroY] != 'W') {
                    heroX--;
                }
                break;
            case 'A':
            case 'a':
                if (heroY > 1 && world[heroX][heroY - 1] != 'W') {
                    heroY--;
                }
                break;
            case 'S':
            case 's':
                if (heroX < world.length - 2 && world[heroX + 1][heroY] != 'W') {
                    heroX++;
                }
                break;
            case 'D':
            case 'd':
                if (heroY < world.length - 2 && world[heroX][heroY + 1] != 'W') {
                    heroY++;
                }
                break;
        }

        // Check for collisions
        if (world[heroX][heroY] == 'W') {
            System.out.println("Game over! The Wumpus got you!");
            System.exit(0);
        } else if (world[heroX][heroY] == 'G') {
            System.out.println("Congratulations, " + name + "! You found the gold!");

            // Mark that the hero has the gold
            hasGold = true;
        }

        // Update the hero's position
        world[heroX][heroY] = 'H';

        // Check if the hero is back to the starting position with the gold
        if (hasGold && heroX == spawnX && heroY == spawnY) {
            System.out.println("You win, " + name + "! You brought the gold back to the starting position!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new WumpusWorld();
    }
}
