package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.IdValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Class to access the database and retrieve all tasks.
 */
@Service
public class RetrieveTasks {

    private IdValidation idValidation;
    private final TaskRepository taskRepository;

    @Autowired
    public RetrieveTasks(TaskRepository taskRepository, IdValidation idValidation) {
        this.taskRepository = taskRepository;
        this.idValidation = idValidation;
    }


    /**
     * Method to retrieve all task data from the database.
     *
     * @return a list of tasks within the database.
     */
    public TaskResponse getAllTasks(String transactionId) {
        validate(transactionId);
        return getTasks();

    }

    private void validate(String transactionId) {
        idValidation.validateId("Transaction", transactionId);
    }

    private TaskResponse getTasks() {
        TaskResponse taskResponse = new TaskResponse();
        List <Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            uk.gov.hmcts.taskmanager.domain.Task aTask = new uk.gov.hmcts.taskmanager.domain.Task();
            aTask.setTaskDescription(task.getDescription());
            aTask.setStatus(task.getStatus());
            aTask.setId(task.getId());
            aTask.setDueDate(task.getDueDate());
            aTask.setTitle(task.getTitle());
            taskResponse.addTasksItem(aTask);
        }
        return taskResponse;
    }
}
