package org.example.taskmanager.validation;

import java.time.LocalDateTime;


import com.google.gson.Gson;
import org.example.taskmanager.pojo.PublicHoliday;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Date checker class to check if due date
 * selected is a weekend or a bank holiday.
 */
@Service
public class DateChecker {

    /**
     * Method to check if date is a bank holiday in the UK.
     *
     * @param date date to be checked.
     * @return true if date is a bank holiday.
     */
    public boolean checkDateIsBankHoliday(final LocalDateTime date) {

        String dateString = String.valueOf(date.getYear());
        boolean check = false;
        String json = new RestTemplate().getForObject(
                "https://date.nager.at/api/v3/publicholidays/" + dateString
                        + "/GB", String.class);

        Gson gson = new Gson();
        String[] dueDate = date.toString().split("T");
        PublicHoliday[] userArray = gson.fromJson(json, PublicHoliday[].class);
        for (PublicHoliday publicHoliday : userArray) {
            if (publicHoliday.getDate().equalsIgnoreCase(dueDate[0])) {
                check = true;
                break;
            }
        }
        return check;
    }

    /**
     * Method to check if date is a weekend.
     *
     * @param date date to be checked.
     * @return true if date is a weekend.
     */
    public boolean checkDateIsWeekend(final LocalDateTime date) {
        return date.getDayOfWeek().toString().equalsIgnoreCase("SATURDAY")
                || date.getDayOfWeek().toString().equalsIgnoreCase("SUNDAY");
    }
}
