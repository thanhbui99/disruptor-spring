package vn.com.disruptorspring.disruptor.handle;

import com.lmax.disruptor.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import vn.com.disruptorspring.model.response.ResponseDto;
import vn.com.disruptorspring.model.request.RequestDtoEvent;
import vn.com.disruptorspring.utilities.JSON;

import static vn.com.disruptorspring.service.kafka.ConsumerResponseOrder.TOPIC_RESULT_ORDER;

/**
 * Output the result of RequestDtoEvent to the handler of kafka
 */
public class RequestDtoEventKafkaOutput implements EventHandler<RequestDtoEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDtoEventKafkaOutput.class);

    KafkaTemplate<String, String> kafkaTemplate;

    public RequestDtoEventKafkaOutput(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) {

        ResponseDto responseDto = event.getResponseDto();
        if (responseDto == null) {
            return;
        }
        if (responseDto.getStatus() == null) responseDto.setStatus(ResponseDto.SUCCESS);
        LOGGER.info("Send Response for RequestID {}", responseDto.getRequestId());
        kafkaTemplate.send(TOPIC_RESULT_ORDER, JSON.toJson(responseDto));

    }
}
