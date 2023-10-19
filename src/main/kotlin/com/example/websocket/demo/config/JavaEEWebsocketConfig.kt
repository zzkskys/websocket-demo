package com.example.websocket.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter

/**
 *
 * @since : 2023/10/18
 * @author zzk
 */
@Configuration
class JavaEEWebsocketConfig {

    @Bean
    fun serverEndpointExporter(): ServerEndpointExporter {
        return ServerEndpointExporter()
    }
}