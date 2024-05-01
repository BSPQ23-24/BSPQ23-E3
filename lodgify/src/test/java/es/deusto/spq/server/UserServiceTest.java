package es.deusto.spq.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.deusto.spq.server.jdo.User;

public class UserServiceTest {

    @Mock
    private PersistenceManager pm;

    @Mock
    private Transaction tx;

    @InjectMocks
    private UserService userService;

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(pm.currentTransaction()).thenReturn(tx);
        when(pm.newQuery(User.class, "username == :username")).thenReturn(mock(Query.class));
    }

    @After
    public void tearDown() {
        pm = null;
        tx = null;
        userService = null;
    }

    @Test
    public void testSayHello() {
        Response response = userService.sayHello();
        assertEquals("Hello world!", response.getEntity());
    }

    @Test
    public void testRegisterUser() {
        User user = new User("testUser", "password", "John", "Doe", "123456789", "test@gmail.com", "type",
                "id_card", 11111111, 1234566, "address");
        @SuppressWarnings("unchecked")
        Query<User> queryMock = mock(Query.class);
        when(pm.newQuery(User.class, "username == :username")).thenReturn(queryMock);
        when(queryMock.execute("testUser")).thenReturn(Collections.emptyList());
        Response response = userService.registerUser(user);
        assertEquals(200, response.getStatus());
        verify(pm).makePersistent(user);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLoginUser() {
        User user = new User("testUser", "password", "John", "Doe", "123456789", "test@gmail.com", "type",
                "id_card", 11111111, 1234566, "address");
        Query<User> queryMock = mock(Query.class);
        when(pm.newQuery(User.class, "username == :username")).thenReturn(queryMock);
        when(queryMock.execute("testUser")).thenReturn(Collections.singletonList(user));
        Response response = userService.loginUser(user);
        assertEquals(200, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testModifyUser() {
        User originalUser = new User("testUser", "password", "John", "Doe", "123456789", "test@gmail.com", "type",
                "id_card", 11111111, 1234566, "address");

        User modifiedUser = new User("testUser", "password", "ModifiedName", "ModifiedSurname", "987654321",
                "modified@example.com", "type", "modified_id_card", 2222222, 33333333, "modified_address");

        Query<User> queryMock = mock(Query.class);
        when(pm.newQuery(User.class, "username == :username")).thenReturn(queryMock);
        when(queryMock.execute("testUser")).thenReturn(Collections.singletonList(originalUser));

        Response response = userService.modifyUser(modifiedUser);

        assertEquals(200, response.getStatus());
        verify(pm).makePersistent(originalUser);
    }
}
