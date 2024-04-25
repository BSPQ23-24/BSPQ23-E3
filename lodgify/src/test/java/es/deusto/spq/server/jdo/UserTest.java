package es.deusto.spq.server.jdo;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import es.deusto.spq.server.jdo.User;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("testUser", "password123", "John", "Doe", "123456789", "john@example.com", "normal", "ABC123", 123456789, 987654321, "123 Main St");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getSurname());
        assertEquals("123456789", user.getPhone_number());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("normal", user.getUser_type());
        assertEquals("ABC123", user.getId_card());
        assertEquals(123456789, user.getBank_account());
        assertEquals(987654321, user.getSocial_SN());
        assertEquals("123 Main St", user.getAddress());

        user.setUsername("newUser");
        user.setPassword("newPassword");
        user.setName("Jane");
        user.setSurname("Smith");
        user.setPhone_number("987654321");
        user.setEmail("jane@example.com");
        user.setUser_type("admin");
        user.setId_card("XYZ789");
        user.setBank_account(987654321);
        user.setSocial_SN(123456789);
        user.setAddress("456 Oak St");

        assertEquals("newUser", user.getUsername());
        assertEquals("newPassword", user.getPassword());
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getSurname());
        assertEquals("987654321", user.getPhone_number());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("admin", user.getUser_type());
        assertEquals("XYZ789", user.getId_card());
        assertEquals(987654321, user.getBank_account());
        assertEquals(123456789, user.getSocial_SN());
        assertEquals("456 Oak St", user.getAddress());
    }

    @Test
    public void testToString() {
        assertEquals("Username --> testUser, password -->  password123", user.toString());
    }
}
