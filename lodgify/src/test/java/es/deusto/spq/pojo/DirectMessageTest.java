package es.deusto.spq.pojo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class DirectMessageTest {

    DirectMessage directMessage;
    UserData userData;
    MessageData messageData;

    @Before
    public void setUp() {

        userData = mock(UserData.class);
        messageData = mock(MessageData.class);

        directMessage = new DirectMessage();
        directMessage.setUserData(userData);
        directMessage.setMessageData(messageData);
    }

    @Test
    public void testGetUserData() {
        assertEquals(userData, directMessage.getUserData());
    }

    @Test
    public void testGetMessageData() {
        assertEquals(messageData, directMessage.getMessageData());
    }

    @Test
    public void testSetUserData() {
        UserData newUserData = mock(UserData.class);
        
        directMessage.setUserData(newUserData);
        
        assertEquals(newUserData, directMessage.getUserData());
    }

    @Test
    public void testSetMessageData() {
        MessageData newMessageData = mock(MessageData.class);
        
        directMessage.setMessageData(newMessageData);
        
        assertEquals(newMessageData, directMessage.getMessageData());
    }
}

