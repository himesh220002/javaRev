package banking;

import java.sql.*;

public class BankManagement {

    static final Connection conn = ConnectionUtl.getConnection();

    static {
        if (conn != null) {
            System.out.println("Database connection is ready to use");
            initDb();
        } else {
            System.out.println("Database Connection failed.");
        }
    }

    public static void main(String[] args) {
        // can be used for direct testing
    }

    private static void initDb() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transaction_history ("
                + "tx_id INT AUTO_INCREMENT PRIMARY KEY, "
                + "ac_no INT, "
                + "tx_type VARCHAR(20), "
                + "amount INT, "
                + "tx_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Verified transaction_history table exists.");
        } catch (SQLException e) {
            System.err.println("Error initializing DB: " + e.getMessage());
        }
    }

    private static void recordTransaction(int acNo, String type, int amount) {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO transaction_history (ac_no, tx_type, amount) VALUES (?, ?, ?)"
        )) {
            ps.setInt(1, acNo);
            ps.setString(2, type);
            ps.setInt(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error recording transaction: " + e.getMessage());
        }
    }

    public static int createAccount(String name, int pass) {
        int randomAcNo = 100000 + new java.util.Random().nextInt(900000);
        
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (ac_no, cname, pass_code, balance) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, randomAcNo);
            ps.setString(2, name);
            ps.setInt(3, pass);
            ps.setDouble(4, 1000);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return randomAcNo;
            }
        } catch (SQLException e) {
            System.err.println("Error Creating Account: " + e.getMessage());
        }

        return -1; //failure
    }

    public static boolean loginAccount(String name, int pass) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM customer WHERE cname = ? AND pass_code = ?"
        )) {
            ps.setString(1, name);
            ps.setInt(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error Logging In: " + e.getMessage());
        }

        return false;
    }

    public static String getUserName(int acNo, int pass) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT cname FROM customer WHERE ac_no = ? AND pass_code = ?"
        )) {
            ps.setInt(1, acNo);
            ps.setInt(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("cname");
            }
        } catch (SQLException e) {
            System.err.println("Error Fetching User Details: " + e.getMessage());
        }
        return null;
    }

    public static int getBalance(int acNo, int pass) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM customer WHERE ac_no = ? AND pass_code = ?"
        )) {
            ps.setInt(1, acNo);
            ps.setInt(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.err.println("Error Getting Balance: " + e.getMessage());
        }
        return -1;
    }

    public static int deposite(int acNo, int amount) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE customer SET balance = balance + ? WHERE ac_no = ?"
        )) {
            ps.setInt(1, amount);
            ps.setInt(2, acNo);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                recordTransaction(acNo, "DEPOSIT", amount);
                try (PreparedStatement ps2 = conn.prepareStatement("SELECT balance FROM customer WHERE ac_no = ?")) {
                    ps2.setInt(1, acNo);
                    ResultSet rs = ps2.executeQuery();
                    if (rs.next()) {
                        return rs.getInt("balance");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Depositing: " + e.getMessage());
        }
        return -1;
    }

    public static int withdrawl(int acNo, int pass, int amount) {
        String UpdateWithdrawl = "UPDATE customer SET balance = balance - ? WHERE ac_no = ? AND pass_code = ? AND balance >= ?";

        try (PreparedStatement ps = conn.prepareStatement(UpdateWithdrawl)) {
            ps.setInt(1, amount);
            ps.setInt(2, acNo);
            ps.setInt(3, pass);
            ps.setInt(4, amount);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                recordTransaction(acNo, "WITHDRAW", amount);
                return getBalance(acNo, pass);
            } else {
                System.err.println("Transaction Failed: Invalid credentials or insufficient balance.");
            }
        } catch (SQLException e) {
            System.err.println("Error Withdrawing: " + e.getMessage());
        }
        return -1;
    }

    public static int transferMoney(int senderAcNo, int pass, int recepientAcNo, int amount) {

        if (amount <= 0 || senderAcNo == recepientAcNo) {
            return -1;
        }

        String updateTransfer = "UPDATE customer SET balance = balance - ? WHERE ac_no = ? AND pass_code = ? AND balance >= ?";
        String updateRecepitent = "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";

        try {
            conn.setAutoCommit(false);

            try {
                PreparedStatement ps = conn.prepareStatement(updateTransfer);
                ps.setInt(1, amount);
                ps.setInt(2, senderAcNo);
                ps.setInt(3, pass);
                ps.setInt(4, amount);
                int senderRows = ps.executeUpdate();
                if (senderRows == 0) {
                    System.err.println("Transfer Failed: Sender auth error or insufficient funds.");
                    conn.rollback(); // Cancel the transaction
                    return -1;
                }

            } catch (SQLException e) {
                System.err.println("Error Transferring: " + e.getMessage());
            }

            try {
                PreparedStatement ps = conn.prepareStatement(updateRecepitent);
                ps.setInt(1, amount);
                ps.setInt(2, recepientAcNo);
                int recepentRows = ps.executeUpdate();
                if (recepentRows == 0) {
                    System.err.println("Transfer Failed: Recipient account not found.");
                    conn.rollback(); // Rollback sender's deduction entirely
                    return -2;
                }
            } catch (SQLException e) {
                System.err.println("Error Depositing: " + e.getMessage());
            }

            conn.commit();
            recordTransaction(senderAcNo, "TRANSFER_OUT", amount);
            recordTransaction(recepientAcNo, "TRANSFER_IN", amount);
            return getBalance(senderAcNo, pass);

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.err.println("Error Rolling back: " + e2.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Always restore normal Auto-Commit state before finishing
                }
            } catch (SQLException e) {
                System.err.println("Error Setting AutoCommit: " + e.getMessage());
            }
        }

        return -1;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed cleanly.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static String getRecentTransactions(int acNo) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT tx_type, amount, tx_date FROM transaction_history WHERE ac_no = ? AND tx_date >= DATE_SUB(NOW(), INTERVAL 7 DAY) ORDER BY tx_date ASC"
        )) {
            ps.setInt(1, acNo);
            ResultSet rs = ps.executeQuery();
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",");
                }
                json.append("{")
                    .append("\"type\":\"").append(rs.getString("tx_type")).append("\",")
                    .append("\"amount\":").append(rs.getInt("amount")).append(",")
                    .append("\"date\":\"").append(rs.getTimestamp("tx_date").toString()).append("\"")
                    .append("}");
                first = false;
            }
        } catch (SQLException e) {
            System.err.println("Error Fetching Transactions: " + e.getMessage());
        }
        json.append("]");
        return json.toString();
    }
}
