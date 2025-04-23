package org.example.taskmanager.service;

import org.example.taskmanager.controllers.Task;

import java.sql.*;

/**
 * Class to save a task in the task manager database.
 */
public class SaveTask {
    // database URL
    static final String DB_URL = "jdbc:h2:file:C:/database/Taskmanager";


    /**
     * Method to save data in the task manager database.
     * @param task task required to be saved in the database.
     * @return returns a success message with the assigned ID of the task.
     */
    public String saveData(Task task) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // set a connection to the driver
            conn = DriverManager.getConnection(DB_URL);

            // set up a query
            stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Taskmanager(Id, Title, description, status)" + "VALUES(?,?,?,?)");
            pstmt.setString(1, task.getId());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getStatus());

            pstmt.executeUpdate();

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        return task.getId() + " successfully saved";
    }
}

