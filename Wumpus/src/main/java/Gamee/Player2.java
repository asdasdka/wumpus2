package Gamee;

public class Player2 {
    private String name;
    private char[][] world;
    private static int heroY;
    private static int heroX;
    private static int spawnX;
    private static int spawnY;
    private static char direction;
    private static int arrows;
    private boolean hasGold;

    public Player2(String name, World2 world) {
        this.name = name;
        this.world = world.getWorld();
        this.direction = 'E'; // Kezdetben keleti irány
    }

    public void performMove(char move) {
        // Move the hero based on user input
        world[heroY][heroX] = ' '; // Clear the current position

        int prevHeroX = heroY;
        int prevHeroY = heroX;

        // Mozgás az aktuális irányba
        switch (move) {
            case 'W':
            case 'w':
                System.out.println(123);
                moveForward();
                break;
            case 'A':
            case 'a':
                turnLeft();
                System.out.println(321);
                break;
            case 'S':
            case 's':
                turnAround();
                break;
            case 'D':
            case 'd':
                turnRight();
                break;
        }

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
        // Mozgás az aktuális irányba
        switch (direction) {
            case 'N':
                if (heroY > 1 && world[heroY - 1][heroX] != 'W') {
                    heroY--;
                    System.out.println(1);
                }
                break;
            case 'E':
                if (heroX < World2.getSize() && world[heroY][heroX + 1] != 'W') {
                    heroX++;
                    System.out.println(2);
                }
                break;
            case 'S':
                if (heroY < World2.getSize() && world[heroY + 1][heroX] != 'W') {
                    heroY++;
                    System.out.println(3);
                }
                break;
            case 'W':
                if (heroX > 1 && world[heroY][heroX - 1] != 'W') {
                    heroX--;
                    System.out.println(4);
                }
                break;
            default:
                System.out.println(5);
                break;
        }
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

    public static int getHeroY() {
        return heroY;
    }

    public static int getHeroX() {
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

    public static void setHeroY(int heroY) {
        Player2.heroY = heroY;
    }

    public static void setHeroX(int heroX) {
        Player2.heroX = heroX;
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
}
