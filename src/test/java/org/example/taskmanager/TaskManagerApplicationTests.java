package org.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TaskManagerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void checkADataBaseIsCreated() {
        //TaskManagerApplication.main(new String[] {});
        final String DB_URL = "jdbc:h2:file:C:/database/Taskmanager";
        assertDoesNotThrow(() -> {
            Connection conn2 = DriverManager.getConnection(DB_URL);
            Statement stmt2 = conn2.createStatement();
            String sql2 = "SELECT * FROM taskmanager";
            ResultSet rs = stmt2.executeQuery(sql2);
        });
    }


}
