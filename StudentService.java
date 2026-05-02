import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

class StudentService {

    void addStudent(Student s) {
        String sql = "INSERT INTO students(id, name, marks) VALUES(?, ?, ?)";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, s.id);
            pstmt.setString(2, s.name);
            pstmt.setDouble(3, s.marks);
            pstmt.executeUpdate();

            System.out.println("Student added successfully.");

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("Student ID already exists.");
            } else {
                System.out.println(e.getMessage());
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection");
            }
        }
    }

    void viewStudents() {
        String sql = "SELECT * FROM students";
        boolean found = false;

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Marks: " + rs.getDouble("marks"));
            }

            if (!found) {
                System.out.println("No students available.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateMarks(int id, double newMarks) {
        String sql = "UPDATE students SET marks = ? WHERE id = ?";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newMarks);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Marks updated successfully.");
            } else {
                System.out.println("Student not found.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void searchStudent(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Student Found:");
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Marks: " + rs.getDouble("marks"));
            } else {
                System.out.println("Student not found.");
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void exportToCSV() {

        String sql = "SELECT * FROM students";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }
        try (Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery(sql);

             FileWriter writer = new FileWriter("students.csv")) {

            // Header

            writer.append("ID,Name,Marks\n");

            boolean found = false;

            while (rs.next()) {

                found = true;

                writer.append(rs.getInt("id") + ",");

                writer.append(rs.getString("name") + ",");

                writer.append(rs.getDouble("marks") + "\n");

            }

            if (!found) {
                System.out.println("No data to export.");
            }

            System.out.println("Data exported to students.csv");

        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }

    }
    void viewTopStudents() {
        String sql = "SELECT * FROM students ORDER BY marks DESC";

        Connection conn = DatabaseConnection.connect();
        if (conn == null) {
            System.out.println("Database connection error.");
            return;
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Marks: " + rs.getDouble("marks"));
            }

            if (!found) {
                System.out.println("No students available.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}