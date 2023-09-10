package vn.com.disruptorspring.request;

import com.lmax.disruptor.EventHandler;
import vn.com.disruptorspring.config.item.ItemAmountUpdateCommand;
import vn.com.disruptorspring.config.order.OrderInsertCommand;
import vn.com.disruptorspring.memdb.Item;
import vn.com.disruptorspring.memdb.ItemRepository;

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

        } else if (item.decreaseAmount()) {

            responseDto.setSuccess(true);

            event.getCommandCollector().addCommand(new ItemAmountUpdateCommand(requestDto.getId(), item.getId(), item.getAmount()));
            event.getCommandCollector().addCommand(new OrderInsertCommand(requestDto.getId(), item.getId(), requestDto.getUserId()));

        } else {

            responseDto.setSuccess(false);
            responseDto.setErrorMessage("Inventory shortage");

        }

        event.setResponseDto(responseDto);


    }

    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

}
