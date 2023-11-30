package org.example;

import java.util.Scanner;

public class WumpusWorld{

    private static final int SIZE = 4;
    private static char[][] world = new char[SIZE][SIZE];
    private static int agentX, agentY;

    public WumpusWorld(int size) {
        initializeWorld();
        printWorld();

        Scanner scanner = new Scanner(System.in);

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
        // Initialize the world with empty cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                world[i][j] = ' ';
            }
        }

        // Place the agent in a random position
        agentX = (int) (Math.random() * SIZE);
        agentY = (int) (Math.random() * SIZE);
        world[agentX][agentY] = 'A';

        // Place the Wumpus in a random position
        int wumpusX, wumpusY;
        do {
            wumpusX = (int) (Math.random() * SIZE);
            wumpusY = (int) (Math.random() * SIZE);
        } while (wumpusX == agentX && wumpusY == agentY);

        world[wumpusX][wumpusY] = 'W';
    }

    private static void printWorld() {
        // Print the current state of the world
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void performMove(char move) {
        // Move the agent based on user input
        world[agentX][agentY] = ' '; // Clear the current position

        switch (move) {
            case 'W':
            case 'w':
                if (agentX > 0) {
                    agentX--;
                }
                break;
            case 'A':
            case 'a':
                if (agentY > 0) {
                    agentY--;
                }
                break;
            case 'S':
            case 's':
                if (agentX < SIZE - 1) {
                    agentX++;
                }
                break;
            case 'D':
            case 'd':
                if (agentY < SIZE - 1) {
                    agentY++;
                }
                break;
        }

        // Check for collisions
        if (world[agentX][agentY] == 'W') {
            System.out.println("Game over! The Wumpus got you!");
            System.exit(0);
        }

        // Update the agent's position
        world[agentX][agentY] = 'A';
    }
}
