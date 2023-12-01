package Gamee;

class World2 {
    private static int size;
    private static char[][] world;
    private static int goldX;
    private static int goldY;
    private static int pitX;
    private static int pitY;
    private static int numWumpus;

    public World2(int size) {
        this.size = size;
        this.world = new char[size + 2][size + 2];
    }

    public void initializeWorld(Player2 player) {
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
            Player2.setHeroY((int) (Math.random() * (size)) + 1);
            Player2.setHeroX((int) (Math.random() * (size)) + 1);
        } while (world[Player2.getHeroY()][Player2.getHeroX()] == 'W');

        world[Player2.getHeroY()][Player2.getHeroX()] = 'H';
        Player2.setSpawnX(Player2.getHeroY());
        Player2.setSpawnY(Player2.getHeroX());

        // Place the Wumpusokat a megadott számban véletlenszerű pozíciókra
        for (int k = 0; k < numWumpus; k++) {
            int wumpusX;
            int wumpusY;
            do {
                wumpusX = (int) (Math.random() * (size)) + 1;
                wumpusY = (int) (Math.random() * (size)) + 1;
            } while (world[wumpusX][wumpusY] == 'W' || (wumpusX == Player2.getHeroY() && wumpusY == Player2.getHeroX()));

            world[wumpusX][wumpusY] = 'U'; // Wumpus 'U'-val jelölve
        }

        // Place the gold in an empty random position
        do {
            goldX = (int) (Math.random() * (size)) + 1;
            goldY = (int) (Math.random() * (size)) + 1;
        } while (world[goldX][goldY] == 'W' || (goldX == Player2.getHeroY() && goldY == Player2.getHeroX()));

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

    public void printWorld() {
        // Print the current state of the world
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world.length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Direction: " + Player2.getDirection()); // Kiírja az aktuális irányt
        System.out.println("Arrows: " + Player2.getArrows()); // Kiírja a nyilak számát
        System.out.println();
    }

    public char[][] getWolrd() {
        return world;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        World2.size = size;
    }

    public static char[][] getWorld() {
        return world;
    }

    public static void setWorld(char[][] world) {
        World2.world = world;
    }

    public static int getGoldX() {
        return goldX;
    }

    public static void setGoldX(int goldX) {
        World2.goldX = goldX;
    }

    public static int getGoldY() {
        return goldY;
    }

    public static void setGoldY(int goldY) {
        World2.goldY = goldY;
    }

    public static int getPitX() {
        return pitX;
    }

    public static void setPitX(int pitX) {
        World2.pitX = pitX;
    }

    public static int getPitY() {
        return pitY;
    }

    public static void setPitY(int pitY) {
        World2.pitY = pitY;
    }

    public static int getNumWumpus() {
        return numWumpus;
    }

    public static void setNumWumpus(int numWumpus) {
        World2.numWumpus = numWumpus;
    }
}
