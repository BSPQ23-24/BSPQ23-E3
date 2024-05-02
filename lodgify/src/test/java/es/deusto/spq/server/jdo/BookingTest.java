package es.deusto.spq.server.jdo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookingTest {

    @Test
    public void testGettersAndSetters() {
        // Set up mock data
        String travelerUsername = "JohnDoe";
        String hostUsername = "JaneSmith";
        Long residenceId = 12345L;
        String startDate = "2024-05-01";
        String endDate = "2024-05-10";

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
        String newStartDate = "2024-06-01";
        String newEndDate = "2024-06-10";

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
        // Create a Booking instance
        Booking booking = new Booking("JohnDoe", "JaneSmith", 12345L, "2024-05-01", "2024-05-10");

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
        String startDate = "2024-05-01";
        String endDate = "2024-05-10";
        Long id = 98765L;

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