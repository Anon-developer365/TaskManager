package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.RetrieveTaskController;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.GetATask;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class GetATaskIntegrationTest {

    private final GetATask getATask = new GetATask();
    private RetrieveTaskController retrieveTaskController;

    @Test
    void checkIfATaskExistsItIsReturned() throws SQLException {
        retrieveTaskController = new RetrieveTaskController(getATask);

        UUID uuid = UUID.randomUUID();
        String caseTitle = "Case Title";
        String description = "Case Description";
        String status = "Open status";
        String dueDate = "05-05-2025 17:00";

        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        Connection conn;
        conn = DriverManager.getConnection(DB_URL);

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, caseTitle);
        pstmt.setString(3, description);
        pstmt.setString(4, status);
        pstmt.setString(5, dueDate);

        pstmt.executeUpdate();

        ResponseEntity<Task> results =  retrieveTaskController.getTask(uuid.toString());
        assert Objects.requireNonNull(results.getBody()).getId().equals(uuid.toString());
        assert results.getBody().getDueDate().toString().equals(dueDate);
        System.out.println(results.getBody().getDueDate());
    }
}
