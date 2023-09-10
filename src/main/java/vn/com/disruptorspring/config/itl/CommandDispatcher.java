package vn.com.disruptorspring.config.itl;

/**
 * Database command dispatcher, assigned to{@link CommandProcessor}
 */
public interface CommandDispatcher {

    void dispatch(Command command);

    void registerCommandProcessor(CommandProcessor commandProcessor);
}
