package vn.com.disruptorspring.disruptor.handle;

import com.lmax.disruptor.EventHandler;
import vn.com.disruptorspring.model.request.RequestDtoEvent;

/**
 * GC handler for RequestDtoEvent
 */
public class RequestDtoEventGcHandler implements EventHandler<RequestDtoEvent> {

    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.clearForGc();
    }
}
