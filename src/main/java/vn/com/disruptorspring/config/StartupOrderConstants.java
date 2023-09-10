package vn.com.disruptorspring.config;

import org.springframework.jca.endpoint.GenericMessageEndpointManager;

/**
 * The order in which data is refreshed when the program starts <br>
 * The smaller the number, the higher it is <br>
 * The last one is also earlier than JMS initialization. See {@link GenericMessageEndpointManager} <br>
 */
public abstract class StartupOrderConstants {

    public static final int DISRUPTOR_REQUEST_DTO = 1;
    public static final int DISRUPTOR_ORDER_INSERT = 2;
    public static final int DISRUPTOR_ITEM_UPDATE = 3;

    private StartupOrderConstants() {
        // singleton
    }

}
