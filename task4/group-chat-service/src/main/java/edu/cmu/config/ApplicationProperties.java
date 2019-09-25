package edu.cmu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Config topics.
 * Aggregate topics into a topic class.
 * We have 1 topics in this project, message.
 * => /topic/messages
 *
 * @author lucas
 */
@ConfigurationProperties(prefix = "raw", ignoreUnknownFields = false)
public class ApplicationProperties {

    /**
     * topic field.
     */
    private final Topic topic = new Topic();

    /**
     * Aggregate topics into a topic class.
     */
    public static class Topic {
        /**
         * message topic.
         */
        private String message;

        /**
         * Getter.
         *
         * @return message topic
         */
        public String getMessage() {
            return message;
        }

        /**
         * Setter.
         *
         * @param message topic
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * Get the topic.
     *
     * @return topic
     */
    public Topic getTopic() {
        return topic;
    }
}
