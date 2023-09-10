package vn.com.disruptorspring.config.itl;

/**
 * {@link Command}processor
 */
public interface CommandProcessor<T extends Command> {

    Class<T> getMatchClass();

    void process(T command);
}
