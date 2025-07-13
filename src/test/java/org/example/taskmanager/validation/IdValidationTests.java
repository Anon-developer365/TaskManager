package org.example.taskmanager.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IdValidationTests {

    private IdValidation IdValidation;

    @Test
    void whenTaskIdContainsOneCharacterNoErrorIsThrown() {
        IdValidation= new IdValidation();
        List<String> expectedOutput = new ArrayList<>();
        List<String> actualOutput = IdValidation.validateId("Task", "2");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void whenTaskIdContainsLettersAndNumbersCharacterNoErrorIsThrown() {
        IdValidation= new IdValidation();
        List<String> expectedOutput = new ArrayList<>();
        List<String> actualOutput = IdValidation.validateId("Task", "2cff49fh5");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void whenTaskIdIsLongerThanTheLimitAnErrorIsReturned() {
        IdValidation= new IdValidation();
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("Task ID does not match the pattern 0-9a-zA-Z-{1,10}");
        List<String> actualOutput = IdValidation.validateId("Task", "2cff4vd7fysfrhwgyq3r8euqcnt48gynhfwaiygvbrughqcihvsfeb9fh5");
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void whenTaskIdIsEmptyAnErrorMessageIsReturned() {
        IdValidation= new IdValidation();
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("Task ID is blank");
        List<String> actualOutput = IdValidation.validateId("Task", "");
        assertEquals(expectedOutput, actualOutput);

    }

}

