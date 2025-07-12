package org.example.taskmanager.exceptions;

import org.hibernate.service.spi.ServiceException;

/**
 * Super-class for Task Not Found Exception.
 */
public class TaskNotFoundException extends ServiceException {

    /**
     * Constructor for Task Not Found Exception.
     *
     * @param message the message.
     */
    public TaskNotFoundException(final String message) {
        super(message);
    }
}
