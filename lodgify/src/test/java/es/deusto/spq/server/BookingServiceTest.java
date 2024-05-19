/*package es.deusto.spq.server;

import static org.mockito.Mockito.*;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import es.deusto.spq.server.jdo.Booking;

public class BookingServiceTest {

    private BookingService bookingService;
    private PersistenceManagerFactory pmf;
    private PersistenceManager pm;
    private Transaction tx;

    @Before
    public void setUp() {
        pmf = mock(PersistenceManagerFactory.class);
        pm = mock(PersistenceManager.class);
        tx = mock(Transaction.class);
        when(pmf.getPersistenceManager()).thenReturn(pm);
        when(pm.currentTransaction()).thenReturn(tx);

        bookingService = new BookingService();
        bookingService.setPersistenceManagerFactory(pmf);
    }

    @Test
    public void testSaveBooking_Success() {
        Booking booking = new Booking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");

        Response response = bookingService.saveBooking(booking);

        verify(tx).begin();
        verify(pm).makePersistent(booking);
        verify(tx).commit();

        verify(pm).close();

        assert response.getStatus() == 200;
    }

    @Test
    public void testSaveBooking_Failure() {
        Booking booking = new Booking("travelerUsername", "hostUsername", 1L, "startDate", "endDate");

        doThrow(new RuntimeException("Test Exception")).when(pm).makePersistent(booking);

        Response response = bookingService.saveBooking(booking);

        verify(tx).begin();

        verify(pm).close();

        assert response.getStatus() == 500;
    }
}
*/