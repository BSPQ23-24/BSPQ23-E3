package es.deusto.spq.server.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @class Booking
 * @brief Represents a booking entity in the system.
 */
@PersistenceCapable
public class Booking {

    /** The unique identifier for the booking. */
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    /** The username of the traveler who made the booking. */
    private String travelerUsername;

    /** The username of the host who owns the residence. */
    private String hostUsername;

    /** The ID of the residence that is booked. */
    private Long residenceId;

    /** The start date of the booking. */
    private String startDate;

    /** The end date of the booking. */
    private String endDate;

    /**
     * Constructs a new Booking with the specified details.
     * @param travelerUsername The username of the traveler.
     * @param hostUsername The username of the host.
     * @param residenceId The ID of the residence.
     * @param startDate The start date of the booking.
     * @param endDate The end date of the booking.
     */
    public Booking(String travelerUsername, String hostUsername, Long residenceId, String startDate, String endDate) {
        this.travelerUsername = travelerUsername;
        this.hostUsername = hostUsername;
        this.residenceId = residenceId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the unique identifier for the booking.
     * @return The booking ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the booking.
     * @param id The booking ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the traveler.
     * @return The traveler's username.
     */
    public String gettravelerUsername() {
        return travelerUsername;
    }

    /**
     * Sets the username of the traveler.
     * @param travelerUsername The traveler's username.
     */
    public void settravelerUsername(String travelerUsername) {
        this.travelerUsername = travelerUsername;
    }

    /**
     * Gets the username of the host.
     * @return The host's username.
     */
    public String gethostUsername() {
        return hostUsername;
    }

    /**
     * Sets the username of the host.
     * @param hostUsername The host's username.
     */
    public void sethostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    /**
     * Gets the ID of the booked residence.
     * @return The residence ID.
     */
    public Long getResidenceId() {
        return residenceId;
    }

    /**
     * Sets the ID of the booked residence.
     * @param residenceId The residence ID.
     */
    public void setResidenceId(Long residenceId) {
        this.residenceId = residenceId;
    }

    /**
     * Gets the start date of the booking.
     * @return The start date.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the booking.
     * @param startDate The start date.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the booking.
     * @return The end date.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the booking.
     * @param endDate The end date.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns a string representation of the booking.
     * @return A string containing the booking details.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", travelerUsername=" + travelerUsername +
                ", hostUsername=" + hostUsername +
                ", residenceId=" + residenceId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

}
