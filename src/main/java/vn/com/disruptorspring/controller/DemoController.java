package vn.com.disruptorspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.disruptorspring.memdb.Item;
import vn.com.disruptorspring.model.request.RequestDto;
import vn.com.disruptorspring.model.response.OrderResponse;
import vn.com.disruptorspring.model.response.ResponseDto;
import vn.com.disruptorspring.service.CommandService;

import java.util.List;

@RestController
@RequestMapping("api/disruptor/order")
@RequiredArgsConstructor
public class DemoController {

    private final CommandService commandService;

    @PostMapping
    public ResponseEntity<OrderResponse> order(@RequestParam Long itemId, @RequestParam String userId) {
        RequestDto requestDto = new RequestDto(itemId, userId);
        return ResponseEntity.ok(new OrderResponse(commandService.doRequest(requestDto)));
    }

    @GetMapping("/result")
    public ResponseEntity<ResponseDto> orderResult(@RequestParam String requestId) {
        ResponseDto response = commandService.getResponse(requestId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Item>> orders(@RequestParam String userId) {
        List<Item> response = commandService.getOrdersByUser(userId);
        return ResponseEntity.ok(response);
    }
}
