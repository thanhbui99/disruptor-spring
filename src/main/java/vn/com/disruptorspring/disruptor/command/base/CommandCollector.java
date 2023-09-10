package vn.com.disruptorspring.disruptor.command.base;


import java.util.ArrayList;
import java.util.List;

/**
 * Operation Command collector
 */
public class CommandCollector {

    private List<Command> commandList = new ArrayList<>(4);

    public List<Command> getCommandList() {
        return commandList;
    }

    public void addCommand(Command command) {
        commandList.add(command);
    }

}
