package com.sim.websocket

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class HelloWebSocketHandler: TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println(">>> Client Connected: ${session.id}")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println(">>> Receive Message : $message")
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        println(">>> Error: ${session.id}, ${exception.message}")
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        println(">>> Connection Closed: ${session.id}")
    }


}