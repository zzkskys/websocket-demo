package com.example.websocket.demo.config

import com.example.websocket.demo.socket.SpringSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
@Configuration
@EnableWebSocket
class SpringWebsocketConfig(
    private val springSocketHandler: SpringSocketHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(springSocketHandler, "/spring-websocket")
            .setAllowedOrigins("*")
    }

}