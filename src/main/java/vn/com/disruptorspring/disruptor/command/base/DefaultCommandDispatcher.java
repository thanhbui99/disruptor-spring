package vn.com.disruptorspring.disruptor.command.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by disruptorspring
 */
public class DefaultCommandDispatcher implements CommandDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCommandDispatcher.class);

    private final Map<Class, CommandProcessor> commandProcessorMap = new HashMap<>();

    @Override
    public void dispatch(Command command) {
        commandProcessorMap.get(command.getClass()).process(command);
    }

    @Override
    public void registerCommandProcessor(CommandProcessor commandProcessor) {
        LOGGER.info("Register CommandDispatcher: [{}] for [{}]", commandProcessor.getClass().getName(), commandProcessor.getMatchClass().getName());
        this.commandProcessorMap.put(commandProcessor.getMatchClass(), commandProcessor);
    }
}
