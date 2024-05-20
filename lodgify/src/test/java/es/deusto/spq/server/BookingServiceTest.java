package es.deusto.spq.server;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	System.exit(1);
    	}

        Booking booking = new Booking("travelerUsername", "hostUsername", 1L, startDate, endDate);

        Response response = bookingService.saveBooking(booking);

        verify(tx).begin();
        verify(pm).makePersistent(booking);
        verify(tx).commit();

        verify(pm).close();

        assert response.getStatus() == 200;
    }

    @Test
    public void testSaveBooking_Failure() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	System.exit(1);
    	}
        
        Booking booking = new Booking("travelerUsername", "hostUsername", 1L, startDate, endDate);

        doThrow(new RuntimeException("Test Exception")).when(pm).makePersistent(booking);

        Response response = bookingService.saveBooking(booking);

        verify(tx).begin();

        verify(pm).close();

        assert response.getStatus() == 500;
    }
}