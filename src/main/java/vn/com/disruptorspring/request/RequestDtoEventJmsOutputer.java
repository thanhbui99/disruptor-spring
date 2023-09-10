package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Output the result of RequestDtoEvent to the handler of jms
 */
public class RequestDtoEventJmsOutputer implements EventHandler<RequestDtoEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestDtoEventJmsOutputer.class);

//  private JmsMessageSender messageSender;

  @Override
  public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) throws Exception {

    ResponseDto responseDto = event.getResponseDto();
    if (responseDto == null) {
      return;
    }
//    LOGGER.info("Send Response for Request {}. Id: {}", responseDto.getRequestId(), responseDto.getId());
    // 这里这么做没有问题, 因为实际的调用方是单线程调用的, 多线程下则会出现并发问题
//    messageSender.sendMessage(responseDto);

  }

//  public void setMessageSender(JmsMessageSender messageSender) {
//    this.messageSender = messageSender;
//  }
}
