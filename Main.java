import java.sql.Connection;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.connect();
        if (conn != null) {
            System.out.println("Connected to database!");
        }
        DatabaseConnection.createTable(); 
        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Update Marks");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.println("7. Export to CSV");
            System.out.println("8. View Top Students");
            System.out.println("9. Import from CSV");
            System.out.print("Enter choice: ");

            int choice;
            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    break;
                } else {
                    System.out.print("Invalid choice. Enter a number: ");
                    sc.next();
                }
            }

            if (choice == 1) {
                System.out.print("Enter id: ");
                int id;
                while (true) {
                    if (sc.hasNextInt()) {
                        id = sc.nextInt();
                        break;
                    } else {
                        System.out.print("Invalid input. Enter numeric ID: ");
                        sc.next();
                    }
                }
                sc.nextLine();

                System.out.print("Enter name: ");
                String name = sc.nextLine();

                System.out.print("Enter marks: ");
                double marks = sc.nextDouble();
                sc.nextLine();

                service.addStudent(new Student(id, name, marks));
                System.out.println();

            } else if (choice == 2) {
                service.viewStudents();
                System.out.println();

            } else if (choice == 3) {
                System.out.print("Enter id to delete: ");
                int id;
                while (true) {
                    if (sc.hasNextInt()) {
                        id = sc.nextInt();
                        break;
                    } else {
                        System.out.print("Invalid input. Enter numeric ID: ");
                        sc.next();
                    }
                }
                service.deleteStudent(id);
                System.out.println();

            } else if (choice == 4) {
                System.out.print("Enter id to update: ");
                int id;
                while (true) {
                    if (sc.hasNextInt()) {
                        id = sc.nextInt();
                        break;
                    } else {
                        System.out.print("Invalid input. Enter numeric ID: ");
                        sc.next();
                    }
                }

                System.out.print("Enter new marks: ");
                double marks = sc.nextDouble();
                sc.nextLine();

                service.updateMarks(id, marks);
                System.out.println();

            } else if (choice == 5) {
                System.out.print("Enter id to search: ");
                int id;
                while (true) {
                    if (sc.hasNextInt()) {
                        id = sc.nextInt();
                        break;
                    } else {
                        System.out.print("Invalid input. Enter numeric ID: ");
                        sc.next();
                    }
                }
                service.searchStudent(id);
                System.out.println();

            } else if (choice == 6) {
                System.out.println("Exiting...");
                break;
            } else if (choice == 7) {
                service.exportToCSV();
                System.out.println();
            } else if (choice == 8) {
                service.viewTopStudents();
                System.out.println();
            } else if (choice == 9) {
                service.importFromCSV();
                System.out.println();
            }
        }
        sc.close();
    }
}