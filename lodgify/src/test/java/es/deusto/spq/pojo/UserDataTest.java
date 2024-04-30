package es.deusto.spq.pojo;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class UserDataTest {

    UserData userData;

    @Before
    public void setUp() {
        userData = new UserData();
        userData.setLogin("test-login");
        userData.setPassword("passwd");
    }

    @Test
    public void testGetLogin() {
        assertEquals("test-login", userData.getLogin());
    }

    @Test
    public void testGetPassword() {
        assertEquals("passwd", userData.getPassword());
    }

    @Test
    public void testToString() {
        assertEquals("[login=test-login, password=passwd]", userData.toString());
    }

    @Test
    public void testConstructor() {
        UserData emptyUserData = new UserData();
        assertEquals(null, emptyUserData.getLogin());
        assertEquals(null, emptyUserData.getPassword());
    }

    @Test
    public void testSetLoginWithNull() {
        userData.setLogin(null);
        assertEquals(null, userData.getLogin());
    }

    @Test
    public void testSetPasswordWithEmptyString() {
        userData.setPassword("");
        assertEquals("", userData.getPassword());
    }

    // Add more tests as needed...
}
