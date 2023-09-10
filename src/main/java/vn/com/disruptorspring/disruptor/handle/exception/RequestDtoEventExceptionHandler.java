package vn.com.disruptorspring.disruptor.handle.exception;

import com.lmax.disruptor.ExceptionHandler;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.disruptorspring.model.response.ResponseDto;
import vn.com.disruptorspring.model.request.RequestDtoEvent;

public class RequestDtoEventExceptionHandler implements ExceptionHandler<RequestDtoEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDtoEventExceptionHandler.class);

    @Override
    public void handleEventException(Throwable ex, long sequence, RequestDtoEvent event) {

        // Record logs when encountering exceptions
        event.setResponseDto(
                createExceptionResponseDto(event.getRequestDto().getId(), ExceptionUtils.getStackTrace(ex))
        );
        LOGGER.error("{} : {}. {} ", event.getRequestDto().getClass().getName(), event.getRequestDto().getId(), ExceptionUtils.getStackTrace(ex));
    }

    private ResponseDto createExceptionResponseDto(String requestId, String exception) {
        ResponseDto responseDto = new ResponseDto(requestId);
        responseDto.setErrorMessage(exception);
        responseDto.setSuccess(false);
        return responseDto;
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        LOGGER.error("Exception during onStart()", ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        LOGGER.error("Exception during onShutdown()", ex);
    }

}
