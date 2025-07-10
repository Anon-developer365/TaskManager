package org.example.taskmanager;

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
public class GetATaskIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void whenTheIsATaskInTheDatabaseAndTheIdMatchesThisIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("2", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "2");


        mvc.perform(get("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
              .andExpect(status().isOk())
              .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id").value("2"))
              .andReturn();

    }

    @Test
    void whenTheIsATaskInTheDatabaseAndTheIdDoeNotMatchAnErrorIsReturned() throws Exception {
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("4", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "2");


        mvc.perform(get("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Task with ID 2 not found]"))
                .andReturn();
    }

    @Test
    void whenTheIsNoTaskInTheDatabaseAndTheIdDoeNotMatchAnErrorIsReturned() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        httpHeaders.add("taskId", "2");


        mvc.perform(get("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Task with ID 2 not found]"))
                .andReturn();
    }

    @Test
    void whenTheIsNoTaskIdInTheRequestAnErrorIsReturned() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "2");
        httpHeaders.add("taskId", "");


        mvc.perform(get("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Task ID is blank]"))
                .andReturn();
    }

    @Test
    void whenTheTaskIdIsInTheIncorrectFormatAnErrorIsReturned() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "2");
        httpHeaders.add("taskId", "dad9vu8ew4tqrguwr0-94tijrvkgjiu");


        mvc.perform(get("/Task").contentType(MediaType.APPLICATION_JSON).headers(httpHeaders))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Task ID does not match the pattern 0-9a-zA-Z-{1,10}]"))
                .andReturn();
    }
}
//test transaction id is missing from headers.
//test format of transaction id.