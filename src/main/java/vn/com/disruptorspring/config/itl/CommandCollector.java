package vn.com.disruptorspring.config.itl;


import java.util.ArrayList;
import java.util.List;

/**
 * Database operation Command collector
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
