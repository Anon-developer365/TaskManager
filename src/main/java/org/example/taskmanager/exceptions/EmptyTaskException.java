package org.example.taskmanager.exceptions;

import org.hibernate.service.spi.ServiceException;

/**
 * Super-class for Empty Task Exception.
 */
public class EmptyTaskException extends ServiceException {

    /**
     * Constructor for Empty Task Exception.
     *
     * @param message the message.
     */
    public EmptyTaskException(final String message) {
        super(message);
    }

}
