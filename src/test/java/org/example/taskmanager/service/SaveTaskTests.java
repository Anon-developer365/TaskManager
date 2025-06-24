package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class SaveTaskTests {

    @Autowired
    private SaveTask saveTask;


    @Test
    @Commit
    void testDataIsSavedInTheDatabase() {
        UUID uuid = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00:00";
        Task task = new Task("2", "develop database", "create a database", "open status", dueDate);
        String result = saveTask.saveData(task);
        System.out.println(result);
        assert result.equals("2");
    }

    @Test
    void checkTheDataBaseIsUsedToSaveTheData() throws SQLException {

        UUID uuid = UUID.randomUUID();
        String dueDate = "20-05-2025 09:00";

        Task task = new Task(uuid.toString(), "develop database", "", "open status", dueDate);
        String result = saveTask.saveData(task);
        assert result.equals(uuid.toString());

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn;
        Statement stmt;
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM Taskmanager";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> id = new ArrayList<>();

        while(rs.next()) {
            id.add(rs.getString("id"));
        }
        assert id.contains(uuid.toString());
        stmt.close();
        conn.close();
    }

}
