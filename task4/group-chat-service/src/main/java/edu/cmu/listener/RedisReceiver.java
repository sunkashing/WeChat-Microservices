package edu.cmu.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.model.ChatMessage;
import edu.cmu.service.WebSocketMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Redis message receiver.
 *
 * @author lucas
 */
public class RedisReceiver {

    /**
     * Logger for receiver.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);

    /**
     * WebSocket service.
     */
    private final WebSocketMessageService webSocketMessageService;

    /**
     * Instantiate with WS service.
     *
     * @param webSocketMessageService ws service
     */
    public RedisReceiver(WebSocketMessageService webSocketMessageService) {
        this.webSocketMessageService = webSocketMessageService;
    }

    /**
     * Invoked when message is publish to "chat" channel.
     *
     * @param message message
     * @throws IOException exception
     */
    public void receiveChatMessage(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

        LOGGER.info("Notification Message Received: " + chatMessage);
        webSocketMessageService.sendChatMessage(chatMessage);
    }
}
