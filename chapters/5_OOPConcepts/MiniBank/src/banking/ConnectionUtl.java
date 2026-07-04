package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtl {

    private static final String URL = "jdbc:mariadb://localhost:3306/BANK";
    private static final String USER = "bankuser";
    private static final String PASSWORD = "5801";

    public static Connection getConnection() {
        try {  //Load MariaDB driver 
            Class.forName("org.mariadb.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MariaDB successfully!");
            return conn;
        } catch (ClassNotFoundException e) {

            System.out.println("❌ MariaDB JDBC driver not found.");
            e.getMessage();
            return null;
        } catch (SQLException e) {
            System.out.println("❌ Connection failed.");
            e.getMessage();
            return null;
        }
    }

}
