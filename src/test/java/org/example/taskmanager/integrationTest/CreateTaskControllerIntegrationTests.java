package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.CreateTaskController;
import org.example.taskmanager.service.CreateTask;
import org.example.taskmanager.service.SaveTask;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class CreateTaskControllerIntegrationTests {

    private CreateTaskController createTaskController;

    private CreateTask createTask;

    private SaveTask saveTask;

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() throws SQLException {
        createTask = new CreateTask();
        saveTask = new SaveTask();
        createTaskController = new CreateTaskController(createTask, saveTask);
        ResponseEntity<String> output = createTaskController.createTask("case title", "", "open status", "05-05-2025 17:00:00");
        assert output != null;
        assert output.getBody().contains("Task Created");

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn;
        Statement stmt;
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM taskmanager";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> id = new ArrayList<>();

        while(rs.next()) {
            id.add(rs.getString("id"));
            id.add(rs.getString("title"));
            id.add(rs.getString("status"));
            id.add(rs.getString("duedate"));
            id.add(rs.getString("description"));
        }

        assert id.contains("case title");
        assert id.contains("open status");

    }
}
