package es.deusto.spq.server;

import es.deusto.spq.server.helper.Hashing;
import es.deusto.spq.server.jdo.User;
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

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	protected static final Logger logger = LogManager.getLogger();

	private PersistenceManager pm = null;
	private Transaction tx = null;

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
      user.setPassword(Hashing.Hash(user.getPassword()));
			user.setUsername(Hashing.Hash(user.getUsername()));
    	User user1 = null;
			Residence residence = null;
			Query<User> query = pm.newQuery(User.class, "username == :username");
			try {
				@SuppressWarnings("unchecked")
				List<User> users = (List<User>) query.execute(user.getUsername());
				if (users.isEmpty()) {
					logger.info("User not found!");
				} else {
					user1 = users.get(0);
				}
			} finally {
				query.closeAll();
			}

    		logger.info("User: {}", user);
			if(user1 != null) {
				logger.info("User already exists!");
				return Response.status(400).entity("User already exists!").build();
			}
    		if (user1 == null) {
    			logger.info("Creating user: {}", user);
				logger.info("Creating username: {}", user.getUsername());
				logger.info("Creating name: {}", user.getName());
				if(user.getName().equals("") || user.getSurname().equals("") || user.getUsername().equals("") || user.getPassword().equals("")){
					logger.info("Not all the data filled!");
					return Response.status(400).entity("Fill all the data!").build();
				}
				else if(!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
					logger.info("Email not valid!");
					return Response.status(400).entity("Not valid email!").build();
				}
				else if(user.getPhone_number().length() != 9){
					logger.info("Phone number must have 9 digits!");
					return Response.status(400).entity("Not valid phone number!").build();
				}	
				
				user1 = new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getPhone_number(), user.getEmail(), user.getUser_type(), user.getId_card(), user.getBank_account(), user.getSocial_SN(), user.getAddress());
				residence = new Residence("Bilbao", "Apartment", 2, 120, "../../../../../../assets/apartamento.jpg", 1);
    			pm.makePersistent(user);
				pm.makePersistent(residence);
				logger.info("Residence: {}", residence);					 
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
			pm.close();
    	}
    }    


	@POST
    @Path("/login")
    public Response loginUser(User user) {
        try
        {	
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", user.getUsername());
    	      user.setPassword(Hashing.Hash(user.getPassword()));
			      user.setUsername(Hashing.Hash(user.getSurname()));
      User user1 = null;
			Query<User> query = pm.newQuery(User.class, "username == :username");
			try {
				@SuppressWarnings("unchecked")
				List<User> users = (List<User>) query.execute(user.getUsername());
				if (users.isEmpty()) {
					logger.info("User not found!");
				} else {
					user1 = users.get(0);
				}
			} finally {
				query.closeAll();
			}
    		logger.info("User: {}", user);
    		if (user1 != null) {
    			logger.info("Submitted password: {}", user.getPassword());
    			logger.info("DB password: {}", user1.getPassword());
				if(user.getPassword().equals(user1.getPassword())){
					return Response.ok(user1).build();
				}
				else{
					return Response.status(401).build();
				}
    		} else {
    			return Response.status(401).build();
    		}
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
			pm.close();
    	}
    }    
}
