package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Output the result of RequestDtoEvent to the handler of jms
 */
public class RequestDtoEventJmsOutputer implements EventHandler<RequestDtoEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDtoEventJmsOutputer.class);


    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) throws Exception {

        ResponseDto responseDto = event.getResponseDto();
        if (responseDto == null) {
            return;
        }
//    LOGGER.info("Send Response for Request {}. Id: {}", responseDto.getRequestId(), responseDto.getId());
        // There is no problem in doing this here, because the actual caller is called from a single thread, but concurrency problems will occur under multi-threads.
    }
}
