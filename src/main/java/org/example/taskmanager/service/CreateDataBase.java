package org.example.taskmanager.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to create a database.
 */
public class CreateDataBase {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:C:/database/Taskmanager";

    /**
     * Creates a database called task manager with the relevant fields.
     * @return boolean returns true if a database is created and false if not.
     */
    public static boolean createDatabase() {

        boolean success;
        Connection conn = null;
        Statement stmt = null;
        try{
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL);

            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   TASKMANAGER " +
                    "(id VARCHAR, " +
                    " title VARCHAR(20), " +
                    " description VARCHAR(200), " +
                    " status VARCHAR(200), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            success = true;

            stmt.close();
            conn.close();
        } catch(SQLException se) {
            throw new RuntimeException(se);
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
                success = false;
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                success = false;
            }
        }
        return success;
    }
}
