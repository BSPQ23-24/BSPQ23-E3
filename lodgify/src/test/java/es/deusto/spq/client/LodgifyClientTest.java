package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

import es.deusto.spq.server.jdo.User;

public class LodgifyClientTest {

    @Mock
    private Client client;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private WebTarget webTarget;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Invocation.Builder invocationBuilder;

    @Captor
    private ArgumentCaptor<Entity<User>> userEntityCaptor;

    private LodgifyClient lodgifyClient;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(client.target("http://localhost:8080/rest")).thenReturn(webTarget);
        when(webTarget.request(MediaType.TEXT_PLAIN)).thenReturn(invocationBuilder);
        Response response = Response.ok("Successful connection").build();
        when(invocationBuilder.get()).thenReturn(response);

        lodgifyClient = new LodgifyClient("localhost", "8080");
    }

    @Test
    public void testRegisterUser() {
        when(webTarget.path("user/register")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);

        Response response = Response.ok().build();
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);

        lodgifyClient.registerUser("test-login", "passwd", "test-name", "test-surname", "999999999", "test@example.com",
                "User", "123456789A", 123456789, 0, "test address");

        verify(invocationBuilder).post(any(Entity.class));
    }

    @Test
    public void testRegisterUserWithError() {
        when(webTarget.path("user/register")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);

        Response response = Response.serverError().build();
        when(invocationBuilder.post(any(Entity.class))).thenReturn(response);

        lodgifyClient.registerUser("test-login", "passwd", "test-name", "test-surname", "999999999", "test@example.com",
                "User", "123456789A", 123456789, 0, "test address");

        verify(invocationBuilder).post(any(Entity.class));
    }
}
