package vn.com.disruptorspring.disruptor.command.base;


import com.lmax.disruptor.EventHandler;

/**
 * GC handler for CommandEvent
 */
public class CommandEventGcHandler<T extends Command> implements EventHandler<CommandEvent<T>> {

    @Override
    public void onEvent(CommandEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.clearForGc();
    }
}
