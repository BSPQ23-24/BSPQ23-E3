package es.deusto.spq.client;

import static org.mockito.Mockito.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class LodgifyClientTest {

    private LodgifyClient lodgifyClient;
    private Client client;
    private WebTarget webTarget;

    @Before
    public void setUp() {
        client = mock(Client.class);
        webTarget = mock(WebTarget.class);
        lodgifyClient = new LodgifyClient("localhost", "8080");
        lodgifyClient.setClient(client);
        lodgifyClient.setWebTarget(webTarget);
    }

    @Test
    public void testRegisterUser() {
        // Configuración del mock
        WebTarget registerUserWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("user/register")).thenReturn(registerUserWebTarget);
        when(registerUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        Response response = Response.ok().build();
        when(invocationBuilder.post(any())).thenReturn(response);

        // Ejecución del método bajo prueba
        lodgifyClient.registerUser("user", "password", "name", "surname", "999999999", "user@mail.es", "User", "", 0, 0,
                "");

        // Verificación de las interacciones con los mocks
        verify(registerUserWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).post(any());
    }

    @Test
    public void testSaveBooking() {
        // Configuración del mock
        WebTarget saveBookingWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("booking/save")).thenReturn(saveBookingWebTarget);
        when(saveBookingWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        Response response = Response.ok().build();
        when(invocationBuilder.post(any())).thenReturn(response);

        // Ejecución del método bajo prueba
        lodgifyClient.saveBooking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");

        // Verificación de las interacciones con los mocks
        verify(saveBookingWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).post(any());
    }
}
