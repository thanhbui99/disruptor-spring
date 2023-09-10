package vn.com.disruptorspring.disruptor.command.order;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.disruptorspring.disruptor.command.base.CommandBuffer;
import vn.com.disruptorspring.disruptor.command.base.CommandBufferOverflowException;

import java.util.ArrayList;
import java.util.List;

public class OrderInsertCommandBuffer implements CommandBuffer<OrderInsertCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInsertCommandBuffer.class);

    private final List<OrderInsertCommand> commandList;

    private final int capacity;

    public OrderInsertCommandBuffer(int capacity) {
        this.capacity = capacity;
        this.commandList = new ArrayList<>(capacity);
    }

    @Override
    public boolean hasRemaining() {
        return commandList.size() < this.capacity;
    }


    /**
     *
     * @param command
     */
    @Override
    public void put(OrderInsertCommand command) {
        if (!hasRemaining()) {
            System.out.println("thanhbnz");
            throw new CommandBufferOverflowException();
        }

        this.commandList.add(command);
//        LOGGER.info("Put Order {}", JSON.toJson(command));

    }

    @Override
    public void clear() {
        commandList.clear();
    }

    @Override
    public List<OrderInsertCommand> get() {
        return new ArrayList<>(commandList);
    }

}
