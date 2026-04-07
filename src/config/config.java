package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.proteanit.sql.DbUtils;

public class config {

    public static Integer currentAdminId = null;
    public static String currentAdminRole = null;
    public static boolean adminLoggedIn = false;

    // Connection Method to SQLITE
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); 
            con = DriverManager.getConnection("jdbc:sqlite:sapatotDB.db"); 
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }

    public static boolean isAdminLoggedIn() {
        return adminLoggedIn && currentAdminId != null && currentAdminRole != null;
    }

    public static void clearAdminSession() {
        currentAdminId = null;
        currentAdminRole = null;
        adminLoggedIn = false;
    }

    public static void setAdminSession(Integer id, String role) {
        currentAdminId = id;
        currentAdminRole = role;
        adminLoggedIn = true;
    }

    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]);
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]);
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]);
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]);
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]);
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    public boolean authenticate(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return false;
    }

    public void updateRecord(String sql, Object... values) {
        try (Connection conn = connectDB(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]);
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]);
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]);
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]);
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]);
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

   public ResultSet getData(String sql) throws SQLException {
    Connection conn = connectDB();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
   
    return rs;
}

    public void displayData(String sql, javax.swing.JTable table) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }

    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting record: " + e.getMessage());
        }
    }

    public boolean recordExists(String sql, Object... values) {
    try (Connection conn = this.connectDB(); // Siguraduha nga mao ni ang imong connection method name
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        ResultSet rs = pstmt.executeQuery();
        return rs.next(); // Mu-return og true kung naay nakit-an nga record

    } catch (SQLException e) {
        System.out.println("Check Error: " + e.getMessage());
        return false;
    }

    }

    public static void ensureInventoryColumns() {
        try (Connection conn = connectDB()) {
            if (conn == null) return;
            
            // Check if table exists
            java.sql.DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, "tbl_inventory", null)) {
                if (!rs.next()) {
                    // Create table if it doesn't exist
                    String createTableSQL = "CREATE TABLE tbl_inventory ("
                        + "item_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "item_name TEXT NOT NULL, "
                        + "color TEXT, "
                        + "size TEXT, "
                        + "qty INTEGER DEFAULT 0, "
                        + "image_path TEXT, "
                        + "price REAL DEFAULT 0, "
                        + "brand TEXT, "
                        + "category TEXT DEFAULT 'all'"
                        + ")";
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(createTableSQL);
                        System.out.println("Table tbl_inventory created successfully");
                    }
                } else {
                    // Check and add missing columns if table exists
                    String[] columns = {"item_name TEXT NOT NULL", "color TEXT", "size TEXT", 
                                       "qty INTEGER DEFAULT 0", "image_path TEXT", "price REAL DEFAULT 0", 
                                       "brand TEXT", "category TEXT DEFAULT 'all'"};
                    
                    for (String column : columns) {
                        String columnName = column.split(" ")[0];
                        try (ResultSet rsColumns = meta.getColumns(null, null, "tbl_inventory", columnName)) {
                            if (!rsColumns.next()) {
                                String alterSQL = "ALTER TABLE tbl_inventory ADD COLUMN " + column;
                                try (Statement stmt = conn.createStatement()) {
                                    stmt.execute(alterSQL);
                                    System.out.println("Added column: " + columnName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error ensuring inventory columns: " + e.getMessage());
        }
    }
}
