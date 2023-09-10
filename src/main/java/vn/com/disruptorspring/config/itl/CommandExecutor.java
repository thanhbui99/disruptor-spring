package vn.com.disruptorspring.config.itl;

/**
 * {@link Command}Actuator
 */
public interface CommandExecutor<T extends CommandBuffer> {

    void execute(T commandBuffer);
}
