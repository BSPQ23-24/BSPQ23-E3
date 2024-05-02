package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Residence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.jdo.Query;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Path("/residence")
@Produces(MediaType.APPLICATION_JSON)
public class ResidenceService {

    protected static final Logger logger = LogManager.getLogger();

    private PersistenceManager pm = null;
    private Transaction tx = null;

    public ResidenceService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

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

    @GET
    @Path("/search")
    public Response searchResidences(@QueryParam("address") String address) {

        if (address == null || address.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Address query parameter is required").build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("residence_address == :address");
        try {
            logger.info(address);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(address);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found at the specified address.").build();
            }
            logger.info(residences);
            return Response.ok(residences).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    @GET
    @Path("/searchByUserID")
    public Response searchResidencesByUserID(@QueryParam("user_id") String user_id) {
        if (user_id == null || user_id.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User ID query parameter is required").build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("user_username == :user_id");
        try {
            logger.info(user_id);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(user_id);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found for the specified user ID.").build();
            }
            logger.info(residences);
            return Response.ok(residences).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    @GET
    @Path("/searchByResidenceID")
    public Response getResidenceByResidenceID(@QueryParam("residence_id") Long residence_id) {
        if (residence_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Residence ID query parameter is required")
                    .build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("id == :residence_id");
        try {
            logger.info(residence_id);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(residence_id);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found for the specified residence ID.").build();
            } else if (residences.size() > 1) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Multiple residences found for the specified residence ID.").build();
            }
            logger.info(residences);
            return Response.ok(residences.get(0)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }

    @POST
    @Path("/deleteResidence")
    public Response deleteResidenceByID(@QueryParam("residence_id") Long residence_id) {
        if (residence_id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Residence ID query parameter is required")
                    .build();
        }
        Query<Residence> query = pm.newQuery(Residence.class);
        query.setFilter("id == :residence_id");
        try {
            logger.info(residence_id);
            @SuppressWarnings("unchecked")
            List<Residence> residences = (List<Residence>) query.execute(residence_id);
            if (residences.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No residences found for the specified residence ID.").build();
            } else if (residences.size() > 1) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Multiple residences found for the specified residence ID.").build();
            }
            logger.info(residences);
            Residence residenceToDelete = residences.get(0);
            pm.deletePersistent(residenceToDelete);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching the data.").build();
        } finally {
            pm.close();
        }
    }
}
