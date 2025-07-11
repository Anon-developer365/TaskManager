package org.example.taskmanager.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanager.TaskManagerApplication;
import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.service.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
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
import uk.gov.hmcts.taskmanager.domain.UpdateStatusRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
public class UpdateStatusIntegrationTests {
    private final WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    private final TaskRepository taskRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public UpdateStatusIntegrationTests(TaskRepository taskRepository, WebApplicationContext webApplicationContext, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.webApplicationContext = webApplicationContext;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void checkAStatusIsUpdatedWhenTheIdIsFoundInTheDatabase() throws Exception {
        UpdateStatusRequest statusRequest = new UpdateStatusRequest();
        statusRequest.setId("2");
        statusRequest.setStatus("this is a new status");
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("2", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(statusRequest);

        mvc.perform(put("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Status updated to: \"this is a new status\""))
                .andReturn();

    }

    @Test
    void checkAnErrorIsReturnedIfTheIdIsNotInTheDatabase() throws Exception {
        UpdateStatusRequest statusRequest = new UpdateStatusRequest();
        statusRequest.setId("2");
        statusRequest.setStatus("this is a new status");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "1");
        String json = objectMapper.writeValueAsString(statusRequest);

        mvc.perform(put("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("Task with ID 2 not found"))
                .andReturn();
    }

    @Test
    void checkAnErrorIsReturnedIfTheTransactionIdIsEmpty() throws Exception {
        UpdateStatusRequest statusRequest = new UpdateStatusRequest();
        statusRequest.setId("2");
        statusRequest.setStatus("this is a new status");
        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);
        Task task = new Task("2", "title", "description", "status",dueDate);
        taskRepository.save(task);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("transactionId", "");
        String json = objectMapper.writeValueAsString(statusRequest);

        mvc.perform(put("/Task").contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders)
                        .content(json))
                .andExpect(status().is(400))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors").value("[Transaction ID is blank]"))
                .andReturn();
    }

}
