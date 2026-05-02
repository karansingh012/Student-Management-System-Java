# Student Management System (Java)

A console-based Student Management System built using Java and SQLite.  
This application demonstrates real-world backend development using JDBC, SQL queries, and Object-Oriented Programming.

---

## 🚀 Features

- Add new student records  
- View all students  
- Update student marks  
- Delete student by ID  
- Search student by ID  
- Export student data to CSV  
- Import student data from CSV  
- View top-performing students (sorted by marks)

---

## 🛠 Tech Stack

- Java (Core)
- JDBC (Java Database Connectivity)
- SQLite (Lightweight Database)

---

## 🧠 Concepts Applied

- Object-Oriented Programming (OOP)
- JDBC for database interaction
- SQL operations (INSERT, SELECT, UPDATE, DELETE)
- PreparedStatement for secure queries
- Input validation using Scanner
- File handling (CSV import & export)
---

## 📂 Project Structure

- Main.java → Handles user interaction and menu logic  
- Student.java → Model class representing student entity  
- StudentService.java → Core business logic + database operations  
- DatabaseConnection.java → Handles SQLite connection  

---

## ▶️ How to Run

### 1. Clone the repository
git clone https://github.com/yourusername/Student-Management-System-Java.git

### 2. Navigate to project folder
cd Student-Management-System-Java

### 3. Compile the project
javac -cp ".:sqlite-jdbc-3.53.0.0.jar" *.java

### 4. Run the application
java -cp ".:sqlite-jdbc-3.53.0.0.jar" Main

---

## ⚠️ Important Notes

- Ensure sqlite-jdbc-3.53.0.0.jar is present in the project directory  
- The database file (students.db) is created automatically  
- Generated files like .db and .csv are excluded using .gitignore  

---

## 📌 Key Highlights

- Persistent data storage using SQLite  
- Clean separation of concerns (MVC-like structure)  
- Robust input validation and error handling  
- Real-world features like CSV export and sorting  

---

## 🔮 Future Improvements
 
- Build GUI using JavaFX or Swing  
- Add authentication system (login/signup)  
- Deploy as a desktop application  

---

## 👨‍💻 Author

Karan Singh