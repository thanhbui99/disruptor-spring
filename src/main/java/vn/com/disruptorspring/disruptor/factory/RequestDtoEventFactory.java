package vn.com.disruptorspring.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import vn.com.disruptorspring.model.request.RequestDtoEvent;

public class RequestDtoEventFactory implements EventFactory<RequestDtoEvent> {

  @Override
  public RequestDtoEvent newInstance() {
    return new RequestDtoEvent();
  }

}
