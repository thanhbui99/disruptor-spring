package vn.com.disruptorspring.disruptor.command.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command buffer overflow exception
 */
public class CommandBufferOverflowException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandBufferOverflowException.class);
    private static final long serialVersionUID = -408555980971903979L;

    /**
     * Constructs an instance of this class.
     */
    public CommandBufferOverflowException() {
        LOGGER.error("CommandBufferOverflowException");
    }
}
