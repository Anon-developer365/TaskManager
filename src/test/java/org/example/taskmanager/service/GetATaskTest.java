package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetATaskTest {
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
    private GetATask getATask;

    @Test
    void checkIfATaskIsRequestedAndExistsItIsReturned() throws SQLException {
        getATask = new GetATask();
        UUID uuid = UUID.randomUUID();

        String dueDate = "05-05-2025 17:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dueDate, formatter);

        Connection conn = DriverManager.getConnection(DB_URL);

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, "case title");
        pstmt.setString(3, "description");
        pstmt.setString(4, "open status");
        pstmt.setString(5, dueDate);

        pstmt.executeUpdate();
        Task task = getATask.getATask(uuid.toString());
        assert task != null;
        assert task.getId().equals(uuid.toString());

    }

    @Test
    void checkIfATaskIsRequestedAndDoesNotExistsAnErrorIsThrown() throws SQLException {
        getATask = new GetATask();
        UUID uuid = UUID.randomUUID();

        Throwable thrown = assertThrows(RuntimeException.class, () -> getATask.getATask(uuid.toString()));
        System.out.println(thrown.getMessage());
        assert thrown.getMessage().contains("Task with id " + uuid + " not found");
    }
}
