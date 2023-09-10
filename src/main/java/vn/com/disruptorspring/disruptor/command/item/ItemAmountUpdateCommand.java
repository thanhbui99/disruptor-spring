package vn.com.disruptorspring.disruptor.command.item;


import org.apache.commons.lang3.builder.ToStringBuilder;
import vn.com.disruptorspring.disruptor.command.base.Command;

import java.util.Objects;

/**
 * Command to update product inventory
 */
public class ItemAmountUpdateCommand extends Command {

    private static final long serialVersionUID = 7896607558242859910L;
    private final Long itemId;

    private final int inventory;

    /**
     * @param requestId requestId of Command source
     * @param itemId    Product ID
     * @param inventory    in stock
     */
    public ItemAmountUpdateCommand(String requestId, Long itemId, int inventory) {
        super(requestId);
        this.itemId = itemId;
        this.inventory = inventory;
    }

    public Long getItemId() {
        return itemId;
    }

    public int getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemAmountUpdateCommand that = (ItemAmountUpdateCommand) o;

        if (inventory != that.inventory) return false;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + inventory;
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("requestId", requestId)
                .append("itemId", itemId)
                .append("inventory", inventory)
                .toString();
    }

}
