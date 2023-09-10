package vn.com.disruptorspring.config.itl.disruptor;


import com.lmax.disruptor.EventFactory;
import vn.com.disruptorspring.config.itl.Command;

public class CommandEventFactory<T extends Command> implements EventFactory<CommandEvent<T>> {
    @Override
    public CommandEvent<T> newInstance() {
        return new CommandEvent<>();
    }
}
