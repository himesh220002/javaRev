import java.sql.*;
import banking.ConnectionUtl;
public class test_db {
    public static void main(String[] args) throws Exception {
        Connection conn = ConnectionUtl.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }
}
