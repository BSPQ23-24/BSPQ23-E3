package es.deusto.spq.pojo;

/**
 * @class MessageData
 * @brief Represents the data of a message.
 */
public class MessageData {

    /** The content of the message. */
    private String message;

    /**
     * Constructs a new MessageData.
     * Required by serialization.
     */
    public MessageData() {
    }

    /**
     * Gets the content of the message.
     * @return The content of the message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the content of the message.
     * @param message The content to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}