package es.deusto.spq.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import es.deusto.spq.server.jdo.User;
import es.deusto.spq.server.jdo.Booking;

public class LodgifyClient {

	protected static final Logger logger = LogManager.getLogger();

	private static final String USER = "BSPQ23E3";
	private static final String PASSWORD = "BSPQ23E3";

	private Client client;
	private WebTarget webTarget;

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

	public void registerUser(String username, String password, String name, String surname, String phone_number, String email, String user_type, String id_card, int bank_account, int social_SN, String address) {
		WebTarget registerUserWebTarget = webTarget.path("user/register");
		Invocation.Builder invocationBuilder = registerUserWebTarget.request(MediaType.APPLICATION_JSON);
		
		User user = new User(username, password, name, surname, phone_number, email, user_type, id_card, bank_account, social_SN, address);
		Response response = invocationBuilder.post(Entity.entity(user, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("User correctly registered, the user name is {}", username);
		}
	}

	public void saveBooking(Long travelerId, Long hostId, Long residenceId, String startDate, String endDate) {
		WebTarget saveBookingWebTarget = webTarget.path("booking/save");
		Invocation.Builder invocationBuilder = saveBookingWebTarget.request(MediaType.APPLICATION_JSON);
	
		Booking booking = new Booking(travelerId, hostId, residenceId, startDate, endDate);
		Response response = invocationBuilder.post(Entity.entity(booking, MediaType.APPLICATION_JSON));
	
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error saving booking. Code: {}", response.getStatus());
		} else {
			logger.info("Booking saved successfully");
		}
	}

	/*
	public void sayMessage(String login, String password, String message) {
		WebTarget sayHelloWebTarget = webTarget.path("sayMessage");
		Invocation.Builder invocationBuilder = sayHelloWebTarget.request(MediaType.APPLICATION_JSON);

		DirectMessage directMessage = new DirectMessage();
		UserData userData = new UserData();
		userData.setLogin(login);
		userData.setPassword(password);

		directMessage.setUserData(userData);

		MessageData messageData = new MessageData();
		messageData.setMessage(message);
		directMessage.setMessageData(messageData);

		Response response = invocationBuilder.post(Entity.entity(directMessage, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}",response.getStatus());
		} else {
			String responseMessage = response.readEntity(String.class);
			logger.info("* Message coming from the server: '{}'", responseMessage);
		}
	}
	*/

	public static void main(String[] args) {
		if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		LodgifyClient exampleClient = new LodgifyClient(hostname, port);
		exampleClient.registerUser(USER, PASSWORD, "user", "user", "999999999", "user@mail.es", "User", "", 0, 0, "");
		exampleClient.saveBooking(1L, 1L, 1L, "hostname", "port");
	}
}