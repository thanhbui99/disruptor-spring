package vn.com.disruptorspring.config.item;


import vn.com.disruptorspring.config.itl.CommandProcessor;
import vn.com.disruptorspring.config.itl.disruptor.CommandEventProducer;

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
