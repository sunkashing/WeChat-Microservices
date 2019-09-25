package edu.cmu.service;

import edu.cmu.config.ApplicationProperties;
import edu.cmu.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Web socket service.
 * Core functions:
 * => sendChatMessage
 *
 * @author lucas
 */
@Service
public class WebSocketMessageService {

    /**
     * config for topic.
     */
    private final ApplicationProperties applicationProperties;
    /**
     * WS Messaging template.
     */
    private final SimpMessagingTemplate template;
    /**
     * logger for WS service.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageService.class);

    /**
     * Instantiate with topic information and template.
     *
     * @param applicationProperties topic config
     * @param template              WS template
     */
    public WebSocketMessageService(ApplicationProperties applicationProperties, SimpMessagingTemplate template) {
        this.applicationProperties = applicationProperties;
        this.template = template;
    }

    /**
     * Async sending chat message.
     *
     * @param message message
     */
    @Async
    public void sendChatMessage(ChatMessage message) {
        LOGGER.info("Message sent: {}", message);
        template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
    }
}
