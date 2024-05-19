package es.deusto.spq.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Booking;
import es.deusto.spq.server.jdo.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Client class for interacting with the Lodgify server.
 */
public class LodgifyClient {

	protected static Logger logger = LogManager.getLogger();

	private static final String USER = "BSPQ23E3";
	private static final String PASSWORD = "BSPQ23E3";

	private Client client;
	private WebTarget webTarget;

	/**
     * Constructor for the LodgifyClient.
     * @param hostname The hostname of the server.
     * @param port The port of the server.
     */
	public LodgifyClient(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest", hostname, port));
		Response response = webTarget.request(MediaType.TEXT_PLAIN).get();
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("Conexión exitosa: " + response.readEntity(String.class));
		} else {
			System.out.println("Fallo en la conexión, código de estado: " + response.getStatus() + webTarget);
		}
	}

	/**
     * Registers a user on the server.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param name The name of the user.
     * @param surname The surname of the user.
     * @param phone_number The phone number of the user.
     * @param email The email of the user.
     * @param user_type The type of user.
     * @param id_card The ID card of the user.
     * @param bank_account The bank account of the user.
     * @param social_SN The social security number of the user.
     * @param address The address of the user.
     */
	public void registerUser(String username, String password, String name, String surname, String phone_number,
			String email, String user_type, String id_card, int bank_account, int social_SN, String address) {
		WebTarget registerUserWebTarget = webTarget.path("user/register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);

		User user = new User(username, password, name, surname, phone_number, email, user_type, id_card, bank_account,
				social_SN, address);
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered, the user name is {}", username);
		}
	}

	/**
     * Saves a booking on the server.
     * @param travelerUsername The username of the traveler.
     * @param hostUsername The username of the host.
     * @param residenceId The ID of the residence.
     * @param startDate The start date of the booking.
     * @param endDate The end date of the booking.
     */
	public void saveBooking(String travelerUsername, String hostUsername, Long residenceId, Date startDate,
			Date endDate) {
		WebTarget saveBookingWebTarget = webTarget.path("booking/save");
		Invocation.Builder invocationBuilder = saveBookingWebTarget.request(MediaType.APPLICATION_JSON);

		Booking booking = new Booking(travelerUsername, hostUsername, residenceId, startDate, endDate);
		Response response = invocationBuilder.post(Entity.entity(booking, MediaType.APPLICATION_JSON));

		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error saving booking. Code: {}", response.getStatus());
		} else {
			logger.info("Booking saved successfully");
		}
	}

	/*
	 * public void sayMessage(String login, String password, String message) {
	 * WebTarget sayHelloWebTarget = webTarget.path("sayMessage");
	 * Invocation.Builder invocationBuilder =
	 * sayHelloWebTarget.request(MediaType.APPLICATION_JSON);
	 * 
	 * DirectMessage directMessage = new DirectMessage();
	 * UserData userData = new UserData();
	 * userData.setLogin(login);
	 * userData.setPassword(password);
	 * 
	 * directMessage.setUserData(userData);
	 * 
	 * MessageData messageData = new MessageData();
	 * messageData.setMessage(message);
	 * directMessage.setMessageData(messageData);
	 * 
	 * Response response = invocationBuilder.post(Entity.entity(directMessage,
	 * MediaType.APPLICATION_JSON));
	 * if (response.getStatus() != Status.OK.getStatusCode()) {
	 * logger.error("Error connecting with the server. Code: {}",response.getStatus(
	 * ));
	 * } else {
	 * String responseMessage = response.readEntity(String.class);
	 * logger.info("* Message coming from the server: '{}'", responseMessage);
	 * }
	 * }
	 */

	public void setClient(Client client) {
		this.client = client;
	}

	public void setWebTarget(WebTarget webTarget) {
		this.webTarget = webTarget;
	}

	/**
     * Main method to run the LodgifyClient.
     * @param args Command-line arguments.
     */
	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
    	Date endDate = null;
    	try {
        	startDate = sdf.parse("2024-01-01");
        	endDate = sdf.parse("2024-01-07");
    	} catch (ParseException e) {
        	logger.error("Error parsing dates", e);
        	System.exit(1);
    	}

		LodgifyClient exampleClient = new LodgifyClient(hostname, port);
		exampleClient.registerUser(USER, PASSWORD, "user", "user", "999999999", "user@mail.es", "User", "", 0, 0, "");
		exampleClient.saveBooking("New", "test", 1L, startDate, endDate);
	}
}