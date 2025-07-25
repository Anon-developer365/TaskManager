package org.example.taskmanager.controllers;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.example.taskmanager.service.GetATask;
import org.example.taskmanager.service.RetrieveTasks;
import org.example.taskmanager.service.TaskRemoval;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.gov.hmcts.taskmanager.domain.SuccessResponse;
import uk.gov.hmcts.taskmanager.domain.Task;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@SpringBootTest
public class TaskManagementSystemControllerTest {
    @Mock
    private CreateTaskOrchestration createTaskOrchestration;

    @Mock
    private RetrieveTasks getAllTasks;

    @Mock
    private GetATask getATask;

    @Mock
    private UpdateStatusOrchestration updateStatusOrchestration;

    @Mock
    private TaskRemoval taskRemoval;

    @InjectMocks
    private TaskManagementSystemController taskController;

    @Test
    void aSuccessMessageIsReceivedWhenDetailsAreProvided() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        final UUID uuid = UUID.randomUUID();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        Task task = new Task();
        task.setId(uuid.toString());
        task.setTaskDescription("description");
        task.setStatus("status");
        task.setDueDate(dueDate);
        when(getATask.getATask("2", uuid.toString())).thenReturn(task);
        ResponseEntity<Task> output = taskController.getTask("2", uuid.toString());
        assert output != null;
        assert Objects.equals(output.getBody().getId(), uuid.toString());

    }

    @Test
    void ifTheIdDoesNotExistAMessageIsReturnedToTheConsumer() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        final UUID uuid = UUID.randomUUID();
        when(getATask.getATask("2", uuid.toString())).thenThrow(new RuntimeException("Task with ID " + uuid + " not found"));

        String expectedError = "Task with ID " + uuid + " not found";
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> taskController.getTask("2", uuid.toString()));
        assertEquals(expectedError, thrown.getMessage());
    }

    @Test
    void whenAnEmptyListIsReceivedBackFromTheServiceThisIsReturned() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        TaskResponse taskResponse = new TaskResponse();
        when(getAllTasks.getAllTasks("2")).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertNull(actual.getBody().getTasks());

    }

    @Test
    void whenAListWithATaskIsReceivedBackFromTheServiceThisIsReturned() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        UUID id = UUID.randomUUID();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(dueDate);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        when(getAllTasks.getAllTasks("2")).thenReturn(expected);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertEquals(expected.getTasks().size(), actual.getBody().getTasks().size());

    }

    @Test
    void whenAListWithMoreThanOneTaskIsReceivedBackFromTheServiceAllItemsAreReturned() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        String stringDate = "2025-05-05 17:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime dueDate = LocalDateTime.parse(stringDate, dateTimeFormatter);

        uk.gov.hmcts.taskmanager.domain.Task task = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(dueDate);
        TaskResponse expected = new TaskResponse();
        expected.addTasksItem(task);
        uk.gov.hmcts.taskmanager.domain.Task task2 = new uk.gov.hmcts.taskmanager.domain.Task();
        task.setTaskDescription("description");
        task.setId(id2.toString());
        task.setStatus("status");
        task.setTitle("title");
        task.setDueDate(dueDate);
        expected.addTasksItem(task2);
        when(getAllTasks.getAllTasks("2")).thenReturn(expected);

        ResponseEntity<TaskResponse> actual = taskController.getTasks("2");
        assertEquals(expected.getTasks().size(), actual.getBody().getTasks().size());
    }

    @Test
    void checkWhenATaskIsDeletedASuccessMessageIsReceived() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);
        SuccessResponse response = new SuccessResponse();
        response.setId("1");
        response.setMessage("Task 1 deleted.");
        when(taskRemoval.taskRemoval("2","1")).thenReturn(response);
        ResponseEntity<SuccessResponse> output = taskController.deleteTask("2", "1");
        assertEquals("1", output.getBody().getId());
        assertEquals(response.getMessage(), output.getBody().getMessage());

    }

    @Test
    void checkWhenATaskIdCanNotBeFoundAnErrorMessageIsReturned() {
        taskController = new TaskManagementSystemController(createTaskOrchestration, getATask, getAllTasks, updateStatusOrchestration, taskRemoval);

        TaskValidationErrorException exception = new TaskValidationErrorException("No task found with that ID 1");
        when(taskRemoval.taskRemoval("2", "1")).thenThrow(exception);
        TaskValidationErrorException actualException = assertThrows(TaskValidationErrorException.class, () -> taskController.deleteTask("2", "1"));
        assertEquals(exception.getMessage(), actualException.getMessage());
    }
}

