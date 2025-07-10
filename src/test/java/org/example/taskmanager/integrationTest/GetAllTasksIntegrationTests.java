package org.example.taskmanager.integrationTest;

import org.example.taskmanager.TaskManagerApplication;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
public class GetAllTasksIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void whenThereAreTasksInTheDatabaseThisIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("2", "title", "description", "status",dueDate);
        Task task2 = new Task("3", "title", "description", "status",dueDate);
        taskRepository.save(task);
        taskRepository.save(task2);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");

        mvc.perform(get("/allTasks").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks[0].id").value("2"))
                .andExpect(jsonPath("$.tasks[1].id").value("3"))
                .andReturn();
    }

    @Test
    void whenThereAreNoTasksInTheDatabaseAnEmptyListIsReturned() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");

        mvc.perform(get("/allTasks").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tasks").isEmpty())
                .andReturn();
    }
}
