package org.example.taskmanager.integrationTest;

import org.example.taskmanager.controllers.UpdateStatusController;
import org.example.taskmanager.controllers.UpdateStatusValidation;
import org.example.taskmanager.service.UpdateStatus;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdateStatusControllerIntegrationTests {

    private UpdateStatusController updateStatusController;

    private UpdateStatus updateStatus;

    private UpdateStatusValidation updateStatusValidation;

    @Test
    void checkAStatusIsUpdateWhenFoundInTheDatabase() throws SQLException {
        updateStatus = new UpdateStatus();
        updateStatusValidation = new UpdateStatusValidation();
        updateStatusController = new UpdateStatusController(updateStatus, updateStatusValidation);
        UUID uuid = UUID.randomUUID();
        String caseTitle = "Case Title";
        String description = "Case Description";
        String status = "Original status";
        String dueDate = "05-05-2025 17:00:00";

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

        String changedStatus = "This is a new status";
        updateStatusController.updateStatus(uuid.toString(), changedStatus);

        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM taskmanager";
        ResultSet rs = stmt.executeQuery(sql);
        List<String> results = new ArrayList<>();

        while(rs.next()) {
            results.add(rs.getString("id"));
            results.add(rs.getString("title"));
            results.add(rs.getString("status"));
            results.add(rs.getString("duedate"));
            results.add(rs.getString("description"));
        }

        assert results.contains(changedStatus);
    }
}
