import java.sql.*;

class DatabaseConnection {

    static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:students.db");
        } catch (SQLException e) {
            // System.out.println("Connection failed");
            e.printStackTrace();
            return null;
        }
    }

    static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, marks REAL)";
        // String sql = "CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, marks REAL)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Table created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}