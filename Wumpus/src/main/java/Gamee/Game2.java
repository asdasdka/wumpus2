package Gamee;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.util.Scanner;

public class Game2 {
    private World2 world;
    private Player2 player;

    public Game2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();

        // Kérje be a pálya méretét 6 és 20 között
        int size;
        do {
            System.out.println("Please set the map's size (N x N, excluding walls, between 6 and 20): ");
            size = scanner.nextInt();
        } while (size < 6 || size > 20);

        world = new World2(size);
        player = new Player2(name, world);
        // Állítsa be a Wumpusok számát a pálya mérete alapján
        if (size <= 8) {
            World2.setNumWumpus(1);
        } else if (size <= 14) {
            World2.setNumWumpus(2);
        } else {
            World2.setNumWumpus(3);
        }
        // Állítsa be a nyilak számát a Wumpusok számával
        Player2.setArrows(World2.getNumWumpus());
        world.initializeWorld(player);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            world.printWorld();
            System.out.println("Enter your move (W to move, Q to quit, R/L to turn, E to shoot arrow): ");
            char move = scanner.next().charAt(0);
            if (move == 'Q' || move == 'q') {
                System.out.println("Game over. Thanks for playing!");
                break;
            } else if (move == 'R' || move == 'r') {
                // Jobbra forgás esetén változtassa meg az irányt
                player.turnRight();
                world.printWorld(); // Pálya kiírása irányváltoztatás után
            } else if (move == 'L' || move == 'l') {
                // Balra forgás esetén változtassa meg az irányt
                player.turnLeft();
                world.printWorld(); // Pálya kiírása irányváltoztatás után
            } else if (move == 'E' || move == 'e') {
                // Nyíl kilövése az aktuális irányba
                player.shootArrow();
                world.printWorld(); // Pálya kiírása lövés után
            } else {
                player.performMove(move);
                world.printWorld();
            }
        }
    }
}
