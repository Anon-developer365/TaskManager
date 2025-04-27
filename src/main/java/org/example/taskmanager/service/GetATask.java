package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Get a task class retrieves a task if it is present in the database or returns an error message.
 */
@Service
public class GetATask {

    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    /**
     * Method to retrieve a task.
     *
     * @param Id the id of the task to be returned.
     * @return the task that wants to be retrieved.
     */
    public Task getATask(String Id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Task task = new Task(null, null, null, null, null);
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // set up a query
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Taskmanager WHERE id = ?");
            pstmt.setString(1, Id);
            ResultSet rs = pstmt.executeQuery();


            while(rs.next()) {
                task.setId(rs.getString("ID"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setStatus(rs.getString("status"));
                task.setDueDate(LocalDateTime.parse(rs.getString("dueDate"), formatter));
            }

            if(task.getId() == null) {
                throw new RuntimeException("Task with ID " + Id + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }
}
