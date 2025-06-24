package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.RetrieveAllTasksController;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.RetrieveTasks;
import org.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RetrieveAllTasksControllerIntegrationTests {

    private RetrieveAllTasksController retrieveAllTasksController;

    private RetrieveTasks retrieveTasks;

    @Autowired
    private TaskService taskService;

    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";



    @Test
    void checkTheServiceRunsSuccessfullyWhenAListIsReturned() throws SQLException {
        retrieveTasks = new RetrieveTasks(taskService);
        retrieveAllTasksController = new RetrieveAllTasksController(retrieveTasks);

        UUID uuid = UUID.randomUUID();

        Connection conn = DriverManager.getConnection(DB_URL);

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, "case title");
        pstmt.setString(3, "description");
        pstmt.setString(4, "open status");
        pstmt.setString(5, "05-05-2025 17:00");

        pstmt.executeUpdate();

        ResponseEntity<List<Task>> output = retrieveAllTasksController.getAllTasks();
        assert Objects.requireNonNull(output.getBody()).get(output.getBody().size() -1).getId().equals(uuid.toString());
    }
}
