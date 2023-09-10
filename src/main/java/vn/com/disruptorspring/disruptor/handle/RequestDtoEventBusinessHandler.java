package vn.com.disruptorspring.disruptor.handle;

import com.lmax.disruptor.EventHandler;
import vn.com.disruptorspring.disruptor.command.item.ItemAmountUpdateCommand;
import vn.com.disruptorspring.disruptor.command.order.OrderInsertCommand;
import vn.com.disruptorspring.memdb.Item;
import vn.com.disruptorspring.memdb.ItemRepository;
import vn.com.disruptorspring.model.request.RequestDto;
import vn.com.disruptorspring.model.response.ResponseDto;
import vn.com.disruptorspring.model.request.RequestDtoEvent;
import vn.com.disruptorspring.utilities.StrongUuidGenerator;

/**
 * Handle business logic
 */
public class RequestDtoEventBusinessHandler implements EventHandler<RequestDtoEvent> {

    private ItemRepository itemRepository;

    @Override
    public void onEvent(RequestDtoEvent event, long sequence, boolean endOfBatch) throws Exception {

        if (event.hasErrorOrException()) {
            return;
        }

        RequestDto requestDto = event.getRequestDto();
        Item item = itemRepository.get(requestDto.getItemId());

        ResponseDto responseDto = new ResponseDto(requestDto.getId());

        if (item == null) {

            responseDto.setSuccess(false);
            responseDto.setErrorMessage("Product data has not been cached in memory");
            responseDto.setStatus(ResponseDto.FAILED);

        } else if (item.decreaseAmount()) {

            responseDto.setSuccess(true);

            event.getCommandCollector().addCommand(new ItemAmountUpdateCommand(StrongUuidGenerator.getNextId(), item.getId(), item.getInventory()));
            event.getCommandCollector().addCommand(new OrderInsertCommand(StrongUuidGenerator.getNextId(), item.getId(), requestDto.getUserId()));

        } else {

            responseDto.setSuccess(false);
            responseDto.setErrorMessage("Inventory shortage");
            responseDto.setStatus(ResponseDto.FAILED);
        }

        event.setResponseDto(responseDto);

    }

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

}
