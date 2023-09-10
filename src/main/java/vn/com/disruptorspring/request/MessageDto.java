package vn.com.disruptorspring.request;

import vn.com.disruptorspring.utilities.StrongUuidGenerator;

import java.io.Serializable;

public abstract class MessageDto implements Serializable {

    private static final long serialVersionUID = 9003442515985424079L;
    /**
     * It should be guaranteed to be globally unique, Use uuid
     */
    protected final String id;

    public MessageDto() {
        this.id = StrongUuidGenerator.getNextId();
    }

    public String getId() {
        return id;
    }

}
