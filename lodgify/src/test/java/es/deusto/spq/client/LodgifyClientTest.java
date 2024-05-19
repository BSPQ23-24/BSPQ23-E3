/*package es.deusto.spq.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
        WebTarget registerUserWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("user/register")).thenReturn(registerUserWebTarget);
        when(registerUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        Response response = Response.ok().build();
        when(invocationBuilder.post(any())).thenReturn(response);

        lodgifyClient.registerUser("user", "password", "name", "surname", "999999999", "user@mail.es", "User", "", 0,
                0, "");

        verify(registerUserWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).post(any());
    }

    @Test
    public void testRegisterUser_Error() {
        WebTarget registerUserWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("user/register")).thenReturn(registerUserWebTarget);
        when(registerUserWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any())).thenThrow(new InternalServerErrorException());

        try {
            lodgifyClient.registerUser("user", "password", "name", "surname", "999999999", "user@mail.es", "User", "",
                    0, 0, "");
        } catch (InternalServerErrorException e) {
            verify(registerUserWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
            verify(invocationBuilder, times(1)).post(any());
            return;
        }

        fail("Se esperaba una InternalServerErrorException al registrar usuario pero no se lanzó ninguna excepción.");
    }

    @Test
    public void testSaveBooking() {
        WebTarget saveBookingWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("booking/save")).thenReturn(saveBookingWebTarget);
        when(saveBookingWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        Response response = Response.ok().build();
        when(invocationBuilder.post(any())).thenReturn(response);

        lodgifyClient.saveBooking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");

        verify(saveBookingWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
        verify(invocationBuilder, times(1)).post(any());
    }

    @Test
    public void testSaveBooking_Error() {
        WebTarget saveBookingWebTarget = mock(WebTarget.class);
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        when(webTarget.path("booking/save")).thenReturn(saveBookingWebTarget);
        when(saveBookingWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any())).thenThrow(new BadRequestException());

        try {
            lodgifyClient.saveBooking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");
        } catch (BadRequestException e) {
            verify(saveBookingWebTarget, times(1)).request(MediaType.APPLICATION_JSON);
            verify(invocationBuilder, times(1)).post(any());
            return;
        }

        fail("Se esperaba una BadRequestException al guardar reserva pero no se lanzó ninguna excepción.");
    }

    @SuppressWarnings("static-access")
    @Test
    public void testMain() {
        // Mock del LodgifyClient
        LodgifyClient mockClient = Mockito.mock(LodgifyClient.class);

        // Redirigir la salida estándar a un PrintStream que podamos controlar
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Llamar al método main
        String[] args = { "localhost", "8080" };
        mockClient.main(args);

        // Verificar si el mensaje de uso se imprimió correctamente
        String expectedOutput = "Fallo en la conexión, código de estado: 404JerseyWebTarget { http://localhost:8080/rest }"
                + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}
*/