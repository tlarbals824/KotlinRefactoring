package com.sim.websocketchat

import mu.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChattingController {
    private val logger = KotlinLogging.logger {}

    @MessageMapping("/chatting-message")
    @SendTo("/topic/chatting")
    fun chatting(chattingMessage: ChattingMessage): ChattingResponse {
        logger.info { ">>> Receive Message: ${chattingMessage.message}" }
        return ChattingResponse(chattingMessage.message)
    }
}