package org.example.taskmanager.exceptions;

import org.hibernate.service.spi.ServiceException;

/**
 * Super-class for Empty Task Exception.
 */
public class TaskValidationErrorException extends ServiceException {

    /**
     * Constructor for Task Validation Error Exception.
     *
     * @param message the message.
     */
    public TaskValidationErrorException(final String message) {
        super(message);
    }

}
