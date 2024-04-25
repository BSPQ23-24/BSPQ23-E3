package es.deusto.spq.server;

import es.deusto.spq.server.jdo.Residence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import javax.ws.rs.GET;
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

    public ResidenceService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
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
}
