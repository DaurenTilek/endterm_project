import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:sqlite:library.db";

    public static void main(String[] args) {
        initialdb();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to Endterm Project 2525");
            System.out.println("1. List all students");
            System.out.println("2. Add a student");
            System.out.println("3. Find a student by ID");
            System.out.println("0. Exit");

            int choice = scan.nextInt();
            if (choice == 0) break;

            switch (choice) {
                case 1: liststudents(); break;
                case 2: addstudent(scan); break;
                default: System.out.println("Invalid option");
            }
        }
    }

    private static void initialdb() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE students(sid INT PRIMARY KEY, firstname VARCHAR(50), lastname VARCHAR(50))");

            stmt.execute("CREATE TABLE books(bid INT PRIMARY KEY, title VARCHAR(50), author VARCHAR(50), pages INT)");

            stmt.execute("CREATE TABLE loans(lid INTEGER PRIMARY KEY AUTOINCREMENT, sid INT, bid INT, loan_date DATE, return_date DATE, FOREIGN KEY (sid) REFERENCES students(sid), FOREIGN KEY (bid) REFERENCES books(bid))");

            System.out.println("Database initialized.");
        } catch (SQLException e) {
            System.out.println("Init error: " + e.getMessage());
        }
    }
    private static void liststudents() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM students")) {
            while (res.next()) {
                System.out.println("studentid:" + res.getInt("sid") + " " + res.getString("firstname") + " " + res.getString("lastname"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void addstudent(Scanner scanner) {
        System.out.print("Eenter studentid:");
        int sid = scanner.nextInt();
        System.out.print("firstname:");
        String fn = scanner.next();
        System.out.print("lastname:");
        String ln = scanner.next();

        String sql = "INSERT INTO students(sid, firstname, lastname) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            pstmt.setString(2, fn);
            pstmt.setString(3, ln);
            pstmt.executeUpdate();
            System.out.println("student added");
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


}