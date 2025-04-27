package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * Class to save a task in the task manager database.
 */
@Service
public class SaveTask {
    // database URL
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";


    /**
     * Method to save data in the task manager database.
     * @param task task required to be saved in the database.
     * @return returns a success message with the assigned ID of the task.
     */
    public String saveData(Task task) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // set up a query
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
            pstmt.setString(1, task.getId());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getStatus());
            pstmt.setString(5, task.getDueDate().toString());

            pstmt.executeUpdate();


            // catch any exceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task.getId();
    }
}

