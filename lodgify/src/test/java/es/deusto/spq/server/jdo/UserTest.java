package es.deusto.spq.server.jdo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("example", "password", "User1", "User1", "123456789", "user1@example.com", "traveller",
                "123456789A",
                123456789, 123456789, "Av Universidades 9");
    }

    @Test
    public void testGetUsername() {
        assertEquals("example", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("new-username");
        assertEquals("new-username", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpasswd");
        assertEquals("newpasswd", user.getPassword());
    }

    @Test
    public void testGetName() {
        assertEquals("User1", user.getName());
    }

    @Test
    public void testSetName() {
        user.setName("User2");
        assertEquals("User2", user.getName());
    }

    @Test
    public void testGetSurname() {
        assertEquals("User1", user.getSurname());
    }

    @Test
    public void testSetSurname() {
        user.setSurname("User2");
        assertEquals("User2", user.getSurname());
    }

    @Test
    public void testGetPhone_number() {
        assertEquals("123456789", user.getPhone_number());
    }

    @Test
    public void testSetPhone_number() {
        user.setPhone_number("987654321");
        assertEquals("987654321", user.getPhone_number());
    }

    @Test
    public void testGetEmail() {
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("user2@example.com");
        assertEquals("user2@example.com", user.getEmail());
    }

    @Test
    public void testGetUserType() {
        assertEquals("traveller", user.getUser_type());
    }

    @Test
    public void testSetUserType() {
        user.setUser_type("host");
        assertEquals("host", user.getUser_type());
    }

    @Test
    public void testGetIdCard() {
        assertEquals("123456789A", user.getId_card());
    }

    @Test
    public void testSetIdCard() {
        user.setId_card("987654321Y");
        assertEquals("987654321Y", user.getId_card());
    }

    @Test
    public void testGetBankAccount() {
        assertEquals(123456789, user.getBank_account());
    }

    @Test
    public void testSetBankAccount() {
        user.setBank_account(987654321);
        assertEquals(987654321, user.getBank_account());
    }

    @Test
    public void testGetSocialSN() {
        assertEquals(123456789, user.getSocial_SN());
    }

    @Test
    public void testSetSocialSN() {
        user.setSocial_SN(987654321);
        assertEquals(987654321, user.getSocial_SN());
    }

    @Test
    public void testGetAddress() {
        assertEquals("Av Universidades 9", user.getAddress());
    }

    @Test
    public void testSetAddress() {
        user.setAddress("Av Universidades 23");
        assertEquals("Av Universidades 23", user.getAddress());
    }

    @Test
    public void testToString() {
        String expected = "Username --> example, password -->  password";
        assertEquals(expected, user.toString());
    }
}