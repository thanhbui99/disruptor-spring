package vn.com.disruptorspring.lifecycle;

import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

public class DisruptorLifeCycleContainer<T> implements SmartLifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisruptorLifeCycleContainer.class);

    private volatile boolean running = false;

    private final String disruptorName;
    private final Disruptor<T> disruptor;
    private final int phase;

    public DisruptorLifeCycleContainer(String disruptorName, Disruptor<T> disruptor, int phase) {
        this.disruptorName = disruptorName;
        this.disruptor = disruptor;
        this.phase = phase;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public void start() {
        LOGGER.info("Starting disruptor [{}]", this.disruptorName);
        disruptor.start();
        this.running = true;
    }

    @Override
    public void stop() {
        LOGGER.info("Shutdown disruptor [{}]", this.disruptorName);
        disruptor.shutdown();
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPhase() {
        return this.phase;
    }
}
