package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class MessageTest {

    private Message message;

    @Before
    public void setUp() {
        message = new Message("Hello world!");
    }

    @Test
    public void getUser() {
        User user = new User("example", "password");
        message.setUser(user);
        assertEquals(user, message.getUser());
    }

    @Test
    public void testToString() {
        long currentTime = System.currentTimeMillis();
        assertEquals("Message: message --> Hello world!, timestamp -->  " + new Date(currentTime), message.toString());
    }
}
