package vn.com.disruptorspring.disruptor.command.base;

import java.util.List;

public interface CommandBuffer<T extends Command> {

    /**
     * Is the Buffer full?
     *
     * @return
     */
    boolean hasRemaining();

    /**
     * Put in Command
     *
     * @param command
     */
    void put(T command);

    /**
     * Empty the cache
     */
    void clear();

    /**
     * get{@link Command}
     *
     * @return
     */
    List<T> get();

}
