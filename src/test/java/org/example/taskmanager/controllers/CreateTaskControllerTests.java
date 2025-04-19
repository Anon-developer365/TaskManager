package org.example.taskmanager.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

public class CreateTaskControllerTests {

    private CreateTaskController createTaskController;

    @Test
    void aSuccessMessageIsReceivedWhenTheEndPointIsHit() {
        createTaskController = new CreateTaskController();
        String output = createTaskController.createTask();
        assert output != null;
        assert output.contains("Task Created");

    }
}
