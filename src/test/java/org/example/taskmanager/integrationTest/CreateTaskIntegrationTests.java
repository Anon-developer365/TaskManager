package org.example.taskmanager.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanager.TaskManagerApplication;
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
import uk.gov.hmcts.taskmanager.domain.CreateTaskRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
public class CreateTaskIntegrationTests {

    private final TaskRepository taskRepository;

    private final MockMvc mvc;

    private final ObjectMapper objectMapper;


    @Autowired
    public CreateTaskIntegrationTests(WebApplicationContext webApplicationContext, ObjectMapper objectMapper,
                                      TaskRepository taskRepository) {
        this.objectMapper = objectMapper;
        this.taskRepository = taskRepository;
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void reset() {
        taskRepository.deleteAll();
    }
    @Test
    void whenAllDetailsAreValidATaskIsSavedInTheDatabase() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setTitle("Title");
        taskRequest.setStatus("Status");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Task Created Successfully"))
                .andReturn();
    }

    @Test
    void whenTaskStatusIsMissingAnErrorMessageIsReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setTitle("Title");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task status is blank]"))
                .andReturn();
    }

    @Test
    void whenTaskTitleIsMissingAnErrorMessageIsReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setStatus("Status");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task title is blank]"))
                .andReturn();
    }

    @Test
    void whenTaskDueDateIsMissingAnErrorMessageIsReturned() throws Exception {
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setStatus("Status");
        taskRequest.setTitle("Title");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task due date is Blank]"))
                .andReturn();
    }

    @Test
    void whenTaskDescriptionIsEmptyTheTaskIsSavedInTheDatabase() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTitle("Title");
        taskRequest.setStatus("Status");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Task Created Successfully"))
                .andReturn();
    }

    @Test
    void whenTaskTitleIsInAnIncorrectFormatAnErrorMessageIsReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setStatus("Status");
        taskRequest.setTitle("##+^&");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task title does not match the pattern a-zA-Z0-9]"))
                .andReturn();
    }

    @Test
    void whenTaskStatusIsInAnIncorrectFormatAnErrorMessageIsReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("Description");
        taskRequest.setStatus("##+^&");
        taskRequest.setTitle("Title");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task status does not match the pattern a-zA-Z0-9]"))
                .andReturn();
    }

    @Test
    void whenTaskDescriptionIsInAnIncorrectFormatAnErrorMessageIsReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("##+^&");
        taskRequest.setStatus("Status");
        taskRequest.setTitle("Title");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task description does not match the pattern a-zA-Z0-9]"))
                .andReturn();
    }

    @Test
    void whenThereIsMoreThanOneErrorAllErrorsAreReturned() throws Exception {
        String stringDate = "2025-05-15 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        CreateTaskRequest taskRequest = new CreateTaskRequest();
        taskRequest.setTaskDescription("##+^&");
        taskRequest.setStatus("");
        taskRequest.setTitle("");
        taskRequest.setDueDate(dueDate);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(taskRequest);

        mvc.perform(post("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("[Task title is blank, Task description does not match the pattern a-zA-Z0-9, Task status is blank]"))
                .andReturn();
    }
}
