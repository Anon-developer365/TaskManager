package org.example.taskmanager.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * POJO for task object.
 */

@AllArgsConstructor
@Data
public class Task {
    private String id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime DueDate;
}
