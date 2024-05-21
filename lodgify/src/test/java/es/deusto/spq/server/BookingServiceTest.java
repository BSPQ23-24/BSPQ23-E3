package es.deusto.spq.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
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

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckAvailability_Success() {
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

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(anyLong(), any(Date.class), any(Date.class))).thenReturn(Collections.emptyList());

        Response response = bookingService.checkAvailability(booking);

        verify(tx).begin();
        verify(query).execute(1L, endDate, startDate);
        verify(tx).commit();
        verify(pm).close();

        assert response.getStatus() == 200;
        assert response.getEntity().equals("Available");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckAvailability_Conflict() {
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

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(anyLong(), any(Date.class), any(Date.class))).thenReturn(Arrays.asList(booking));

        Response response = bookingService.checkAvailability(booking);

        verify(tx).begin();
        verify(query).execute(1L, endDate, startDate);
        verify(tx).commit();
        verify(pm).close();

        assert response.getStatus() == 409;
        assert response.getEntity().equals("Residence not available on those dates!");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteBookingByID_Success() {
        Long bookingId = 1L;
        Booking booking = new Booking(null, null, bookingId, null, null);

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(bookingId)).thenReturn(Arrays.asList(booking));

        Response response = bookingService.deleteBookingByID(bookingId);

        verify(query).execute(bookingId);
        verify(pm).deletePersistent(booking);
        verify(pm).close();

        assert response.getStatus() == 200;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeleteBookingByID_NotFound() {
        Long bookingId = 1L;

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(bookingId)).thenReturn(Collections.emptyList());

        Response response = bookingService.deleteBookingByID(bookingId);

        verify(query).execute(bookingId);
        verify(pm).close();

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No bookings found for the specified booking ID.");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchByResidenceID_Success() {
        Long residenceId = 1L;
        Booking booking = new Booking(null, null, residenceId, null, null);

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(Arrays.asList(booking));

        Response response = bookingService.searchByResidenceID(residenceId.toString());

        verify(query).execute(residenceId);
        verify(pm).close();

        assert response.getStatus() == 200;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchByResidenceID_NotFound() {
        Long residenceId = 1L;

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(residenceId)).thenReturn(Collections.emptyList());

        Response response = bookingService.searchByResidenceID(residenceId.toString());

        verify(query).execute(residenceId);
        verify(pm).close();

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No bookings found for the specified residence ID.");
    }

    @Test
    public void testSearchByUserID_Success() {
        String userUsername = "travelerUsername";
        Booking booking = new Booking(userUsername, userUsername, null, null, null);

        @SuppressWarnings("unchecked")
        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(userUsername)).thenReturn(Arrays.asList(booking));

        Response response = bookingService.searchByUserID(userUsername);

        verify(query).execute(userUsername);
        verify(pm).close();

        assert response.getStatus() == 200;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSearchByUserID_NotFound() {
        String userUsername = "travelerUsername";

        Query<Booking> query = mock(Query.class);
        when(pm.newQuery(Booking.class)).thenReturn(query);
        when(query.execute(userUsername)).thenReturn(Collections.emptyList());

        Response response = bookingService.searchByUserID(userUsername);

        verify(query).execute(userUsername);
        verify(pm).close();

        assert response.getStatus() == 404;
        assert response.getEntity().equals("No Bookings found for the specified user ID.");
    }
}