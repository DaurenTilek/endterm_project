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
            System.out.println("3. Find a student by id");
            System.out.println("4. List all books");
            System.out.println("5. Add a book");
            System.out.println("6. Find a book by id");
            System.out.println("7. Add a new loan");
            System.out.println("8. List all loans");
            System.out.println("9. Return a loaned book");
            System.out.println("10. List all students who ever took a particular book");
            System.out.println("11. List all loaned but not yet returned books of a student");
            System.out.println("12. Output the total number of pages a student has read");
            System.out.println("13. Popular book");
            System.out.println("14. Unpopular book");
            System.out.println("15. Аll books by an author");
            System.out.println("16. Exit");

            int choice = scan.nextInt();
            if (choice == 0) break;

            switch (choice) {
                case 1: liststudents();break;
                case 2: addstudent(scan);break;
                case 3: findsid(scan);break;
                case 4: listbooks();break;
                case 5: addbook(scan);break;
                case 6: findbook(scan);break;
                case 7: issuebook(scan);break;
                case 8: listloans();break;
                case 9: returnbook(scan);break;
                case 10: whoevertookaparticularbook(scan);break;
                case 11: loanedbutnotyetreturnedbooksofastudent(scan);break;
                case 12: outputthetotalnumberofpagesastudenthasread(scan);break;
                case 13: popularbook();break;
                case 14: unpopularbook();break;
                case 20: allbooksauthor(scan);break;
                default: System.out.println("Invalid option");
            }
        }
    }

    private static void initialdb() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found: " + e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS students(sid INT PRIMARY KEY, firstname VARCHAR(50), lastname VARCHAR(50))");

            stmt.execute("CREATE TABLE IF NOT EXISTS books(bid INT PRIMARY KEY, title VARCHAR(50), author VARCHAR(50), pages INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS loans(lid INTEGER PRIMARY KEY AUTOINCREMENT, sid INT, bid INT, loan_date DATE, return_date DATE, FOREIGN KEY (sid) REFERENCES students(sid), FOREIGN KEY (bid) REFERENCES books(bid))");

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

    private static void findsid(Scanner scanner) {
        System.out.print("Enter studentid to find: ");
        int sid = scanner.nextInt();
        String sql = "SELECT * FROM students WHERE sid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                System.out.println("found student:" + res.getString("firstname") + " " + res.getString("lastname"));
            } else {
                System.out.println("student not found.");
            }
        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        }
    }
    private static void listbooks() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery("SELECT * FROM books")) {
            while (res.next()) {
                System.out.println("ID: " + res.getInt("bid") + "Title: " + res.getString("title") + "Author: " + res.getString("author"));
            }
        } catch (SQLException e)
        { System.out.println("еrror: " + e.getMessage()); }
    }

    private static void addbook(Scanner scan) {
        System.out.print("еnter bid: "); int bid = scan.nextInt();
        scan.nextLine();
        System.out.print("title:"); String title = scan.nextLine();
        System.out.print("author:"); String author = scan.nextLine();
        System.out.print("pages:"); int pages = scan.nextInt();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO books VALUES(?,?,?,?)")) {
            stmt.setInt(1, bid);
            stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.setInt(4, pages);
            stmt.executeUpdate();
            System.out.println("book added");
        } catch (SQLException e)
            { System.out.println("error: " + e.getMessage()); }
    }
    private static void findbook(Scanner scan) {
        System.out.print("enter bid to find: ");
        int bid = scan.nextInt();

        String sql = "SELECT * FROM books WHERE bid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bid);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                System.out.println("ID: " + res.getInt("bid"));
                System.out.println("Title: " + res.getString("title"));
                System.out.println("Author: " + res.getString("author"));
                System.out.println("Pages: " + res.getInt("pages"));
            } else {
                System.out.println("not found "+bid);
            }
        } catch (SQLException e) {
            System.out.println("error:" + e.getMessage());
        }
    }
    private static void issuebook(Scanner scan) {
        System.out.print("Enter sid:");
        int sid = scan.nextInt();
        System.out.print("Enter bid:");
        int bid = scan.nextInt();
        String date = java.time.LocalDate.now().toString();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO loans (sid, bid, issue_date) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, sid);
            pstmt.setInt(2, bid);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            System.out.println("Book " + bid + " issued to student " + sid + " оn" + date);
        } catch (SQLException e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }
    private static void listloans() {
        String sql = "SELECT * FROM loans";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {
            System.out.println("all Loans");
            while (res.next()) {
                System.out.println("student ID: " + res.getInt("sid") + "book ID: " + res.getInt("bid") + "date: " + res.getString("issue_date"));
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
    private static void returnbook(Scanner scan) {
        System.out.print("enter student id:");
        int sid = scan.nextInt();
        System.out.print("enter book id:");
        int bid = scan.nextInt();

        String sql = "DELETE FROM loans WHERE sid = ? AND bid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            pstmt.setInt(2, bid);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("book returned");
            } else {
                System.out.println("no such loan found");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
    private static void whoevertookaparticularbook(Scanner scan) {
        System.out.print("enter bid: ");
        int bid = scan.nextInt();
        String sql = "SELECT s.firstname FROM students s JOIN loans l ON s.sid = l.sid WHERE l.bid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bid);
            ResultSet res = pstmt.executeQuery();
            System.out.println("readers:");
            while (res.next())
                System.out.println( res.getString("firstname"));
        }
        catch (SQLException e)
        { System.out.println("Error: " + e.getMessage()); }
}
    private static void loanedbutnotyetreturnedbooksofastudent(Scanner scan) {
        System.out.print("enter sid: ");
        int sid = scan.nextInt();
        String sql = "SELECT b.title FROM books b JOIN loans l ON b.bid = l.bid WHERE l.sid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            ResultSet res = pstmt.executeQuery();
            System.out.println("books on hand:");
            while (res.next())
                System.out.println( res.getString("title"));
        }
        catch (SQLException e)
        { System.out.println("еrror:" + e.getMessage()); }
    }
    private static void outputthetotalnumberofpagesastudenthasread(Scanner scan) {
        System.out.print("enter sid: ");
        int sid = scan.nextInt();
        String sql = "SELECT SUM(b.pages) FROM books b JOIN loans l ON b.bid = l.bid WHERE l.sid = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sid);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
                System.out.println("total pages: " + res.getInt(1));
        } catch (SQLException e) {
            System.out.println("error: " + e.getMessage()); }
    }
    private static void popularbook() {
        String sql = "SELECT b.title, COUNT(l.bid) as count FROM books b " + "JOIN loans l ON b.bid = l.bid " + "GROUP BY b.bid ORDER BY count DESC";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {
            System.out.println("popular book");
            while (res.next()) {
                System.out.println(res.getString("title") + "times borrowed: " + res.getInt("count"));
            }
        } catch (SQLException e)
            { System.out.println("error:" + e.getMessage()); }
    }
    private static void unpopularbook() {
        String sql = "SELECT b.title, b.author FROM books b " + "LEFT JOIN loans l ON b.bid = l.bid " + "WHERE l.bid IS NULL";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {
            System.out.println("unpopular book");
            boolean found = false;
            while (res.next()) {
                found = true;
                System.out.println("Title: " + res.getString("title") + "Author: " + res.getString("author"));
            }
            if (!found) {
                System.out.println("no unpopular book");
            }
        }
        catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
    private static void allbooksauthor(Scanner scan) {
        System.out.print("enter author's name:");
        scan.nextLine();
        String author = scan.nextLine();
        String sql = "SELECT * FROM books WHERE author LIKE ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + author + "%");
            ResultSet res = pstmt.executeQuery();
            System.out.println("books by " + author );
            boolean found = false;
            while (res.next()) {
                found = true;
                System.out.println("id: " + res.getInt("bid") + "title: " + res.getString("title") + "pages: " + res.getInt("pages"));
            }
            if (!found)
                System.out.println("no books found for this author.");
        }
        catch (SQLException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
