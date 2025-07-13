package org.example.taskmanager.validation;

import org.example.taskmanager.exceptions.TaskValidationErrorException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IdValidationTests {

    private IdValidation IdValidation;
/**
    @Test
    void whenTaskIdContainsOneCharacterNoErrorIsThrown() {
        IdValidation= new IdValidation();
        assertDoesNotThrow(() -> IdValidation.validateId("Task", "2"));
    }

    @Test
    void whenTaskIdContainsLettersAndNumbersCharacterNoErrorIsThrown() {
        IdValidation= new IdValidation();
        assertDoesNotThrow(() -> IdValidation.validateId("Task","2cff49fh5"));
    }

    @Test
    void whenTaskIdIsLongerThanTheLimitAnErrorIsThrown() {
        IdValidation= new IdValidation();
        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, ()
                -> IdValidation.validateId("Task", "2cff4vd7fysfrhwgyq3r8euqcnt48gynhfwaiygvbrughqcihvsfeb9fh5"));

    }

    @Test
    void whenTaskIdIsEmptyAnErrorIsThrown() {
        IdValidation= new IdValidation();
        TaskValidationErrorException thrown = assertThrows(TaskValidationErrorException.class, ()
                -> IdValidation.validateId("Task", ""));

    }
**/
}

