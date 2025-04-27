package org.example.taskmanager.service;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdateStatusTests {

    private final UpdateStatus updateStatus = new UpdateStatus();

    @Test
    void checkTheServiceUpdatesAStatusIfTheTaskExists() throws SQLException {
        final String DB_URL = "jdbc:h2:file:database:/Taskmanager";
        UUID uuid = UUID.randomUUID();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            // set up a query
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status, dueDate)" + "VALUES(?,?,?,?,?)");
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, "case title");
            pstmt.setString(3, "description");
            pstmt.setString(4, "open status");
            pstmt.setString(5, "05-05-2025 17:00:00");

            pstmt.executeUpdate();

            updateStatus.updateStatus(uuid.toString(), "awaiting new part to continue development");

            Connection conn1;
            Statement stmt1;
            conn1 = DriverManager.getConnection(DB_URL);
            stmt1 = conn1.createStatement();
            String sql = "SELECT * FROM taskmanager";
            ResultSet rs = stmt1.executeQuery(sql);
            List<String> results = new ArrayList<>();

            while(rs.next()) {
                results.add(rs.getString("id"));
                results.add(rs.getString("title"));
                results.add(rs.getString("status"));
                results.add(rs.getString("duedate"));
                results.add(rs.getString("description"));
            }
            assert results.contains("awaiting new part to continue development");
            stmt1.close();
            conn1.close();
            pstmt.close();
        }
    }

}
