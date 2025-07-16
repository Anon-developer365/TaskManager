package org.example.taskmanager.pojo;

import jakarta.annotation.Nullable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * POJO for task object.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "TASKMANAGER")
public class Task {
    /**
     * Unique identifying ID for the task.
     */
    @Id
    private String id;

    /**
     * Title of the task.
     */
    private String title;

    /**
     * Description of the task.
     */
    @Nullable
    private String description;

    /**
     * Status of the task.
     */
    private String status;

    /**
     * Due date for the task.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime dueDate;
}
