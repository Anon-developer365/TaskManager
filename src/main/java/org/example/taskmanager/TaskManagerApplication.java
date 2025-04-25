package org.example.taskmanager;

import org.example.taskmanager.service.CreateDataBase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {
    static final CreateDataBase createDataBase = new CreateDataBase();

    public static void main(String[] args) {
        createDataBase.createDatabase();
    }

}
