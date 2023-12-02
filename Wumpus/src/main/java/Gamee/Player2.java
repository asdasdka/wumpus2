/*package Gamee;

public class Player2 {
    private String name;
    private char[][] world;
    private int heroY;
    private int heroX;
    private static int spawnX;
    private static int spawnY;
    private static char direction;
    private static int arrows;
    private boolean hasGold;

    public Player2(String name, World2 world) {
        this.name = name;
        this.world = world.getWorld();
        this.direction = 'E'; // Kezdetben keleti irány
        initializeHeroPosition();
    }

    public void initializeHeroPosition() {
        // Kezdeti pozíció beállítása random helyre a pályán
        do {
            heroY = (int) (Math.random() * (World2.getSize())) + 1;
            heroX = (int) (Math.random() * (World2.getSize())) + 1;
        } while (world[heroY][heroX] == 'W');

        spawnX = heroY;
        spawnY = heroX;
        world[heroY][heroX] = 'H';
    }

    public void performMove(char move) {
        // Move the hero based on user input
        world[heroY][heroX] = ' '; // Clear the current position

        int prevHeroX = heroY;
        int prevHeroY = heroX;

        Player2 player = this;

        switch (move) {
            case 'Q':
            case 'q':
                System.out.println("Game over. Thanks for playing!");
                System.exit(0);
                break;
            case 'R':
            case 'r':
                player.turnRight();
                break;
            case 'L':
            case 'l':
                player.turnLeft();
                break;
            case 'E':
            case 'e':
                player.shootArrow();
                break;
            case 'W':
            case 'w':
                player.moveForward();
                break;
            default:
                System.out.println("Invalid move. Try again.");
        }

        // Check for game over conditions here if needed




    // Check for collisions
        if (world[heroY][heroX] == 'U') {
            System.out.println("Game over! The Wumpus got you!");
            System.exit(0);
        } else if (world[heroY][heroX] == 'G') {
            System.out.println("Congratulations, " + name + "! You found the gold!");

            // Mark that the hero has the gold
            hasGold = true;
        } else if (world[heroY][heroX] == 'P') {
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
        world[heroY][heroX] = 'H';

        // Check if the hero is back to the starting position with the gold
        if (hasGold && heroY == spawnX && heroX == spawnY) {
            System.out.println("You win, " + name + "! You brought the gold back to the starting position!");
            System.exit(0);
        }
    }



    private void moveForward() {
        // Clear the current position
        world[heroY][heroX] = ' ';

        // Calculate the new position based on the current direction
        int newHeroY = heroY;
        int newHeroX = heroX;

        switch (direction) {
            case 'N':
                newHeroY--;
                break;
            case 'E':
                newHeroX++;
                break;
            case 'S':
                newHeroY++;
                break;
            case 'W':
                newHeroX--;
                break;
            default:
                break;
        }

        // Check if the new position is within the boundaries
        if (newHeroY >= 0 && newHeroY < World2.getSize() && newHeroX >= 0 && newHeroX < World2.getSize()) {
            // Check if the new position is not a wall
            if (world[newHeroY][newHeroX] != 'W') {
                // Update the hero's position
                heroY = newHeroY;
                heroX = newHeroX;
            } else {
                System.out.println("Cannot move into a wall.");
            }
        } else {
            System.out.println("Cannot move outside the boundaries.");
        }

        // Update the world with the new hero position
        world[heroY][heroX] = 'H';
    }










    public void turnLeft() {
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

    public void turnRight() {
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

    private void turnAround() {
        // Fordulás 180 fokkal (két lépés balra)
        turnLeft();
        turnLeft();
    }

    public void shootArrow() {
        // Lövés esetén csökkentse a nyilak számát
        if (arrows > 0) {
            arrows--;

            System.out.println("You shot an arrow! Arrows left: " + arrows);

            // Ellenőrizze, hogy a Wumpus eltalálta-e a lövés
            int arrowX = heroY;
            int arrowY = heroX;

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
                if (arrowX <= 0 || arrowX >= World2.getSize() || arrowY <= 0 || arrowY >= World2.getSize()) {
                    System.out.println("Arrow missed and disappeared!");
                    break;
                }

                // Ha a nyíl eltalál egy Wumpust, akkor
                // a Wumpus helyén megjelenik egy 'X'
                if (world[arrowX][arrowY] == 'U') {
                    System.out.println("Arrow hit the Wumpus! Wumpus eliminated!");
                    world[arrowX][arrowY] = 'X';
                    break;
                }
            }
        } else {
            System.out.println("Out of arrows! You can no longer shoot.");
        }
    }

    public String getName() {
        return name;
    }

    public char[][] getWorld() {
        return world;
    }

    public int getHeroY() {
        return heroY;
    }

    public int getHeroX() {
        return heroX;
    }

    public static char getDirection() {
        return direction;
    }

    public static int getArrows() {
        return arrows;
    }

    public boolean isHasGold() {
        return hasGold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(char[][] world) {
        this.world = world;
    }

    public void setHeroY(int heroY) {
        this.heroY = heroY;
    }

    public void setHeroX(int heroX) {
        this.heroX = heroX;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public static void setArrows(int arrows) {
        Player2.arrows = arrows;
    }

    public void setHasGold(boolean hasGold) {
        this.hasGold = hasGold;
    }

    public static int getSpawnX() {
        return spawnX;
    }

    public static void setSpawnX(int spawnX) {
        Player2.spawnX = spawnX;
    }

    public static int getSpawnY() {
        return spawnY;
    }

    public static void setSpawnY(int spawnY) {
        Player2.spawnY = spawnY;
    }
}*/