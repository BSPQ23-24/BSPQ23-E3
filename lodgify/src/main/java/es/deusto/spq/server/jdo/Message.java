package es.deusto.spq.server.jdo;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

/**
 * @class Message
 * @brief Represents a message entity in the system.
 */
@PersistenceCapable
public class Message {

    /** The user associated with the message. */
	User user=null;

    /** The text content of the message. */
	String text=null;

    /** The timestamp when the message was created. */
	long timestamp;
	

    /**
     * Constructs a new Message with the specified text.
     * @param text The text content of the message.
     */
    public Message(String text) {
        this.text = text;
		this.timestamp = System.currentTimeMillis();
    }

    /**
     * Gets the user associated with the message.
     * @return The user.
     */
	public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the message.
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns a string representation of the message.
     * @return A string containing the message details.
     */
    @Override
    public String toString() {
        return "Message: message --> " + this.text + ", timestamp -->  " + new Date(this.timestamp);
    }
}