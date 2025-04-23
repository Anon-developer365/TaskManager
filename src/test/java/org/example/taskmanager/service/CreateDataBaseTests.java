package org.example.taskmanager.service;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBaseTests {
    private final CreateDataBase createDataBase = new CreateDataBase();

    @Test
    void testADataBaseIsCreated() throws SQLException {
        // reset database table for testing purposes
        final String DB_URL = "jdbc:h2:file:C:/database/Taskmanager";
        Connection conn;
        Statement stmt;
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement();
        String sql = "DROP TABLE IF EXISTS taskmanager";
        stmt.executeUpdate(sql);
        // execute the test to check a database is created.
        boolean result = createDataBase.createDatabase();
        assert result;

    }
}
