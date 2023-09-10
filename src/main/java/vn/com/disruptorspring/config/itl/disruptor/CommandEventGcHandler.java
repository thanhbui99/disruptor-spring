package vn.com.disruptorspring.config.itl.disruptor;


import com.lmax.disruptor.EventHandler;
import vn.com.disruptorspring.config.itl.Command;

/**
 * GC handler for DbCommandEvent
 */
public class CommandEventGcHandler<T extends Command> implements EventHandler<CommandEvent<T>> {

    @Override
    public void onEvent(CommandEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.clearForGc();
    }
}
