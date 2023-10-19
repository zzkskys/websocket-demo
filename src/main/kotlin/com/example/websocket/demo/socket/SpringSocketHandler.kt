package com.example.websocket.demo.socket

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @since : 2023/10/19
 * @author zzk
 */
@Component
class SpringSocketHandler : WebSocketHandler {
    companion object {
        private val log = LoggerFactory.getLogger(SpringSocketHandler::class.java)


        /**
         * 模拟的 session 的存储。
         * 实质 session 将存储与 redis 中
         */
        private val SESSIONS = ConcurrentHashMap<String, WebSocketSession>()
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val username = session.getUsername()
        if (username != null) {
            SESSIONS[username] = session
        }
        log.info("[SpringSocketHandler] 已连接 , 目前人数 : ${SESSIONS.size}")
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        //消息处理
        sendAll(message.payload.toString())
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        //异常处理
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        SESSIONS.remove(session.getUsername())
        log.info("[SpringSocketHandler] 已关闭,目前人数 : ${SESSIONS.size}")
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }


    fun sendAll(message: String) {
        for (session in SESSIONS.values) {
            session.sendMessage(TextMessage(message))
        }
    }
}

fun WebSocketSession.getUsername(): String? {
    return this.handshakeHeaders["name"]?.first()
}