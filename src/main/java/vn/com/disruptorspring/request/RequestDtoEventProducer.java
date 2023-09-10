package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

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
