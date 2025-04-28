package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class RetrieveTasksTests {

    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
    private RetrieveTasks retrieveTasks;
    @Test
    void checkWhenThereAreTasksInTheDatabaseTheseAreAllReturned() throws SQLException {
        retrieveTasks = new RetrieveTasks();
        UUID uuid = UUID.randomUUID();

        String dueDate = "05-05-2025 17:00";

        Connection conn = DriverManager.getConnection(DB_URL);

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
        pstmt.setString(1, uuid.toString());
        pstmt.setString(2, "case title");
        pstmt.setString(3, "description");
        pstmt.setString(4, "open status");
        pstmt.setString(5, dueDate);

        pstmt.executeUpdate();

        List<Task> results = retrieveTasks.getAllTasks();
        assert !results.isEmpty();
        assert results.get(results.size() - 1).getId().equals(uuid.toString());
        assert results.get(results.size()-1).getDueDate().equals(dueDate);
    }

    @Test
    void whenTheDataBaseIsEmptyAnEmptyListIsReturned() throws SQLException {
        retrieveTasks = new RetrieveTasks();

        Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        String reset = "DROP TABLE IF EXISTS Taskmanager";
        stmt.executeUpdate(reset);

        String sql =  "CREATE TABLE   TASKMANAGER " +
                "(id VARCHAR, " +
                " title VARCHAR(20), " +
                " description VARCHAR(200), " +
                " status VARCHAR(200), " +
                "duedate VARCHAR(20)," +
                " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql);

        List<Task> results = retrieveTasks.getAllTasks();
        assert results.isEmpty();
    }
}
