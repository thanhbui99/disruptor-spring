package vn.com.disruptorspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.disruptorspring.request.RequestDto;
import vn.com.disruptorspring.request.RequestDtoEventProducer;

@RestController
@RequestMapping("api/demo")
public class DemoController {

    @Autowired
    RequestDtoEventProducer requestDtoEventProducer;

    @GetMapping
    public String test(@RequestParam Long itemId, @RequestParam String userId){
        requestDtoEventProducer.onData(new RequestDto(itemId, userId));
        return "ok";
    }
}
