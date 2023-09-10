package vn.com.disruptorspring.config.order;

import vn.com.disruptorspring.config.itl.Command;

/**
 * Save order command
 */
public class OrderInsertCommand extends Command {

    private static final long serialVersionUID = -1844388054958673686L;
    private final Long itemId;

    private final String userId;

    /**
     * @param requestId requestId of Command source
     * @param itemId    Product ID
     * @param userId    User ID
     */
    public OrderInsertCommand(String requestId, Long itemId, String userId) {
        super(requestId);
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getUserId() {
        return userId;
    }

}
