package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Booking;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
public class BookingService {

    protected static final Logger logger = LogManager.getLogger();

    private PersistenceManager pm = null;
    private Transaction tx = null;

    public BookingService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    @POST
    @Path("/save")
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
}
