package vn.com.disruptorspring.memdb;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * merchandise
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

    private static final long serialVersionUID = -873268150277605569L;
    /**
     * ID
     */
    private final Long id;

    /**
     * amount
     */
    private int amount;

    /**
     * inventory
     */
    private Integer inventory;

    /**
     * sum
     */
    private int sum;


    public Item(Long id, int inventory) {
        this.id = id;
        this.inventory = inventory;
    }

    public Item(Long id) {
        this.id = id;
    }


    /**
     * Reduce inventory，If stock is low，The deduction fails
     *
     * @return
     */
    public boolean decreaseAmount() {

        if (!hasRemaining()) {
            return false;
        }
        inventory--;
        return true;

    }

    /**
     * Is it still in stock?
     *
     * @return
     */
    public boolean hasRemaining() {
        return inventory > 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("amount", amount)
                .append("inventory", inventory)
                .toString();
    }

}
