/**
 * @file UserService.java
 * @brief Contains the implementation of the UserService class.
 */

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.helper.Hashing;
import es.deusto.spq.server.jdo.User;


/**
 * @class UserService
 * @brief Provides RESTful web services for user management.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    /** The logger instance for this class. */
    protected static final Logger logger = LogManager.getLogger();

    /** The PersistenceManager instance for interacting with the database. */
    private PersistenceManager pm = null;
    
    /** The Transaction instance for database transactions. */
    private Transaction tx = null;


    /**
     * Constructor for UserService class.
     * Initializes the PersistenceManager and Transaction objects.
     */
    public UserService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }


    /**
     * Retrieves a simple greeting message.
     * @return A Response containing the greeting message.
     */
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        return Response.ok("Hello world!").build();
    }


    /**
     * Registers a new user.
     * @param user The user to register.
     * @return A Response indicating the success or failure of the registration process.
     */
    @POST
    @Path("/register")
    public Response registerUser(User user) {
        try {
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", user.getUsername());
            user.setPassword(Hashing.Hash(user.getPassword()));
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
                logger.info("User already exists!");
                return Response.status(400).entity("User already exists!").build();
            }
            if (user1 == null) {
                logger.info("Creating user: {}", user);
                logger.info("Creating username: {}", user.getUsername());
                logger.info("Creating name: {}", user.getName());
                if (user.getName().equals("") || user.getSurname().equals("") || user.getUsername().equals("")
                        || user.getPassword().equals("")) {
                    logger.info("Not all the data filled!");
                    return Response.status(400).entity("Fill all the data!").build();
                } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    logger.info("Email not valid!");
                    return Response.status(400).entity("Not valid email!").build();
                } else if (user.getPhone_number().length() != 9) {
                    logger.info("Phone number must have 9 digits!");
                    return Response.status(400).entity("Not valid phone number!").build();
                }

                user1 = new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(),
                        user.getPhone_number(), user.getEmail(), user.getUser_type(), user.getId_card(),
                        user.getBank_account(), user.getSocial_SN(), user.getAddress());
                pm.makePersistent(user);
                logger.info("User created: {}", user);
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
     * Logs in an existing user.
     * @param user The user to log in.
     * @return A Response containing user information if login is successful, or an error response otherwise.
     */
    @POST
    @Path("/login")
    public Response loginUser(User user) {
        try {
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", user.getUsername());
            user.setPassword(Hashing.Hash(user.getPassword()));
            User user1 = null;
            Query<User> query = pm.newQuery(User.class, "username == :username");
            try {
                @SuppressWarnings("unchecked")
                List<User> users = (List<User>) query.execute(user.getUsername());
                if (users.isEmpty()) {
                    logger.info("User not found!");
                } else {
                    user1 = users.get(0);
                    logger.info("Aquí es {}", user1);
                }
            } finally {
                query.closeAll();
            }
            logger.info("User: {}", user);
            if (user1 != null) {
                logger.info("Tipo: {}", user1.getUser_type());
                logger.info("User found: {}", user1.toString());
                logger.info("Submitted password: {}", user.getPassword());
                logger.info("DB password: {}", user1.getPassword());
                if (user.getPassword().equals(user1.getPassword())
                        && user.getUser_type().equals(user1.getUser_type())) {
                    logger.info("El usuario es: {}", user1);
                    User userDef = new User(user1.getUsername(), user1.getPassword(), user1.getName(),
                            user1.getSurname(), user1.getPhone_number(), user1.getEmail(), user1.getUser_type(),
                            user1.getId_card(), user1.getBank_account(), user1.getSocial_SN(), user1.getAddress());
                    return Response.ok(userDef).build();
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


    /**
     * Modifies an existing user's information.
     * @param user The user whose information needs to be modified.
     * @return A Response indicating the success or failure of the modification process.
     */
    @POST
    @Path("/modify")
    public Response modifyUser(User user) {
        try {
            tx.begin();
            logger.info("Checking whether the user already exits or not: '{}'", user.getUsername());
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

            logger.info("User: {}", user1);
            if (user1 != null) {
                user1.setName(user.getName());
                user1.setSurname(user.getSurname());
                user1.setEmail(user.getEmail());
                user1.setPhone_number(user.getPhone_number());
                user1.setId_card(user.getId_card());
                user1.setBank_account(user.getBank_account());
                user1.setSocial_SN(user.getSocial_SN());
                user1.setAddress(user.getAddress());
            }
            pm.makePersistent(user1);
            logger.info("User created: {}", user);
            tx.commit();
            return Response.ok().build();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}
