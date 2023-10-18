package com.example.websocket.demo.socket

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

/**
 *
 * @since : 2023/10/18
 * @author zzk
 */
@Component
@ServerEndpoint("/websocket/{name}")
class TestSocket {

    lateinit var session: Session

    lateinit var name: String

    companion object {
        private val SESSIONS: ConcurrentHashMap<String, TestSocket> = ConcurrentHashMap()

        private val log = LoggerFactory.getLogger(TestSocket::class.java)
    }

    /**
     * 连接建立时调用的方法
     */
    @OnOpen
    fun onOpen(session: Session, @PathParam("name") name: String) {
        log.info("----------------------------------")
        this.session = session;
        this.name = name;

        SESSIONS[name] = this
        log.info("[TestSocket] 连接成功，当前人数为 : ${SESSIONS.size}")
        log.info("---------------------------------")
    }

    /**
     * 连接关闭调用方法
     */
    @OnClose
    fun onClose() {
        SESSIONS.remove(this.name)
        log.info("[TestSocket] 退出成功，当前连接人数为 : ${SESSIONS.size}")
        groupSending("${this.name}走了")
    }


    /**
     * 收到客户端消息处理方案
     */
    @OnMessage
    fun onMessage(message: String) {
        //客户端消息格式 : to-user:u1:message:aaaaa
        if (message.indexOf("to-user") == -1) {
            groupSending(message)
        } else {
            val split = message.split(":")
            sendTo(split[1], split[3])
        }

    }


    fun sendTo(name: String, message: String) {
        if (SESSIONS.containsKey(name)) {
            SESSIONS[name]!!.session.basicRemote.sendText(message)
        }
    }

    /**
     * 消息群发
     */
    fun groupSending(message: String) {
        for (sock in SESSIONS.values) {
            sock.session.basicRemote.sendText(message)
        }
    }

}