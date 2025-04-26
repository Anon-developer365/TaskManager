package org.example.taskmanager.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Create database class to create a H2 database in the directory database.
 */
public class CreateDataBase {

    /**
     * JDBC driver
     */
    static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * url for database connection.
     */
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    /**
     * Method to create database.
     */
    public void createDatabase() {
        Connection conn;
        Statement stmt;
        try{
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection to the database
            conn = DriverManager.getConnection(DB_URL);

            // create a statement
            stmt = conn.createStatement();
            String sql =  "CREATE TABLE   TASKMANAGER " +
                    "(id VARCHAR, " +
                    " title VARCHAR(20), " +
                    " description VARCHAR(200), " +
                    " status VARCHAR(200), " +
                    "duedate VARCHAR(20)," +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
