package org.example.taskmanager.integrationTest;

import org.example.taskmanager.TaskManagerApplication;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
public class DeleteTaskIntegrationTests {
    private final MockMvc mvc;

    private final TaskRepository taskRepository;

    @Autowired
    public DeleteTaskIntegrationTests(TaskRepository taskRepository, WebApplicationContext webApplicationContext) {
        this.taskRepository = taskRepository;
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void reset() {
        taskRepository.deleteAll();
    }

    @Test
    void whenThereIsATaskInTheDatabaseAndTheIdMatchesThisIsDeleted() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("2", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "2");


        mvc.perform(delete("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.message").value("Task 2 deleted."))
                .andReturn();

    }

    @Test
    void whenThereIsATaskInTheDatabaseAndTheIdDoesNotMatchAnErrorIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("4", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "2");


        mvc.perform(delete("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[No task found with that ID: 2]"))
                .andReturn();

    }

    @Test
    void whenThereIsNoTaskIdInTheRequestAnErrorIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("4", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "");


        mvc.perform(delete("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Task ID is blank]"))
                .andReturn();

    }

    @Test
    void whenThereIsNoTransactionIdInTheRequestAnErrorIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("4", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "");
        httpHeaders.add("taskId", "2");


        mvc.perform(delete("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Transaction ID is blank]"))
                .andReturn();

    }


}
