package edu.cmu.config;

import edu.cmu.listener.RedisReceiver;
import edu.cmu.service.WebSocketMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

/**
 * Config for Redis Pub/Sub.
 * Use Redis Pub/Sub as a messaging service, so that we can scale the web socket service horizontally
 *
 * @author lucas
 */
@Configuration
public class RedisConfig {

    /**
     * Aggregate config information for Redis.
     *
     * @param connectionFactory          redis connection factory
     * @param chatMessageListenerAdapter adapter for chat
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            @Qualifier("chatMessageListenerAdapter") MessageListenerAdapter chatMessageListenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(chatMessageListenerAdapter, new PatternTopic("chat"));
        return container;
    }

    /**
     * Adapter for different type of topic messages.
     * This one is for chat messages
     *
     * @param redisReceiver receiver
     * @return adapter
     */
    @Bean("chatMessageListenerAdapter")
    MessageListenerAdapter chatMessageListenerAdapter(RedisReceiver redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "receiveChatMessage");
    }

    /**
     * Get receiver helper class.
     *
     * @param webSocketMessageService web socket message service
     * @return Redis receiver
     */
    @Bean
    RedisReceiver receiver(WebSocketMessageService webSocketMessageService) {
        return new RedisReceiver(webSocketMessageService);
    }

    /**
     * Get string redis template (helper class that simplifies Redis data access code, especially for string messages).
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    /**
     * Get redis template (helper class that simplifies Redis data access code).
     */
    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
