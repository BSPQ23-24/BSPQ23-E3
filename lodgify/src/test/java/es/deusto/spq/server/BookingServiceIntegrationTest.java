package es.deusto.spq.server;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.deusto.spq.server.jdo.Booking;

public class BookingServiceIntegrationTest {

    private BookingService bookingService;
    private PersistenceManagerFactory pmf;
    private PersistenceManager pm;
    private Transaction tx;
    private Client client;
    private WebTarget target;
    private Invocation.Builder invocationBuilder;

    @Before
    public void setUp() {
        // Mock de PersistenceManager y Transaction
        pmf = mock(PersistenceManagerFactory.class);
        pm = mock(PersistenceManager.class);
        tx = mock(Transaction.class);
        when(pmf.getPersistenceManager()).thenReturn(pm);
        when(pm.currentTransaction()).thenReturn(tx);

        // Mock del cliente HTTP
        client = mock(Client.class);
        target = mock(WebTarget.class);
        invocationBuilder = mock(Invocation.Builder.class);
        when(client.target("http://localhost:8080/booking/save")).thenReturn(target);
        when(target.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);

        // Configuración del servicio de reserva con el PersistenceManager mock
        bookingService = new BookingService();
        bookingService.setPersistenceManagerFactory(pmf);
    }

    @Test
    public void testSaveBooking_Success() {
        // Mock del objeto Booking
        Booking booking = mock(Booking.class);

        // Configurar el comportamiento del cliente HTTP mock
        Response response = Response.ok().build();
        when(invocationBuilder.post(Entity.entity(booking, MediaType.APPLICATION_JSON))).thenReturn(response);

        // Probar la funcionalidad del servicio de reserva
        Response actualResponse = bookingService.saveBooking(booking);

        // Verificar que se inicia y se confirma la transacción
        verify(tx).begin();
        verify(pm).makePersistent(booking);
        verify(tx).commit();

        // Verificar que se devuelve una respuesta exitosa
        assertEquals(Response.Status.OK.getStatusCode(), actualResponse.getStatus());
    }
}
