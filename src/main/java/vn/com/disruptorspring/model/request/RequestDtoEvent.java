package vn.com.disruptorspring.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.disruptorspring.disruptor.command.base.CommandCollector;
import vn.com.disruptorspring.model.response.ResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDtoEvent {

    private RequestDto requestDto;

    /**
     * Operation Command collector
     */
    private final CommandCollector commandCollector = new CommandCollector();

    /**
     * response result
     */
    private ResponseDto responseDto;


    public void clearForGc() {
        this.requestDto = null;
        this.commandCollector.getCommandList().clear();
        this.responseDto = null;
    }

    public boolean hasErrorOrException() {
        return responseDto != null && (responseDto.getErrorMessage() != null);
    }

}
