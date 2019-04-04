package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestResource {

    private final MessagesGateway gateway;

    @RequestMapping( value = "send", method = RequestMethod.GET)
    public void send() {
        gateway.sendMessage("TEST MESSAGE");
    }

}
