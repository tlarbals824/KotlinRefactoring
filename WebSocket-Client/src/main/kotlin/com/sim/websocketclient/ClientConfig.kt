package com.sim.websocketclient

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

@Configuration
class ClientConfig {
    companion object{
        private const val ENDPOINT = "ws://localhost:8080/hello-websocket-chatting"
    }

    @Bean
    fun webSocketStompClient(webSocketStompClient: WebSocketStompClient, stompSessionHandler: StompSessionHandler) : WebSocketStompClient{
        webSocketStompClient.messageConverter = MappingJackson2MessageConverter()

        val stompHeaders = StompHeaders()
        stompHeaders.add("name","sim")

        val urlVariables = arrayOf<Any>()
        webSocketStompClient.connectAsync(ENDPOINT,null,stompHeaders,stompSessionHandler, urlVariables)

        return webSocketStompClient
    }

    @Bean
    fun webSocketClient() : WebSocketStompClient?{
        val webSocketClient : WebSocketClient = StandardWebSocketClient()
        return WebSocketStompClient(webSocketClient)
    }

    @Bean
    fun stompSessionHandler(objectMapper: ObjectMapper) : StompSessionHandler?{
        return ClientWebSocketStompSessionHandler(objectMapper)
    }
}