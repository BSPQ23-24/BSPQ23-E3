package es.deusto.spq.server.jdo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingTest {

    @Test
    public void testGettersAndSetters() {
        // Set up mock data
        String travelerUsername = "JohnDoe";
        String hostUsername = "JaneSmith";
        Long residenceId = 12345L;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	System.exit(1);
    	}

        // Create a real instance of Booking
        Booking booking = new Booking(travelerUsername, hostUsername, residenceId, startDate, endDate);

        // Test getter methods
        assertEquals(travelerUsername, booking.gettravelerUsername());
        assertEquals(hostUsername, booking.gethostUsername());
        assertEquals(residenceId, booking.getResidenceId());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());

        // Test setter methods
        String newTravelerUsername = "NewUser";
        String newHostUsername = "NewHost";
        Long newResidenceId = 54321L;

        Date newStartDate = null;
        Date newEndDate = null;

    	try {
        	newStartDate = sdf.parse("2024-01-02");
        	newEndDate = sdf.parse("2024-01-08");
    	} catch (ParseException e) {
        	System.exit(1);
    	}

        booking.settravelerUsername(newTravelerUsername);
        booking.sethostUsername(newHostUsername);
        booking.setResidenceId(newResidenceId);
        booking.setStartDate(newStartDate);
        booking.setEndDate(newEndDate);

        assertEquals(newTravelerUsername, booking.gettravelerUsername());
        assertEquals(newHostUsername, booking.gethostUsername());
        assertEquals(newResidenceId, booking.getResidenceId());
        assertEquals(newStartDate, booking.getStartDate());
        assertEquals(newEndDate, booking.getEndDate());
    }

    @Test
    public void testSetId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	System.exit(1);
    	}
        // Create a Booking instance
        Booking booking = new Booking("JohnDoe", "JaneSmith", 12345L, startDate, endDate);

        // Set new ID
        Long newId = 98765L;
        booking.setId(newId);

        // Check if the ID was set correctly
        assertEquals(newId, booking.getId());
    }

    @Test
    public void testToString() {
        // Set up mock data
        String travelerUsername = "JohnDoe";
        String hostUsername = "JaneSmith";
        Long residenceId = 12345L;
        Long id = 98765L;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	System.exit(1);
    	}

        // Create a Booking instance
        Booking booking = new Booking(travelerUsername, hostUsername, residenceId, startDate, endDate);
        booking.setId(id);

        // Define expected string representation
        String expectedString = "Booking{id=" + id +
                ", travelerUsername=" + travelerUsername +
                ", hostUsername=" + hostUsername +
                ", residenceId=" + residenceId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';

        // Check if the toString method returns the expected string
        assertEquals(expectedString, booking.toString());
    }
}