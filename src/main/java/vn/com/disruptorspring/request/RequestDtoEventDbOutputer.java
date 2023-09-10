package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventHandler;
import org.apache.commons.collections4.CollectionUtils;
import vn.com.disruptorspring.config.itl.Command;
import vn.com.disruptorspring.config.itl.CommandDispatcher;

import java.util.List;

/**
 * Output the result of RequestDtoEvent to the database
 */
public class RequestDtoEventDbOutputer implements EventHandler<RequestDtoEvent> {


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
