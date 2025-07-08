package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.DeleteTask;
import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.Task;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest
public class TaskManagementSystemControllerTest {

    @Autowired
    private final WebApplicationContext context;

    @Mock
    private CreateTaskOrchestration createTaskOrchestration;

    @Mock
    private RetrieveTasks getAllTasks;

    @Mock
    private GetATask getATask;

    @Mock
    private UpdateStatusOrchestration updateStatusOrchestration;

    @Mock
    private DeleteTask deleteTask;

    @InjectMocks
    private TaskManagementSystemController taskController;

    public TaskManagementSystemControllerTest(WebApplicationContext context) {
        this.context = context;
    }

    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() throws ParseException {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        final UUID uuid = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");
        System.out.println(date);

        Task task = new Task();
        task.setId(uuid.toString());
        task.setTaskDescription("description");
        task.setStatus("status");
        task.setDueDate(date);
        when(getATask.getATask(uuid.toString())).thenReturn(task);
        ResponseEntity<Task> output = taskController.getTask("1", uuid.toString());
        assert output != null;
        assert Objects.equals(output.getBody().getId(), uuid.toString());

    }

    @Test
    void whenAPostRequestIsSentAnErrorIsReceived() throws Exception {
        MockMvc mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        mvc.perform(post("/getTask")).andExpect(status().is4xxClientError());
    }

    @Test
    void ifTheIdDoesNotExistAMessageIsReturnedToTheConsumer() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        final UUID uuid = UUID.randomUUID();
        when(getATask.getATask(uuid.toString())).thenThrow(new RuntimeException("Task with ID " + uuid + " not found"));

        String expectedError = "Task with ID " + uuid + " not found";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> taskController.getTask("2", uuid.toString()));
        assertEquals(expectedError, thrown.getMessage());
    }

    @Test
    void whenAnEmptyListIsReceivedBackFromTheServiceThisIsReturned() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        TaskResponse taskResponse = new TaskResponse();
        when(getAllTasks.getAllTasks()).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertNull(actual.getBody().getTasks());

    }

    @Test
    void whenAListWithATaskIsReceivedBackFromTheServiceThisIsReturned() throws ParseException {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        UUID id = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");

        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        when(getAllTasks.getAllTasks()).thenReturn(expected);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertEquals(expected.getTasks().size(), actual.getBody().getTasks().size());

    }

    @Test
    void whenAListWithMoreThanOneTaskIsReceivedBackFromTheServiceAllItemsAreReturned() throws ParseException {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = dateFormat.parse("2025-05-05 17:00");
        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        uk.gov.hmcts.taskmanager.domain.Task task2 = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id2.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(date);
        expected.addTasksItem(task2);
        when(getAllTasks.getAllTasks()).thenReturn(expected);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertEquals(expected.getTasks().size(), actual.getBody().getTasks().size());
    }

    @Test
    void checkWhenATaskIsDeletedASuccessMessageIsReceived(){
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);
        SuccessResponse response = new SuccessResponse();
        response.setId("1");
        response.setMessage("Task 1 deleted.");
        when(deleteTask.deleteTask("1")).thenReturn(response);
        ResponseEntity<SuccessResponse> output = taskController.deleteTask("2", "1");
        assertEquals("1", output.getBody().getId());
        assertEquals(response.getMessage(), output.getBody().getMessage());

    }

    @Test
    void checkWhenATaskIdCanNotBeFoundAnErrorMessageIsReturned(){
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, deleteTask);

        TaskValidationErrorException exception = new TaskValidationErrorException("No task found with that ID 1");
        when(deleteTask.deleteTask("1")).thenThrow(exception);
        TaskValidationErrorException actualException = assertThrows(TaskValidationErrorException.class, () -> taskController.deleteTask("2", "1"));
        assertEquals(exception.getMessage(), actualException.getMessage());
    }
}

