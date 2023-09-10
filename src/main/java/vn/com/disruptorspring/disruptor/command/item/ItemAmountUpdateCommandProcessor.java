package vn.com.disruptorspring.disruptor.command.item;


import vn.com.disruptorspring.disruptor.command.base.CommandProcessor;
import vn.com.disruptorspring.disruptor.command.base.CommandEventProducer;

public class ItemAmountUpdateCommandProcessor implements CommandProcessor<ItemAmountUpdateCommand> {

    private final CommandEventProducer<ItemAmountUpdateCommand>[] commandEventProducerList;

    private final int producerCount;

    public ItemAmountUpdateCommandProcessor(
            CommandEventProducer<ItemAmountUpdateCommand>[] commandEventProducerList) {
        this.commandEventProducerList = commandEventProducerList;
        this.producerCount = commandEventProducerList.length;
    }

    @Override
    public Class<ItemAmountUpdateCommand> getMatchClass() {
        return ItemAmountUpdateCommand.class;
    }

    @Override
    public void process(ItemAmountUpdateCommand command) {

        int index = (int) (command.getItemId() % (long) this.producerCount);
        commandEventProducerList[index].onData(command);

    }

}
