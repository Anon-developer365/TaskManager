package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to access the database and retrieve all tasks.
 */
@Service
public class RetrieveTasks {

    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    /**
     * Method to retrieve all task data from the database.
     *
     * @return a list of tasks within the database.
     * @throws SQLException
     */
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // set up a query
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Taskmanager";
            ResultSet rs = stmt.executeQuery(sql);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            Task task = new Task(null, null, null, null, null);
            while(rs.next()) {
                task.setId(rs.getString("ID"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setStatus(rs.getString("status"));
                task.setDueDate(rs.getString("dueDate"));
                tasks.add(task);
            }
        }
        return tasks;
    }
}
