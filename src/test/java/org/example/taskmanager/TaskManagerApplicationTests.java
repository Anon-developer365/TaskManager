package org.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TaskManagerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testMain() throws SQLException {
        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt1 = conn.createStatement();
        String reset = "DROP TABLE IF EXISTS Taskmanager";
        stmt1.executeUpdate(reset);
        TaskManagerApplication.main(new String[]{});
    }


}
