package org.example.taskmanager.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Service
public class UpdateStatus {

    // database URL
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    public boolean updateStatus(String id, String status) {
        boolean success = false;
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
