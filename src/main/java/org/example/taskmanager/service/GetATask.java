package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;

import java.sql.*;
import java.time.LocalDateTime;

public class GetATask {
    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    public Task getATask(String Id) {
        Task task = new Task(null, null, null, null, null);
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            // set a connection to the driver

            // set up a query

            String sql = "SELECT * FROM Taskmanager WHERE id = '" + Id + "'";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()) {

                task.setId(rs.getString("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setStatus(rs.getString("status"));
                task.setDueDate(LocalDateTime.parse(rs.getString("dueDate")));
            }


            System.out.println(task.getId());
                // catch any exceptions
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return task;
    }
}
