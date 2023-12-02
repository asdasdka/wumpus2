package Game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class WumpusWorldTest {

    private InputStream originalSystemIn;
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        originalSystemIn = System.in;
        originalSystemOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    // ... Existing tests ...

    @Test
    public void testInitialization() {
        // Test whether the game initializes correctly without errors
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nq\n".getBytes());
        System.setIn(testInput);

        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        assertNotNull(wumpusWorld);
    }

    // ... Existing tests ...

    @Test
    public void testShootArrow() {
        // Test shooting arrows in different scenarios

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\ne\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Ensure arrows are available
        WumpusWorld.setArrows(1);

        // Set the direction to 'E'
        WumpusWorld.setDirection('E');

        // Test shooting an arrow and hitting the Wumpus
        WumpusWorld.shootArrow();
        assertTrue(outputStream.toString().contains("Arrow hit the Wumpus!"));

        // Test shooting an arrow and missing the Wumpus
        WumpusWorld.shootArrow();
        assertTrue(outputStream.toString().contains("Arrow missed and disappeared"));

        // Test shooting when out of arrows
        WumpusWorld.setArrows(0);
        WumpusWorld.shootArrow();
        assertTrue(outputStream.toString().contains("Out of arrows! You can no longer shoot."));
    }

    @Test
    public void testMoveForward() {
        // Test moving forward in different directions

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nw\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Set the hero's position to the middle of the world
        WumpusWorld.setHeroX(WumpusWorld.getSize() / 2);
        WumpusWorld.setHeroY(WumpusWorld.getSize() / 2);

        // Test moving forward in each direction
        WumpusWorld.setDirection('N');
        WumpusWorld.moveForward();
        assertEquals(WumpusWorld.getHeroX(), WumpusWorld.getSize() / 2 - 1);

        WumpusWorld.setDirection('E');
        WumpusWorld.moveForward();
        assertEquals(WumpusWorld.getHeroY(), WumpusWorld.getSize() / 2 + 1);

        WumpusWorld.setDirection('S');
        WumpusWorld.moveForward();
        assertEquals(WumpusWorld.getHeroX(), WumpusWorld.getSize() / 2 + 1);

        WumpusWorld.setDirection('W');
        WumpusWorld.moveForward();
        assertEquals(WumpusWorld.getHeroY(), WumpusWorld.getSize() / 2 - 1);
    }

    @Test
    public void testTurnLeft() {
        // Test turning left in different directions

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\na\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Set the initial direction to 'N'
        WumpusWorld.setDirection('N');

        // Test turning left in each direction
        WumpusWorld.turnLeft();
        assertEquals(WumpusWorld.getDirection(), 'W');

        WumpusWorld.turnLeft();
        assertEquals(WumpusWorld.getDirection(), 'S');

        WumpusWorld.turnLeft();
        assertEquals(WumpusWorld.getDirection(), 'E');

        WumpusWorld.turnLeft();
        assertEquals(WumpusWorld.getDirection(), 'N');
    }

    @Test
    public void testTurnRight() {
        // Test turning right in different directions

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nd\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Set the initial direction to 'N'
        WumpusWorld.setDirection('N');

        // Test turning right in each direction
        WumpusWorld.turnRight();
        assertEquals(WumpusWorld.getDirection(), 'E');

        WumpusWorld.turnRight();
        assertEquals(WumpusWorld.getDirection(), 'S');

        WumpusWorld.turnRight();
        assertEquals(WumpusWorld.getDirection(), 'W');

        WumpusWorld.turnRight();
        assertEquals(WumpusWorld.getDirection(), 'N');
    }

    @Test
    public void testTurnAround() {
        // Test turning around in different directions

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\ns\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Set the initial direction to 'N'
        WumpusWorld.setDirection('N');

        // Test turning around
        WumpusWorld.turnAround();
        assertEquals(WumpusWorld.getDirection(), 'S');
    }

    @Test
    public void testInitializeWorld() {
        // Test the initialization of the game world

        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nw\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Check if the world is properly initialized
        char[][] world = WumpusWorld.getWorld();
        assertNotNull(world);
        assertEquals(WumpusWorld.getSize() + 2, world.length); // Including walls

        // Check if the hero, gold, wumpus, and pit positions are valid
        char heroCell = world[WumpusWorld.getHeroX()][WumpusWorld.getHeroY()];
        assertEquals('H', heroCell);

        char goldCell = world[WumpusWorld.getGoldX()][WumpusWorld.getGoldY()];
        assertEquals('G', goldCell);

        int numWumpus = WumpusWorld.getNumWumpus();
        for (int i = 0; i < numWumpus; i++) {
            char wumpusCell = world[WumpusWorld.getPitX()][WumpusWorld.getPitY()];
            assertEquals('U', wumpusCell);
        }

        int numPits = WumpusWorld.getNumWumpus();
        for (int i = 0; i < numPits; i++) {
            char pitCell = world[WumpusWorld.getPitX()][WumpusWorld.getPitY()];
            assertEquals('P', pitCell);
        }
    }

    @Test
    public void testPerformMove() {
        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Move the hero to the next position
        wumpusWorld.performMove('W');
        assertEquals('H', WumpusWorld.getWorld()[WumpusWorld.getHeroX()][WumpusWorld.getHeroY()]);
        assertEquals(' ', WumpusWorld.getWorld()[WumpusWorld.getHeroX() + 1][WumpusWorld.getHeroY()]);
    }

    @Test
    public void testGameWinCondition() {
        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nwwwwwlwwwwwlwwwwwlwwwwwlwwwwwlwwwwwlwwwwwq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Set hero's position to the gold position
        WumpusWorld.setHeroX(WumpusWorld.getGoldX());
        WumpusWorld.setHeroY(WumpusWorld.getGoldY());

        // Perform moves to bring the gold back to the starting position
        for (int i = 0; i < 6; i++) {
            wumpusWorld.performMove('W'); // Use the wumpusWorld instance instead of the class name
        }

        // Check if the game ends with a win
        assertTrue(wumpusWorld.isHasGold());
        assertEquals('H', wumpusWorld.getWorld()[wumpusWorld.getSpawnX()][wumpusWorld.getSpawnY()]);
    }

    @Test
    public void testQuitGame() {
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Ensure the game terminated
        assertTrue(outputStream.toString().contains("Game over. Thanks for playing!"));
    }

    @Test
    public void testMoveToBoundaries() {
        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nwwwwwwwwwwdwwwwwwwwwwq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Ensure the hero does not move beyond the boundaries
        assertEquals(1, WumpusWorld.getHeroX());
        assertEquals(WumpusWorld.getSize(), WumpusWorld.getHeroY());
    }

    @Test
    public void testShootArrowWumpusElimination() {
        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\neq\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Ensure the Wumpus is eliminated
        assertTrue(outputStream.toString().contains("Arrow hit the Wumpus! Wumpus eliminated!"));
    }

    @Test
    public void testFallIntoPit() {
        // Initialize the game
        ByteArrayInputStream testInput = new ByteArrayInputStream("John\n10\nws\n".getBytes());
        System.setIn(testInput);
        Scanner scanner = new Scanner(System.in);
        WumpusWorld wumpusWorld = new WumpusWorld(scanner, System.out);

        // Ensure the hero loses an arrow and respawns
        assertTrue(outputStream.toString().contains("Oh no! You fell into a pit and lost an arrow!"));
    }
}
