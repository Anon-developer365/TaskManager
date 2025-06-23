package org.example.taskmanager.pojo;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * POJO for task object.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="TASKMANAGER")
public class Task {
    @Id
    private String id;

    private String title;
    @Nullable
    private String description;
    private String status;
    private String DueDate;
}
