package es.deusto.spq.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
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
import es.deusto.spq.server.jdo.Residence;

/**
 * @class ResidenceService
 * @brief Provides RESTful web services for managing residences.
 */
@Path("/residence")
@Produces(MediaType.APPLICATION_JSON)
public class ResidenceService {

    /** The logger instance for this class. */
    protected static final Logger logger = LogManager.getLogger();

    /** The PersistenceManager instance for interacting with the database. */
    private PersistenceManager pm = null;

    /** The Transaction instance for database transactions. */
    private Transaction tx = null;

    /**
     * Constructor for ResidenceService class.
     * Initializes the PersistenceManager and Transaction objects.
     */
    public ResidenceService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    /**
     * Registers a new residence in the database.
     * 
     * @param residence The residence to be registered.
     * @return A Response indicating the success or failure of the registration
     *         process.
     */
    @POST
    @Path("/register")
    public Response registerUser(Residence residence) {
        try {
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", residence.getId());
            Residence residence1 = null;
            Query<Residence> query = pm.newQuery(Residence.class, "id == :id");
            try {
                @SuppressWarnings("unchecked")
                List<Residence> residences = (List<Residence>) query.execute(residence.getId());
                if (residences.isEmpty()) {
                    logger.info("Residence not found!");
                } else {
                    residence1 = residences.get(0);
                }
            } finally {
                query.closeAll();
            }

            logger.info("Residence: {}", residence);
            if (residence1 != null) {
                logger.info("Residence already exists!");
                return Response.status(400).entity("Residence already exists!").build();
            }
            if (residence1 == null) {
                logger.info("Creating residence: {}", residence);
                if (residence.getResidence_address().equals("") || residence.getResidence_type().equals("")
                        || residence.getPrice() == 0 || residence.getN_rooms() == 0) {
                    logger.info("Not all the data filled!");
                    return Response.status(400).entity("Fill all the data!").build();
                }

                residence1 = new Residence(residence.getResidence_address(), residence.getResidence_type(),
                        residence.getN_rooms(), residence.getPrice(), "../../../../../../assets/apartamento.jpg",
                        residence.getUser_username());
                logger.info("Username: {}", residence.getUser_username());
                pm.makePersistent(residence1);
                logger.info("Residence: {}", residence1);
            }
            tx.commit();
            return Response.ok().build();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    /**
     * Searches for residences based on the specified address.
     * 
     * @param address The address to search for residences.
     * @return A Response containing a list of residences that match the address or
     *         an error message if none are found.
     */
    @GET
    @Path("/search")
    public Response searchResidences(@QueryParam("address") String address) {

        if (address == null || address.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Address query parameter is required").build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        // address = address.trim();
        query.setFilter("residence_address == :address");
        try {
            logger.info("Searching for residences with address: {}", address);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(address);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found at the specified address.").build();
            }
            logger.info("Found residences: {}", residences);
            return Response.ok(residences).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Deletes a residence based on the specified residence ID.
     * 
     * @param residenceId The ID of the residence to be deleted.
     * @return A Response indicating the success or failure of the deletion process.
     */
    @POST
    @Path("/delete")
    public Response deleteResidence(@QueryParam("residence_id") Long residenceId) {
        if (residenceId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Residence ID query parameter is required")
                    .build();
        }

        try {
            tx.begin();
            Query<Residence> query = pm.newQuery(Residence.class);
            query.setFilter("id == :residence_id");

            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(residenceId);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Residence not found.").build();
            }

            Residence residenceToDelete = residences.get(0);
            pm.deletePersistent(residenceToDelete);
            tx.commit();
            Response response = new BookingService().searchByResidenceID(residenceId.toString());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                @SuppressWarnings("unchecked")
                List<Booking> bookingsToDelete = (List<Booking>) response.getEntity();
                logger.info("Bookings associated with this residence (to delete): {}", bookingsToDelete);
                for (Booking booking : bookingsToDelete) {
                    new BookingService().deleteBookingByID(booking.getId());
                }
            } else {
                logger.warn("No bookings found or an error occurred when searching for bookings with residence ID: {}",
                        residenceId);
            }
            return Response.ok().entity("Residence deleted successfully.").build();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting residence.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Searches for residences based on the specified user ID.
     * 
     * @param user_id The ID of the user whose residences are to be searched.
     * @return A Response containing a list of residences that match the user ID or
     *         an error message if none are found.
     */
    @GET
    @Path("/searchByUserID")
    public Response searchResidencesID(@QueryParam("user_id") String user_id) {
        if (user_id == null || user_id.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User ID query parameter is required").build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("user_username == :user_id");
        try {
            logger.info("Searching for residences whose host is with user_id: {}", user_id);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(user_id);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found for the specified user ID.").build();
            }
            logger.info("Found residences: {}", residences);
            return Response.ok(residences).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    /**
     * Retrieves a residence based on the specified residence ID.
     * 
     * @param residence_id The ID of the residence to be retrieved.
     * @return A Response containing the residence details or an error message if
     *         none is found.
     */
    @GET
    @Path("/searchByResidenceID")
    public Response getResidenceByID(@QueryParam("residence_id") Long residence_id) {
        if (residence_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Residence ID query parameter is required")
                    .build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("id == :residence_id");
        try {
            logger.info("Searching for residence with id: {}", residence_id);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(residence_id);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found for the specified residence ID.").build();
            } else if (residences.size() > 1) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Multiple residences found for the specified residence ID.").build();
            }
            logger.info("Found residences: {}", residences);
            return Response.ok(residences.get(0)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }
}
