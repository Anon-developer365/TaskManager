package org.example.taskmanager.pojo;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime DueDate;
}
