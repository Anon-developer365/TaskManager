package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SaveTaskTests {


    @Test
    void testDataIsSavedInTheDatabase() {
        UUID uuid = UUID.randomUUID();
        String date = "20-05-2025 09:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dueDate = LocalDateTime.parse(date, formatter);

        SaveTask saveTask = new SaveTask();
        Task task = new Task(uuid.toString(), "develop database", "", "open status", dueDate);
        String result = saveTask.saveData(task);
        assert result.equals(uuid.toString());

    }

    @Test
    void checkTheDataBaseIsUsedToSaveTheData() throws SQLException {

        UUID uuid = UUID.randomUUID();
        String date = "20-05-2025 09:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dueDate = LocalDateTime.parse(date, formatter);

        SaveTask saveTask = new SaveTask();
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
