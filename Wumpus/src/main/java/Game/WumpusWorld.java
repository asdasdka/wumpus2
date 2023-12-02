package Game;

import database.Database;

import java.io.PrintStream;
import java.util.Scanner;

public class WumpusWorld {

    private final Scanner scanner;
    private final PrintStream printStream;
    private static int size;
    private static char[][] world;
    private static int heroX;
    private static int heroY;
    private static int spawnX;
    private static int spawnY;
    private static int goldX;
    private static int goldY;
    private static int pitX;
    private static int pitY;
    private static int numWumpus;
    private static int arrows;
    private static char direction = 'E'; // Kezdetben keleti irány
    private static boolean hasGold = false;
    private static String name;
    private Database database;


    public WumpusWorld(Scanner scanner, PrintStream printStream) {
        this.scanner = scanner;
        this.printStream = printStream;
        System.out.println("What is your name?");
        name = scanner.nextLine();
        // Kérje be a pálya méretét 6 és 20 között
        do {
            System.out.println("Please set the map's size (N x N, excluding walls, between 6 and 20): ");
            size = scanner.nextInt();
        } while (size < 6 || size > 20);
        // Állítsa be a Wumpusok számát a pálya mérete alapján
        if (size <= 8) {
            numWumpus = 1;
        } else if (size <= 14) {
            numWumpus = 2;
        } else {
            numWumpus = 3;
        }
        // Állítsa be a nyilak számát a Wumpusok számával
        arrows = numWumpus;
        initializeWorld();
        printWorld();
        while (true) {
            this.printStream.println("Enter your move (W to move, Q to quit, R/L to turn, E to shoot arrow): ");
            char move = this.scanner.next().charAt(0);
            if (move == 'Q' || move == 'q') {
                System.out.println("Game over. Thanks for playing!");
                break;
            } else if (move == 'R' || move == 'r') {
                // Jobbra forgás esetén változtassa meg az irányt
                turnRight();
                printWorld(); // Pálya kiírása irányváltoztatás után
            } else if (move == 'L' || move == 'l') {
                // Balra forgás esetén változtassa meg az irányt
                turnLeft();
                printWorld(); // Pálya kiírása irányváltoztatás után
            } else if (move == 'E' || move == 'e') {
                // Nyíl kilövése az aktuális irányba
                shootArrow();
                printWorld(); // Pálya kiírása lövés után
            } else {
                performMove(move);
                printWorld();
            }
        }
        this.database = new Database();
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

        // Place the Wumpusokat a megadott számban véletlenszerű pozíciókra
        for (int k = 0; k < numWumpus; k++) {
            int wumpusX;
            int wumpusY;
            do {
                wumpusX = (int) (Math.random() * (size)) + 1;
                wumpusY = (int) (Math.random() * (size)) + 1;
            } while (world[wumpusX][wumpusY] == 'W' || (wumpusX == heroX && wumpusY == heroY));

            world[wumpusX][wumpusY] = 'U'; // Wumpus 'U'-val jelölve
        }

        // Place the gold in an empty random position
        do {
            goldX = (int) (Math.random() * (size)) + 1;
            goldY = (int) (Math.random() * (size)) + 1;
        } while (world[goldX][goldY] == 'W' || (goldX == heroX && goldY == heroY));

        world[goldX][goldY] = 'G';

        // Place the pits at random positions
        for (int p = 0; p < numWumpus; p++) {
            do {
                pitX = (int) (Math.random() * (size)) + 1;
                pitY = (int) (Math.random() * (size)) + 1;
            } while (world[pitX][pitY] == 'W' || world[pitX][pitY] == 'P' || world[pitX][pitY] == 'H' || world[pitX][pitY] == 'G');

            world[pitX][pitY] = 'P';
        }
    }

    private void printWorld() {
        // Print the current state of the world
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        this.printStream.println("Direction: " + direction);
        this.printStream.println("Arrows: " + arrows);
        this.printStream.println();
    }

    public void performMove(char move) {
        // Move the hero based on user input
        world[heroX][heroY] = ' '; // Clear the current position

        int prevHeroX = heroX;
        int prevHeroY = heroY;

        // Mozgás az aktuális irányba
        switch (move) {
            case 'W':
            case 'w':
                moveForward();
                break;
            case 'A':
            case 'a':
                turnLeft();
                break;
            case 'S':
            case 's':
                turnAround();
                break;
            case 'D':
            case 'd':
                turnRight();
                break;
            case 'Q':
            case 'q':
                System.out.println("Game over. Thanks for playing!");
                AdatbazisKezelo.adatbazisbaMent(name, arrows); // Mentés az adatbázisba
                System.exit(0);
            default:
                performMove(move);
                printWorld();
        }

        // Check for collisions
        if (world[heroX][heroY] == 'U') {
            System.out.println("Game over! The Wumpus got you!");
            System.exit(0);
        } else if (world[heroX][heroY] == 'G') {
            System.out.println("Congratulations, " + name + "! You found the gold!");

            // Mark that the hero has the gold
            hasGold = true;
        } else if (world[heroX][heroY] == 'P') {
            System.out.println("Oh no! You fell into a pit and lost an arrow!");
            arrows--;

            // Check if the hero is out of arrows
            if (arrows <= 0) {
                System.out.println("Out of arrows! Game over.");
                System.exit(0);
            }

            // Respawn the pit at the hero's previous position
            world[prevHeroX][prevHeroY] = 'P';

            // Check if the hero is out of arrows after falling into a pit
            if (arrows <= 0) {
                System.out.println("Out of arrows! Game over.");
                System.exit(0);
            }
        }

        // Update the hero's position
        world[heroX][heroY] = 'H';

        // Check if the hero is back to the starting position with the gold
        if (hasGold && heroX == spawnX && heroY == spawnY) {
            System.out.println("You win, " + name + "! You brought the gold back to the starting position!");
            database.insertScore(name);
            System.exit(0);
        }
    }


