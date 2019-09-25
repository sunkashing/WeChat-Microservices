package edu.cmu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.model.ChatMessage;
import edu.cmu.model.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Controller for message(chat) service.
 *
 * @author lucas
 */
@Controller
public class MessageController {

    /**
     * logger for message controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    /**
     * string redis template to handle chat message.
     */
    private final StringRedisTemplate stringRedisTemplate;
    /**
     * JPA repo for chat message bean.
     */
    @Autowired
    private ChatMessageRepository repository;

    /**
     * Instantiate with redis template.
     *
     * @param stringRedisTemplate
     */
    public MessageController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * Map to "/message", handle chat messages.
     *
     * @param headerAccessor helper to access web socket message header
     * @param message        chat message
     * @throws JsonProcessingException exception
     */
    @MessageMapping("/message")
    public void sendWsChatMessage(SimpMessageHeaderAccessor headerAccessor, String message) throws JsonProcessingException {
        LOGGER.info("Incoming WebSocket Message : {}", message);
        Map<String, List<String>> nativeHeaderMap = headerAccessor.toNativeHeaderMap();
        List<String> usernameList = nativeHeaderMap.get("username");
        String username = "Unknown";
        if (!(usernameList == null || usernameList.size() == 0)) {
            username = usernameList.get(0);
        }
        LOGGER.info("Message: " + message + " | From User: " + username);

        List<String> timeList = nativeHeaderMap.get("time");
        String time = "";
        if (!(timeList == null || timeList.size() == 0)) {
            time = timeList.get(0);
        }
        LOGGER.info("Message: " + message + " | At time: " + time);

        publishMessageToRedis(message, username, time);
    }

    /**
     * Publish message to Redis Pub/Sub.
     *
     * @param message  message
     * @param username username
     * @param time time
     * @throws JsonProcessingException exception
     */
    private void publishMessageToRedis(String message, String username, String time) throws JsonProcessingException {
        // chat
        ChatMessage chatMessage = new ChatMessage(message, username, time);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(chatMessage);

        // Publish Message to Redis Channels
        stringRedisTemplate.convertAndSend("chat", chatString);

        // save to mysql for persistence
        repository.save(chatMessage);
    }

    /**
     * Map to "/getallmessages" HTTP requests.
     * get all the message beans in MySQLs
     *
     * @return a list of chat message beans
     */
    @RequestMapping(value = "/getallmessages", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatMessage> getAllChatMessages(Model model) {
        return repository.findAll();
    }
}
