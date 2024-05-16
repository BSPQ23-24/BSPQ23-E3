package es.deusto.spq.pojo;

/**
 * @class DirectMessage
 * @brief Represents a direct message containing user data and message data.
 */
public class DirectMessage {

    /** The user data associated with the direct message. */
    private UserData userData;

    /** The message data associated with the direct message. */
    private MessageData messageData;

    /**
     * Constructs a new DirectMessage.
     * Required by serialization.
     */
    public DirectMessage() {
    }

    /**
     * Sets the user data associated with the direct message.
     * @param userData The user data to set.
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
     * Gets the user data associated with the direct message.
     * @return The user data.
     */
    public UserData getUserData() {
        return this.userData;
    }

    /**
     * Sets the message data associated with the direct message.
     * @param messageData The message data to set.
     */
    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    /**
     * Gets the message data associated with the direct message.
     * @return The message data.
     */
    public MessageData getMessageData() {
        return this.messageData;
    }
}