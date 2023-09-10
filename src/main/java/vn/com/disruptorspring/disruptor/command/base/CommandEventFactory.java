package vn.com.disruptorspring.disruptor.command.base;


import com.lmax.disruptor.EventFactory;

public class CommandEventFactory<T extends Command> implements EventFactory<CommandEvent<T>> {
    @Override
    public CommandEvent<T> newInstance() {
        return new CommandEvent<>();
    }
}
