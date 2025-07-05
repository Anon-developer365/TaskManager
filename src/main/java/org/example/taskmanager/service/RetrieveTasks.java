package org.example.taskmanager.service;

import org.example.taskmanager.pojo.Task;
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


    private final TaskRepository taskRepository;

    @Autowired
    public RetrieveTasks(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    /**
     * Method to retrieve all task data from the database.
     *
     * @return a list of tasks within the database.
     */
    public TaskResponse getAllTasks() {
        TaskResponse taskResponse = new TaskResponse();
        List <Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            Date date = java.util.Date
                    .from(task.getDueDate().atZone(ZoneId.systemDefault())
                            .toInstant());
            uk.gov.hmcts.taskmanager.domain.Task aTask = new uk.gov.hmcts.taskmanager.domain.Task();
            aTask.setTaskDescription(task.getDescription());
            aTask.setStatus(task.getStatus());
            aTask.setId(task.getId());
            aTask.setDueDate(date);
            aTask.setTitle(task.getTitle());
            taskResponse.addTasksItem(aTask);
        }
        return taskResponse;

    }
}
