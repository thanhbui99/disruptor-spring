package vn.com.disruptorspring.config.itl.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import vn.com.disruptorspring.config.itl.Command;


public class CommandEventProducer<T extends Command> {

    private final EventTranslatorOneArg<CommandEvent, T> TRANSLATOR = (event, sequence, command) -> event.setCommand(command);

    private final RingBuffer<CommandEvent<T>> ringBuffer;

    public CommandEventProducer(RingBuffer<CommandEvent<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(T command) {
        ringBuffer.publishEvent((EventTranslatorOneArg) TRANSLATOR, command);
    }
}
