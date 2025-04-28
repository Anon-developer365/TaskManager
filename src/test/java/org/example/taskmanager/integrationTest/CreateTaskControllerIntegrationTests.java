package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.CreateTaskController;
import org.example.taskmanager.controllers.TaskValidation;
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

    private TaskValidation taskValidation;

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() throws SQLException {
        createTask = new CreateTask();
        saveTask = new SaveTask();
        taskValidation = new TaskValidation();
        createTaskController = new CreateTaskController(createTask, saveTask, taskValidation);
        ResponseEntity<String> output = createTaskController.createTask("case title", "", "open status", "05-05-2025 17:00");
        assert output != null;
        assert output.getBody().contains("Task Created");

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn;
        Statement stmt;
        conn = DriverManager.getConnection(DB_URL);
        stmt = conn.createStatement();
        String sql = "SELECT * FROM taskmanager";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> results = new ArrayList<>();

        while(rs.next()) {
            results.add(rs.getString("id"));
            results.add(rs.getString("title"));
            results.add(rs.getString("status"));
            results.add(rs.getString("duedate"));
            results.add(rs.getString("description"));
        }

        assert results.contains("case title");
        assert results.contains("open status");

    }
}
