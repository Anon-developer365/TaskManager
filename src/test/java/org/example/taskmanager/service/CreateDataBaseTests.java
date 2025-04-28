package org.example.taskmanager.service;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateDataBaseTests {

    CreateDataBase createDataBase = new CreateDataBase();

    @Test
    void checkADataBaseIsCreated() throws SQLException {

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt1 = conn.createStatement();
        String reset = "DROP TABLE IF EXISTS Taskmanager";
        stmt1.executeUpdate(reset);
        Statement stmt = conn.createStatement();
        createDataBase.createDatabase();
        assertDoesNotThrow(() -> {
            String sql = "SELECT * FROM taskmanager";
            stmt.executeQuery(sql);
        });
        stmt.close();
        conn.close();
    }


}
