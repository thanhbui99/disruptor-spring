package vn.com.disruptorspring.config.item;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.disruptorspring.config.itl.CommandBuffer;
import vn.com.disruptorspring.config.itl.CommandBufferOverflowException;
import vn.com.disruptorspring.utilities.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link ItemAmountUpdateCommand}Buffer <br>
 * Internal storage is a{@link ItemAmountUpdateCommand}Map for Key <br>
 * The advantage of doing this is, If there are multiple ones with the same Key{@link ItemAmountUpdateCommand}, Then only the last one will be recorded<br>
 * Thus reducing the number of Sql statements
 */
public class ItemAmountUpdateCommandBuffer implements CommandBuffer<ItemAmountUpdateCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAmountUpdateCommandBuffer.class);

    private final Map<Long, ItemAmountUpdateCommand> commandMap = new ConcurrentHashMap<>();

    private final int capacity;

    public ItemAmountUpdateCommandBuffer(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean hasRemaining() {
        return commandMap.size() < this.capacity;
    }

    /**
     * @param command
     * @throws CommandBufferOverflowException
     */
    @Override
    public void put(ItemAmountUpdateCommand command) {

        Long key = command.getItemId();
        if (!hasRemaining() && commandMap.get(key) == null) {
            throw new CommandBufferOverflowException();
        }

        ItemAmountUpdateCommand prevValue = this.commandMap.put(key, command);
        if (prevValue != null) {
//            LOGGER.info("Optimized {}", JSON.toJson(command));
        }
//        LOGGER.info("Put Item {}", JSON.toJson(command));

    }

    @Override
    public void clear() {
        commandMap.clear();
    }

    @Override
    public List<ItemAmountUpdateCommand> get() {
        return new ArrayList<>(commandMap.values());
    }

}
