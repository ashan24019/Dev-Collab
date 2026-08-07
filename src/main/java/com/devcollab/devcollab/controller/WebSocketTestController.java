package com.devcollab.devcollab.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketTestController {

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String testBroadcast(String message){
        return "Server received Message: " + message;
    }

}
