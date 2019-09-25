package edu.cmu.model;

import javax.persistence.*;

/**
 * Model class for chat message.
 *
 * @author lucas
 */
@Entity
@Table(name = "chatmessage")
public class ChatMessage {

    /**
     * message id, required by Hibernate
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    /**
     * message content.
     */
    private String message;
    /**
     * sender username.
     */
    private String username;
    /**
     * message occurred time.
     */
    private String time;

    /**
     * Instantiate with message, username and message time.
     *
     * @param message  message
     * @param username username
     * @param time time
     */
    public ChatMessage(String message, String username, String time) {
        this.message = message;
        this.username = username;
        this.time = time;
    }

    /**
     * Instantiate with nothing.
     * Required by ORM tools
     */
    public ChatMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Override to string.
     *
     * @return chat message as a string
     */
    @Override
    public String toString() {
        return "ChatMessage{" +
                " id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", username='" + username + '\'' +
                ". time='" + time + '\'' +
                '}';
    }
}
