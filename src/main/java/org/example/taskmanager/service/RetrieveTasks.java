package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
import org.example.taskmanager.validation.ValidationOrchestration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.taskmanager.domain.TaskResponse;

import java.util.List;

/**
 * Class to access the database and retrieve all tasks.
 */
@Service
public class RetrieveTasks {

    /**
     * Validation service.
     */
    private final ValidationOrchestration validationOrchestration;

    /**
     * Task repository for service.
     */
    private final TaskRepository taskRepository;

    /**
     * Autowired constructor for task repository.
     *
     * @param aTaskRepository task repository.
     * @param aValidationOrchestration validation service.
     */
    @Autowired
    public RetrieveTasks(final TaskRepository aTaskRepository,
                         final ValidationOrchestration
                                 aValidationOrchestration) {
        this.taskRepository = aTaskRepository;
        this.validationOrchestration = aValidationOrchestration;
    }


    /**
     * Method to retrieve all task data from the database.
     *
     * @param transactionId ID of the transaction.
     * @return a list of tasks within the database.
     */
    public TaskResponse getAllTasks(final String transactionId) {
        validationOrchestration.getAllTaskValidation(transactionId);
        return getTasks();

    }

    /**
     * Method to retrieve all tasks in the database.
     *
     * @return Task Response with the details of all the tasks in the database.
     */
    private TaskResponse getTasks() {
        TaskResponse taskResponse = new TaskResponse();
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            uk.gov.hmcts.taskmanager.domain.Task aTask
                    = new uk.gov.hmcts.taskmanager.domain.Task();
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
