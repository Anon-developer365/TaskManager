package org.example.taskmanager.service;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class CreateDataBaseTests {

    CreateDataBase createDataBase = new CreateDataBase();

    @Test
    void checkADataBaseIsCreated() throws SQLException {

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn1 = DriverManager.getConnection(DB_URL);
        Statement stmt1 = conn1.createStatement();
        String reset = "DROP TABLE IF EXISTS Taskmanager";
        stmt1.executeUpdate(reset);
        createDataBase.createDatabase();
        assertDoesNotThrow(() -> {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM taskmanager";
            stmt.executeQuery(sql);
        });
    }


}
