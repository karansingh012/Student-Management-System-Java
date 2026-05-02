import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

class StudentService {

    void addStudent(Student s) {
        String sql = "INSERT INTO students(id, name, marks) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = (conn == null ? null : conn.prepareStatement(sql))) {

            if (conn == null || pstmt == null) {
                System.out.println("Database connection error.");
                return;
            }

            pstmt.setInt(1, s.id);
            pstmt.setString(2, s.name);
            pstmt.setDouble(3, s.marks);
            pstmt.executeUpdate();

            System.out.println("Student added successfully.");

        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                System.out.println("Student ID already exists.");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }

    void viewStudents() {
        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = (conn == null ? null : conn.createStatement());
             ResultSet rs = (stmt == null ? null : stmt.executeQuery(sql))) {

            if (conn == null || stmt == null || rs == null) {
                System.out.println("Database connection error.");
                return;
            }

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Marks: " + rs.getDouble("marks"));
            }
            if (!found) System.out.println("No students available.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = (conn == null ? null : conn.prepareStatement(sql))) {

            if (conn == null || pstmt == null) {
                System.out.println("Database connection error.");
                return;
            }

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Student deleted successfully." : "Student not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateMarks(int id, double newMarks) {
        String sql = "UPDATE students SET marks = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = (conn == null ? null : conn.prepareStatement(sql))) {

            if (conn == null || pstmt == null) {
                System.out.println("Database connection error.");
                return;
            }

            pstmt.setDouble(1, newMarks);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Marks updated successfully." : "Student not found.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void searchStudent(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = (conn == null ? null : conn.prepareStatement(sql))) {

            if (conn == null || pstmt == null) {
                System.out.println("Database connection error.");
                return;
            }

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Student Found:");
                    System.out.println("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Marks: " + rs.getDouble("marks"));
                } else {
                    System.out.println("Student not found.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void exportToCSV() {

        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = (conn == null ? null : conn.createStatement());
             ResultSet rs = (stmt == null ? null : stmt.executeQuery(sql));
             FileWriter writer = new FileWriter("students.csv")) {

            if (conn == null || stmt == null || rs == null) {
                System.out.println("Database connection error.");
                return;
            }

            writer.append("ID,Name,Marks\n");
            boolean found = false;
            while (rs.next()) {
                found = true;
                writer.append(rs.getInt("id") + ",");
                writer.append(rs.getString("name") + ",");
                writer.append(rs.getDouble("marks") + "\n");
            }
            if (!found) System.out.println("No data to export.");
            System.out.println("Data exported to students.csv");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
    void viewTopStudents() {
        String sql = "SELECT * FROM students ORDER BY marks DESC LIMIT 3";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = (conn == null ? null : conn.createStatement());
             ResultSet rs = (stmt == null ? null : stmt.executeQuery(sql))) {

            if (conn == null || stmt == null || rs == null) {
                System.out.println("Database connection error.");
                return;
            }

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Marks: " + rs.getDouble("marks"));
            }
            if (!found) System.out.println("No students available.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void importFromCSV() {
        String filePath = "students.csv";

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath));
             Connection conn = DatabaseConnection.connect()) {

            if (conn == null) {
                System.out.println("Database connection error.");
                return;
            }

            String line = br.readLine(); // skip header
            String sql = "INSERT INTO students(id, name, marks) VALUES(?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] data = line.split(",");
                    if (data.length < 3) {
                        System.out.println("Skipping malformed row: " + line);
                        continue;
                    }

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    double marks = Double.parseDouble(data[2].trim());

                    pstmt.setInt(1, id);
                    pstmt.setString(2, name);
                    pstmt.setDouble(3, marks);

                    try {
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        if (e.getMessage() != null && e.getMessage().contains("UNIQUE")) {
                            System.out.println("Skipping duplicate ID: " + id);
                        } else {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                System.out.println("CSV data imported successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error reading CSV file.");
        }
    }
}