package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.helper.Hashing;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Transaction transaction;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // initializing static mock object PersistenceManagerFactory
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);
            
            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);
            when(persistenceManager.currentTransaction()).thenReturn(transaction);

            // instantiate tested object with mock dependencies
            userService = new UserService();
        }
    }

    @Test
    public void testSayHello() {
        Response response = userService.sayHello();
        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals("Hello world!", response.getEntity().toString());
    }

    @Test
    public void testRegisterUser() {
        // prepare mock Persistence Manager to return User
        User user = new User("test-user", "passwd", "Test", "User", "123456789", "test@example.com", "user", "12345678A", 0, 0, "Test Address");

        // simulate that the object is not found in the database
        when(persistenceManager.getObjectById(any(), anyString())).thenReturn(null);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = userService.registerUser(user);

        // check that the new user is stored in the database with the correct values
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(persistenceManager).makePersistent(userCaptor.capture());
        assertEquals("test-user", userCaptor.getValue().getUsername());
        assertEquals("passwd", userCaptor.getValue().getPassword());

        // check expected response
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        // prepare mock Persistence Manager to return existing User
        User existingUser = new User("existing-user", "passwd", "Existing", "User", "987654321", "existing@example.com", "user", "87654321A", 0, 0, "Existing Address");
        when(persistenceManager.getObjectById(User.class, existingUser.getUsername())).thenReturn(existingUser);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = userService.registerUser(existingUser);

        // check expected response
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void testRegisterUserIncompleteData() {
        // prepare mock Persistence Manager to return User
        User user = new User("incomplete-user", "", "Incomplete", "User", "987654321", "incomplete@example.com", "user", "87654321A", 0, 0, "Incomplete Address");

        // simulate that the object is not found in the database
        when(persistenceManager.getObjectById(any(), anyString())).thenReturn(null);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = userService.registerUser(user);

        // check expected response
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void testLoginUser() {
        // prepare mock Persistence Manager to return existing User
        User existingUser = new User("test-user", "passwd", "Test", "User", "123456789", "test@example.com", "user", "12345678A", 0, 0, "Test Address");
        when(persistenceManager.getObjectById(User.class, existingUser.getUsername())).thenReturn(existingUser);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = userService.loginUser(existingUser);

        // check expected response
        assertEquals(Response.Status.OK, response.getStatusInfo());
        User returnedUser = (User) response.getEntity();
        assertEquals(existingUser.getUsername(), returnedUser.getUsername());
        assertEquals(existingUser.getPassword(), returnedUser.getPassword());
    }

    @Test
    public void testLoginUserNotFound() {
        // prepare mock Persistence Manager to return existing User
        User existingUser = new User("nonexistent-user", "passwd", "Nonexistent", "User", "987654321", "nonexistent@example.com", "user", "87654321A", 0, 0, "Nonexistent Address");
        when(persistenceManager.getObjectById(User.class, existingUser.getUsername())).thenReturn(null);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // call tested method
        Response response = userService.loginUser(existingUser);

        // check expected response
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }

    @Test
    public void testLoginUserInvalidPassword() {
        // prepare mock Persistence Manager to return existing User
        User existingUser = new User("test-user", "passwd", "Test", "User", "123456789", "test@example.com", "user", "12345678A", 0, 0, "Test Address");
        when(persistenceManager.getObjectById(User.class, existingUser.getUsername())).thenReturn(existingUser);

        // prepare mock transaction behaviour
        when(transaction.isActive()).thenReturn(true);

        // create a user with incorrect password
        User userWithIncorrectPassword = new User(existingUser.getUsername(), "incorrect-password", "", "", "", "", "", "", 0, 0, "");

        // call tested method
        Response response = userService.loginUser(userWithIncorrectPassword);

        // check expected response
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
}
