package org.example.taskmanager.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.taskmanager.TaskManagerApplication;
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

    private final WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    public CreateTaskIntegrationTests(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void whenAllDetailsAreValidATaskIsSavedInTheDatabase() throws Exception {
        String stringDate = "2025-05-05 17:00";
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

}
