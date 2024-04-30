package es.deusto.spq.pojo;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class MessageDataTest {

    MessageData messageData;

    @Before
    public void setUp() {
        messageData = new MessageData();
        messageData.setMessage("Hello World!");
    }

    @Test
    public void testGetMessage() {
        assertEquals("Hello World!", messageData.getMessage());
    }

    @Test
    public void testConstructor() {
        MessageData emptyMessageData = new MessageData();
        assertEquals(null, emptyMessageData.getMessage());
    }

    @Test
    public void testSetMessage() {
        messageData.setMessage("New message");
        assertEquals("New message", messageData.getMessage());
    }

    @Test
    public void testSetMessageWithNull() {
        messageData.setMessage(null);
        assertEquals(null, messageData.getMessage());
    }

    @Test
    public void testSetMessageWithEmptyString() {
        messageData.setMessage("");
        assertEquals("", messageData.getMessage());
    }
}
