package vn.com.disruptorspring.disruptor.command.order;


import vn.com.disruptorspring.disruptor.command.base.CommandProcessor;
import vn.com.disruptorspring.disruptor.command.base.CommandEventProducer;

public class OrderInsertCommandProcessor implements CommandProcessor<OrderInsertCommand> {

    private final CommandEventProducer<OrderInsertCommand>[] commandEventProducerList;

    private final int producerCount;

    public OrderInsertCommandProcessor(CommandEventProducer<OrderInsertCommand>[] commandEventProducerList) {
        this.commandEventProducerList = commandEventProducerList;
        this.producerCount = commandEventProducerList.length;
    }

    @Override
    public Class<OrderInsertCommand> getMatchClass() {
        return OrderInsertCommand.class;
    }

    @Override
    public void process(OrderInsertCommand command) {
        // Remove mold based on product ID. Loại bỏ based dựa trên product ID
        int index = (int) (command.getItemId() % (long) this.producerCount);
        commandEventProducerList[index].onData(command);

    }
}
