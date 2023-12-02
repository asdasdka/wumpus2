package database;

import java.sql.*;
import java.sql.DriverManager;

public class Database {
    static final String URL = "jdbc:mysql://localhost:3306/wumpus";
    static final String USER = "root";
    static final String PASSWORD = "";
    private final Connection connection;

    public Database() {
        this.connection = createConnection();
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertScore(String name){
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO toplist (name, victories) VALUES (?,1) ON DUPLICATE KEY UPDATE victories = victories + 1;")){
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
