package com.sim.websocketchat

import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.*

@Component
class WebSocketEventHandler {
    private val logger = KotlinLogging.logger {}

    @EventListener
    fun handleWebSocketSessionConnectEventListener(event: SessionConnectEvent){
        logger.info { ">>> WebSocket Connect" }
    }

    @EventListener
    fun handleWebSocketSessionSubscribeEventListener(evnet: SessionSubscribeEvent){
        logger.info { ">>> WebSocket Subscribe" }
    }

    @EventListener
    fun handleWebSocketSessionUnsubscribeEventListener(event: SessionUnsubscribeEvent){
        logger.info { ">>> WebSocket Unsubscribe" }
    }

    @EventListener
    fun handleWebSocketSessionConnectedEventListener(event: SessionConnectedEvent){
        logger.info { ">>> WebSocket Connected" }
    }

    @EventListener
    fun handleWebSocketSessionDisconnectEventListener(event: SessionDisconnectEvent){
        logger.info { ">>> WebSocket Disconnect" }
    }

}