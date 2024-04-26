/*package es.deusto.spq.server;

import es.deusto.spq.server.helper.Hashing;
import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.jdo.Host;
import es.deusto.spq.server.jdo.Residence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.jdo.Query;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/host")
@Produces(MediaType.APPLICATION_JSON)
public class HostService {
    protected static final Logger logger = LogManager.getLogger();

    private PersistenceManager pm = null;
    private Transaction tx = null;

    public HostService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    @POST
    @Path("/register")
    public Response registerHost(Host host) {
        try {
            tx.begin();
            logger.info("Got host:", host.toString(), host.getUsername());
            logger.info("Checking whether the host already exits or not: '{}'", host.getUsername());
            // host.setPassword(Hashing.Hash(host.getPassword()));
            Host host1 = null;
            Residence residence = null;
            Query<Host> query = pm.newQuery(Host.class, "username == :username");
            try {
                @SuppressWarnings("unchecked")
                List<Host> hosts = (List<Host>) query.execute(host.getUsername());
                if (hosts.isEmpty()) {
                    logger.info("host not found!");
                } else {
                    host1 = hosts.get(0);
                }
            } finally {
                query.closeAll();
            }

            logger.info("host: {}", host);
            if (host1 != null) {
                logger.info("host already exists!");
                return Response.status(400).entity("host already exists!").build();
            }
            if (host1 == null) {
                logger.info("Creating host: {}", host);
                logger.info("Creating hostname: {}", host.getUsername());
                logger.info("Creating name: {}", host.getName());
                if (host.getName().equals("") || host.getSurname().equals("") || host.getUsername().equals("")
                        || host.getPassword().equals("")) {
                    logger.info("Not all the data filled!");
                    return Response.status(400).entity("Fill all the data!").build();
                } else if (!host.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    logger.info("Email not valid!");
                    return Response.status(400).entity("Not valid email!").build();
                } else if (host.getPhone_number().length() != 9) {
                    logger.info("Phone number must have 9 digits!");
                    return Response.status(400).entity("Not valid phone number!").build();
                }

                host1 = new Host(host.getUsername(), host.getPassword(), host.getName(), host.getSurname(),
                        host.getPhone_number(), host.getEmail(), host.getBank_account(),
                        host.getSocial_SN(), host.getAddress());
                residence = new Residence("Bilbao", "Apartment", 2, 120, "../../../../../../assets/apartamento.jpg", 1);
                pm.makePersistent(host);
                pm.makePersistent(residence);
                logger.info("Residence: {}", residence);
                logger.info("host created: {}", host);
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

    @POST
    @Path("/login")
    public Response loginHost(Host host) {
        try {
            tx.begin();
            logger.info("Checking whether the host already exits or not: '{}'", host.getUsername());
            // host.setPassword(Hashing.Hash(host.getPassword()));
            Host host1 = null;
            Query<Host> query = pm.newQuery(Host.class, "username == :username");
            try {
                @SuppressWarnings("unchecked")
                List<Host> hosts = (List<Host>) query.execute(host.getUsername());
                if (hosts.isEmpty()) {
                    logger.info("host not found!");
                } else {
                    host1 = hosts.get(0);
                    logger.info("Aquí es {}", host1);
                }
            } finally {
                query.closeAll();
            }
            logger.info("host: {}", host);
            if (host1 != null) {
                logger.info("host found: {}", host1.toString());
                logger.info("Submitted password: {}", host.getPassword());
                logger.info("DB password: {}", host1.getPassword());
                if (host.getPassword().equals(host1.getPassword())) {
                    logger.info("El usuario es: {}", host1);
                    Host hostDef = new Host(host.getUsername(), host.getPassword(), host.getName(), host.getSurname(),
                            host.getPhone_number(), host.getEmail(), host.getBank_account(),
                            host.getSocial_SN(), host.getAddress());
                    return Response.ok(hostDef).build();
                } else {
                    return Response.status(401).build();
                }
            } else {
                return Response.status(401).build();
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}
*/
