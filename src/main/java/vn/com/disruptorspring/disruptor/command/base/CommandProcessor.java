package vn.com.disruptorspring.disruptor.command.base;

/**
 * {@link Command}processor
 */
public interface CommandProcessor<T extends Command> {

    Class<T> getMatchClass();

    void process(T command);
}
