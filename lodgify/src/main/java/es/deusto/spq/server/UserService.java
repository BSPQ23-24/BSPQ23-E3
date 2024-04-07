package es.deusto.spq.server;

import es.deusto.spq.server.jdo.User;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	protected static final Logger logger = LogManager.getLogger();

	private PersistenceManager pm=null;
	private Transaction tx=null;

	public UserService() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHello() {
		return Response.ok("Hello world!").build();
	}

    @POST
    @Path("/register")
    public Response registerUser(User user) {
        try
        {	
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", user.getUsername());
    		User user1 = null;
    		try {
    			user1 = pm.getObjectById(User.class, user.getUsername());
    		} catch (javax.jdo.JDOObjectNotFoundException jonfe) {
    			logger.info("Exception launched: {}", jonfe.getMessage());
    		}
    		logger.info("User: {}", user);
    		if (user1 != null) {
    			logger.info("Setting password user: {}", user);
    			user1.setPassword(user.getPassword());
    			logger.info("Password set user: {}", user);
    		} else {
    			logger.info("Creating user: {}", user);
    			user1 = new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getPhone_number(), user.getEmail());
    			pm.makePersistent(user);					 
    			logger.info("User created: {}", user);
    		}
    		tx.commit();
    		return Response.ok().build();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
    	}
    }    
}
