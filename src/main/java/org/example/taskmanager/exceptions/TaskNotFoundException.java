package org.example.taskmanager.exceptions;

import org.hibernate.service.spi.ServiceException;

public class TaskNotFoundException extends ServiceException {

    /**
     * Constructor for Empty Task Exception.
     *
     * @param message the message.
     */
    public TaskNotFoundException(final String message) {
        super(message);
    }
}
