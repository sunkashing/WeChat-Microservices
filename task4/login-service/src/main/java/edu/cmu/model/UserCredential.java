package edu.cmu.model;

/**
 * User credential bean.
 * need to use the same format that Spring Security requires
 *
 * @author lucas
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserCredential {

    /**
     * username, as Id.
     */
    @Id
    private String username;
    /**
     * password, which will be encoded.
     */
    private String password;
    /**
     * Indicates whether the user is enabled or disabled.
     */
    private boolean enabled = true;

    /**
     * Default constructor required by ORM tools.
     */
    protected UserCredential() {
    }

    /**
     * Instantiate with username and password.
     * @param u username
     * @param p password
     */
    public UserCredential(String u, String p) {
        username = u;
        password = p;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
