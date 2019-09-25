package edu.cmu.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repo for user credentials.
 *
 * @author lucas
 */
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    UserCredential findByUsername(final String username);
}
