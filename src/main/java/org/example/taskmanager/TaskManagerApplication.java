package org.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Task manager application class.
 */
@SpringBootApplication
public class TaskManagerApplication {

    /**
     * Main entry method for the application.
     *
     * @param args command line arguments to the application.
     */
    public static void main(final String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);

    }

}