    public static void moveForward() {
        // Mozgás az aktuális irányba
        switch (direction) {
            case 'N':
                if (heroX > 1 && world[heroX - 1][heroY] != 'W') {
                    heroX--;
                }
                break;
            case 'E':
                if (heroY < size && world[heroX][heroY + 1] != 'W') {
                    heroY++;
                }
                break;
            case 'S':
                if (heroX < size && world[heroX + 1][heroY] != 'W') {
                    heroX++;
                }
                break;
            case 'W':
                if (heroY > 1 && world[heroX][heroY - 1] != 'W') {
                    heroY--;
                }
                break;
            default:
                break;
        }
    }

    public static void turnLeft() {
        // Balra forgás esetén változtassa meg az irányt
        switch (direction) {
            case 'N':
                direction = 'W';
                break;
            case 'E':
                direction = 'N';
                break;
            case 'S':
                direction = 'E';
                break;
            case 'W':
                direction = 'S';
                break;
            default:
                break;
        }
        System.out.println("Direction: " + direction);
    }

    public static void turnRight() {
        // Jobbra forgás esetén változtassa meg az irányt
        switch (direction) {
            case 'N':
                direction = 'E';
                break;
            case 'E':
                direction = 'S';
                break;
            case 'S':
                direction = 'W';
                break;
            case 'W':
                direction = 'N';
                break;
            default:
                break;
        }
        System.out.println("Direction: " + direction);
    }

    public static void turnAround() {
        // Fordulás 180 fokkal (két lépés balra)
        turnLeft();
        turnLeft();
    }

    public static void shootArrow() {
        // Lövés esetén csökkentse a nyilak számát
        if (arrows > 0) {
            arrows--;

            System.out.println("You shot an arrow! Arrows left: " + arrows);

            // Ellenőrizze, hogy a Wumpus eltalálta-e a lövés
            int arrowX = heroX;
            int arrowY = heroY;

            boolean wumpusHit = false;

            while (true) {
                switch (direction) {
                    case 'N':
                        arrowX--;
                        break;
                    case 'E':
                        arrowY++;
                        break;
                    case 'S':
                        arrowX++;
                        break;
                    case 'W':
                        arrowY--;
                        break;
                    default:
                        break;
                }

                // Ha a nyíl eléri a pálya szélét, akkor eltűnik
                if (arrowX <= 0 || arrowX >= size || arrowY <= 0 || arrowY >= size) {
                    System.out.println("Arrow missed and disappeared!");
                    break;
                }

                // Ha a nyíl eltalál egy Wumpust, akkor
                // a Wumpus helyén megjelenik egy 'X'
                if (world[arrowX][arrowY] == 'U') {
                    System.out.println("Arrow hit the Wumpus! Wumpus eliminated!");
                    world[arrowX][arrowY] = ' ';  // Clear the Wumpus from the cell
                    wumpusHit = true;
                    break;
                }
            }

            // Ha nem talált a nyíl Wumpust, akkor a célmezőn az arrowX és arrowY pozíció
            if (!wumpusHit) {
                System.out.println("Arrow missed and disappeared at (" + arrowX + ", " + arrowY + ")");
            }
        } else {
            System.out.println("Out of arrows! You can no longer shoot.");
        }
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        WumpusWorld.size = size;
    }

    public static char[][] getWorld() {
        return world;
    }

    public static void setWorld(char[][] world) {
        WumpusWorld.world = world;
    }

    public static int getHeroX() {
        return heroX;
    }

    public static void setHeroX(int heroX) {
        WumpusWorld.heroX = heroX;
    }

    public static int getHeroY() {
        return heroY;
    }

    public static void setHeroY(int heroY) {
        WumpusWorld.heroY = heroY;
    }

    public static int getSpawnX() {
        return spawnX;
    }

    public static void setSpawnX(int spawnX) {
        WumpusWorld.spawnX = spawnX;
    }

    public static int getSpawnY() {
        return spawnY;
    }

    public static void setSpawnY(int spawnY) {
        WumpusWorld.spawnY = spawnY;
    }

    public static int getGoldX() {
        return goldX;
    }

    public static void setGoldX(int goldX) {
        WumpusWorld.goldX = goldX;
    }

    public static int getGoldY() {
        return goldY;
    }

    public static void setGoldY(int goldY) {
        WumpusWorld.goldY = goldY;
    }

    public static int getPitX() {
        return pitX;
    }

    public static void setPitX(int pitX) {
        WumpusWorld.pitX = pitX;
    }

    public static int getPitY() {
        return pitY;
    }

    public static void setPitY(int pitY) {
        WumpusWorld.pitY = pitY;
    }

    public static int getNumWumpus() {
        return numWumpus;
    }

    public static void setNumWumpus(int numWumpus) {
        WumpusWorld.numWumpus = numWumpus;
    }

    public static int getArrows() {
        return arrows;
    }

    public static void setArrows(int arrows) {
        WumpusWorld.arrows = arrows;
    }

    public static char getDirection() {
        return direction;
    }

    public static void setDirection(char direction) {
        WumpusWorld.direction = direction;
    }

    public static boolean isHasGold() {
        return hasGold;
    }

    public static void setHasGold(boolean hasGold) {
        WumpusWorld.hasGold = hasGold;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        WumpusWorld.name = name;
    }
}
