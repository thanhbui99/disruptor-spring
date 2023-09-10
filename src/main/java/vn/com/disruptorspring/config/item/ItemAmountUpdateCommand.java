package vn.com.disruptorspring.config.item;


import org.apache.commons.lang3.builder.ToStringBuilder;
import vn.com.disruptorspring.config.itl.Command;

import java.util.Objects;

/**
 * Command to update product inventory
 */
public class ItemAmountUpdateCommand extends Command {

    private static final long serialVersionUID = 7896607558242859910L;
    private final Long itemId;

    private final int amount;

    /**
     * @param requestId requestId of Command source
     * @param itemId    Product ID
     * @param amount    in stock
     */
    public ItemAmountUpdateCommand(String requestId, Long itemId, int amount) {
        super(requestId);
        this.itemId = itemId;
        this.amount = amount;
    }

    public Long getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemAmountUpdateCommand that = (ItemAmountUpdateCommand) o;

        if (amount != that.amount) return false;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("requestId", requestId)
                .append("itemId", itemId)
                .append("amount", amount)
                .toString();
    }

}
