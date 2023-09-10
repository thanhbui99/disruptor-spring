package vn.com.disruptorspring.disruptor.producer;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import vn.com.disruptorspring.model.request.RequestDto;
import vn.com.disruptorspring.model.request.RequestDtoEvent;

public class RequestDtoEventProducer {

    private static final EventTranslatorOneArg<RequestDtoEvent, RequestDto> TRANSLATOR =
            (event, sequence, requestDto) -> event.setRequestDto(requestDto);

    private final RingBuffer<RequestDtoEvent> ringBuffer;

    public RequestDtoEventProducer(RingBuffer<RequestDtoEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(RequestDto requestDto) {
        ringBuffer.publishEvent(TRANSLATOR, requestDto);
    }

}
