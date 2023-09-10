package vn.com.disruptorspring.config.itl.disruptor;


import vn.com.disruptorspring.config.itl.Command;

public class CommandEvent<T extends Command> {

    private T command;

    public T getCommand() {
        return command;
    }

    public void setCommand(T command) {
        this.command = command;
    }

    public void clearForGc() {
        this.command = null;
    }

}
