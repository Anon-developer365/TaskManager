package org.example.taskmanager.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Update status class created to update the status of a task.
 */
@Service
public class UpdateStatus {

    // database URL
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    /**
     * Method to update the task with the matching Id number to the new status.
     *
     * @param id id of the task to be updated.
     * @param status new status of the task.
     * @return boolean returns true if a task is updated.
     */
    public boolean updateStatus(String id, String status) {
        boolean success;
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // set up a query
            PreparedStatement pstmt = conn.prepareStatement("UPDATE taskmanager SET status = ? WHERE id = ?");
            pstmt.setString(1, status);
            pstmt.setString(2, id);

            pstmt.executeUpdate();
            success = true;

            // catch any exceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
