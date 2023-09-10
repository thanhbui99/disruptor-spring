package vn.com.disruptorspring.disruptor.command.base;

/**
 * {@link Command}Actuator
 */
public interface CommandExecutor<T extends CommandBuffer> {

    void execute(T commandBuffer);
}
