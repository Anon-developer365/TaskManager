package org.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TaskManagerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void checkADataBaseIsCreated() {
        final String DB_URL = "jdbc:h2:file:C:/database/Taskmanager";
        assertDoesNotThrow(() -> {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM taskmanager";
            stmt.executeQuery(sql);
        });
    }


}
