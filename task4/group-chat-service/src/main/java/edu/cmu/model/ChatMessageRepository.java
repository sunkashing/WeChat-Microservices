package edu.cmu.model;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repo for fetching chat message data.
 *
 * @author lucas
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

}