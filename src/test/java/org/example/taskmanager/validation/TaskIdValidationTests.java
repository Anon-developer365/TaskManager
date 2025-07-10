package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TaskIdValidationTests {

    private TaskIdValidation taskIdValidation;

    @Test
    void whenTaskIdContainsOneCharacterNoErrorIsThrown() {
        taskIdValidation= new TaskIdValidation();
        assertDoesNotThrow(() -> taskIdValidation.validateTaskId("2"));
    }

    @Test
    void whenTaskIdContainsLettersAndNumbersCharacterNoErrorIsThrown() {
        taskIdValidation= new TaskIdValidation();
        assertDoesNotThrow(() -> taskIdValidation.validateTaskId("2cff49fh5"));
    }

    @Test
    void whenTaskIdIsLongerThanTheLimitAnErrorIsThrown() {
        taskIdValidation= new TaskIdValidation();
        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, ()
                -> taskIdValidation.validateTaskId("2cff4vd7fysfrhwgyq3r8euqcnt48gynhfwaiygvbrughqcihvsfeb9fh5"));

    }

    @Test
    void whenTaskIdIsEmptyAnErrorIsThrown() {
        taskIdValidation= new TaskIdValidation();
        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, ()
                -> taskIdValidation.validateTaskId(""));

    }

}

