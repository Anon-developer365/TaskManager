package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TaskRepository taskRepository;

    static final String DB_URL = "jdbc:h2:file:database:/Taskmanager";

    /**
     * Method to retrieve all task data from the database.
     *
     * @return a list of tasks within the database.
     * @throws SQLException
     */
    public List<Task> getAllTasks() throws SQLException {
        return taskRepository.findAll();

    }
}
