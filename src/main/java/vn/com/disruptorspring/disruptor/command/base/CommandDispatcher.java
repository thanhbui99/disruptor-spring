package vn.com.disruptorspring.disruptor.command.base;

/**
 * Command dispatcher, assigned to{@link CommandProcessor}
 */
public interface CommandDispatcher {

    void dispatch(Command command);

    void registerCommandProcessor(CommandProcessor commandProcessor);
}
