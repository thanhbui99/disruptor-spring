package vn.com.disruptorspring.disruptor.handle;

import com.lmax.disruptor.EventHandler;
import org.apache.commons.collections4.CollectionUtils;
import vn.com.disruptorspring.disruptor.command.base.Command;
import vn.com.disruptorspring.disruptor.command.base.CommandDispatcher;
import vn.com.disruptorspring.model.request.RequestDtoEvent;

import java.util.List;

/**
 * Output the result of RequestDtoEvent to the database
 */
public class RequestDtoEventDbOutput implements EventHandler<RequestDtoEvent> {


    private CommandDispatcher commandDispatcher;

    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) {

        if (event.hasErrorOrException()) {
            return;
        }

        List<Command> commandList = event.getCommandCollector().getCommandList();
        if (CollectionUtils.isEmpty(commandList)) {
            return;
        }

        for (Command command : commandList) {
            commandDispatcher.dispatch(command);
        }

    }

    public void setCommandDispatcher(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }
}
