package org.example.taskmanager.controllers;

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
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime DueDate;
}
