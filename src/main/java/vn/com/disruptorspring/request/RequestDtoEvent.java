package vn.com.disruptorspring.request;


import vn.com.disruptorspring.config.itl.CommandCollector;

public class RequestDtoEvent {

    private RequestDto requestDto;

    /**
     * Database operation Command collector
     */
    private final CommandCollector commandCollector = new CommandCollector();

    /**
     * response result
     */
    private ResponseDto responseDto;

    public RequestDto getRequestDto() {
        return requestDto;
    }

    public void setRequestDto(RequestDto requestDto) {
        this.requestDto = requestDto;
    }

    public void setResponseDto(ResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public CommandCollector getCommandCollector() {
        return commandCollector;
    }

    public ResponseDto getResponseDto() {
        return responseDto;
    }

    public void clearForGc() {
        this.requestDto = null;
        this.commandCollector.getCommandList().clear();
        this.responseDto = null;
    }

    public boolean hasErrorOrException() {
        return responseDto != null && (responseDto.getErrorMessage() != null);
    }

}
