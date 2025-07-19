package org.example.taskmanager.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateCheckerTests {
    private DateChecker dateChecker;

    @ParameterizedTest
    @ValueSource(strings = {"2025-01-01 12:00", "2025-04-18 01:00",
            "2025-04-21 02:00", "2025-05-05 03:00", "2025-05-26 07:00",
    "2025-08-25 10:00", "2025-12-25 14:00", "2025-12-26 17:00",
            "2026-01-01 12:00", "2026-04-06 12:00", "2026-05-25 12:00"})
    void whenADateIsABankHolidayTrueIsReturned(String stringDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime date = LocalDateTime.parse(stringDate, dateTimeFormatter);

        dateChecker = new DateChecker();
        boolean check = dateChecker.checkDateIsBankHoliday(date);

        assertTrue(check);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2025-01-11 12:00", "2025-01-12 01:00",
            "2025-03-15 02:00", "2025-03-22 03:00", "2025-10-25 07:00",
            "2025-08-23 10:00", "2025-12-13 14:00", "2025-12-14 17:00",
            "2026-01-10 12:00", "2026-04-11 12:00", "2026-05-16 12:00"})
    void whenADateIsAWeekendTrueIsReturned(String stringDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime date = LocalDateTime.parse(stringDate, dateTimeFormatter);

        dateChecker = new DateChecker();
        boolean check = dateChecker.checkDateIsWeekend(date);

        assertTrue(check);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2025-01-15 12:00", "2025-01-16 01:00",
            "2025-03-18 02:00", "2025-03-26 03:00", "2025-10-28 07:00",
            "2025-08-27 10:00", "2025-12-19 14:00", "2025-12-22 17:00",
            "2026-01-15 12:00", "2026-04-20 12:00", "2026-05-19 12:00"})
    void whenADateIsNotAWeekendOrABankHolidayFalseIsReturned(String stringDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.UK);
        LocalDateTime date = LocalDateTime.parse(stringDate, dateTimeFormatter);

        dateChecker = new DateChecker();
        boolean check = dateChecker.checkDateIsWeekend(date);

        assertFalse(check);
    }
}
