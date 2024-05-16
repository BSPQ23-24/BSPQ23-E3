package es.deusto.spq.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Booking;

/**
 * @class BookingService
 * @brief Provides RESTful web services for managing bookings.
 */
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
public class BookingService {

    /** The logger instance for this class. */
    protected static final Logger logger = LogManager.getLogger();

    /** The PersistenceManager instance for interacting with the database. */
    private PersistenceManager pm = null;

    /** The Transaction instance for database transactions. */
    private Transaction tx = null;

    /**
     * Constructor for BookingService class.
     * Initializes the PersistenceManager and Transaction objects.
     */
    public BookingService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    /**
     * Saves a new booking in the database.
     * @param booking The booking to be saved.
     * @return A Response indicating the success or failure of the save operation.
     */
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveBooking(Booking booking) {
        try {
            tx.begin();
            pm.makePersistent(booking);
            tx.commit();
            logger.info("Booking saved successfully");
            return Response.ok().build();
        } catch (Exception e) {
            logger.error("Error saving booking: {}", e.getMessage());
            if (tx.isActive()) {
                tx.rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while saving the booking.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Searches for bookings based on the specified user username.
     * @param user_username The username of the user whose bookings are to be searched.
     * @return A Response containing a list of bookings that match the username or an error message if none are found.
     */
    @GET
    @Path("/searchByUserUsername")
    public Response searchByUserID(@QueryParam("user_username") String user_username) {
        if (user_username == null || user_username.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User ID query parameter is required").build();
        }
        Query<Booking> query = pm.newQuery(Booking.class);
        query.setFilter("travelerUsername == :user_username");
        try {
            logger.info("Searching for bookings with user username: {}", user_username);
            @SuppressWarnings("unchecked")
            List<Booking> Bookings = (List<Booking>) query.execute(user_username);
            if (Bookings.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No Bookings found for the specified user ID.").build();
            }
            logger.info("Found bookings: {}", Bookings);
            return Response.ok(Bookings).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Deletes a booking based on the specified booking ID.
     * @param booking_id The ID of the booking to be deleted.
     * @return A Response indicating the success or failure of the deletion process.
     */
    @POST
    @Path("/deleteBooking")
    public Response deleteBookingByID(@QueryParam("booking_id") Long booking_id) {
        if (booking_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("booking ID query parameter is required")
                    .build();
        }
        Query<Booking> query = pm.newQuery(Booking.class);
        query.setFilter("id == :booking_id");
        try {
            logger.info("Deleting booking with id: {}", booking_id);
            @SuppressWarnings("unchecked")
            List<Booking> bookings = (List<Booking>) query.execute(booking_id);
            if (bookings.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No bookings found for the specified booking ID.").build();
            } else if (bookings.size() > 1) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Multiple bookings found for the specified booking ID.").build();
            }
            logger.info("Found bookings to delete: {}", bookings);
            Booking bookingToDelete = bookings.get(0);
            pm.deletePersistent(bookingToDelete);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Sets the PersistenceManagerFactory for this service.
     * @param pmf The PersistenceManagerFactory to be set.
     */
    public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

}