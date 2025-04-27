package org.example.taskmanager.pojo;

import jakarta.annotation.Nullable;
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
    @Nullable
    private String description;
    private String status;
    private LocalDateTime DueDate;
}
