package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdatbazisKezelo {

    private static final String URL = "jdbc:mysql://localhost:3306/wumpus";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    public static void adatbazisbolBeolvas() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM jatekosok";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String nev = resultSet.getString("nev");
                        int pontszam = resultSet.getInt("pontszam");
                        System.out.println(nev + ": " + pontszam + " pont");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void toplistaMegjelenit() {
        List<String> toplista = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT name, victories FROM toplist ORDER BY victories DESC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String nev = resultSet.getString("name");
                        int pontszam = resultSet.getInt("victories");
                        toplista.add(nev + ": " + pontszam);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Toplist:");
        for (String entry : toplista) {
            System.out.println(entry);
        }
    }

    public static void adatbazisbaMent(String nev, int pontszam) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO jatekosok (nev, pontszam) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nev);
                preparedStatement.setInt(2, pontszam);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
