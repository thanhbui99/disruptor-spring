package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventHandler;

/**
 * GC handler for RequestDtoEvent
 */
public class RequestDtoEventGcHandler implements EventHandler<RequestDtoEvent> {

    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.clearForGc();
    }
}
