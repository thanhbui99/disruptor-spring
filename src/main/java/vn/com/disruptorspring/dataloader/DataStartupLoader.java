package vn.com.disruptorspring.dataloader;

import org.springframework.context.SmartLifecycle;

/**
 * Data loader on startup
 */
public abstract class DataStartupLoader implements SmartLifecycle {

    private volatile boolean running = false;

    private final Object monitor = new Object();

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
        synchronized (this.monitor) {
            if (!this.running) {
                doLoad();
            }
            this.running = true;
        }

    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    protected abstract void doLoad();

}
