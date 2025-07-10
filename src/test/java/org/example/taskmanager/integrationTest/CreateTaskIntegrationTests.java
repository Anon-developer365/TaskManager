package org.example.taskmanager.integrationTest;

import org.example.taskmanager.TaskManagerApplication;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
public class CreateTaskIntegrationTests {

    private final WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    private final TaskRepository taskRepository;


    @Autowired
    public CreateTaskIntegrationTests(TaskRepository taskRepository, WebApplicationContext webApplicationContext) {
        this.taskRepository = taskRepository;
        this.webApplicationContext = webApplicationContext;
    }

}
