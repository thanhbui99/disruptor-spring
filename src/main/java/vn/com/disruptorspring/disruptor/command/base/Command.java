package vn.com.disruptorspring.disruptor.command.base;


import vn.com.disruptorspring.utilities.StrongUuidGenerator;

import java.io.Serializable;

/**
 * Operation commands
 */
public abstract class Command implements Serializable {

    private static final long serialVersionUID = -2463630580877588711L;
    protected final String id;

    protected final String requestId;

    /**
     * requestId of Command source
     *
     * @param requestId
     */
    public Command(String requestId) {
        this.id = StrongUuidGenerator.getNextId();
        this.requestId = requestId;
    }

    /**
     * Globally unique ID, uuid
     *
     * @return
     */
    public String getId() {
        return id;
    }


    /**
     * @return
     */
    public String getRequestId() {
        return requestId;
    }
}
