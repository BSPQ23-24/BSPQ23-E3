package es.deusto.spq.server;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfTest;

import es.deusto.spq.server.jdo.User;

public class UserServicePerformanceTest {

    private WebTarget target;

    @Before
    public void setUp() {
        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/rest/");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testRegisterUserPerf() {
        User user = new User("newUser", "password", "Jane", "Doe", "987654321", "jane@example.com", "user", "987654321", 987654321, 1234, "456 Test St");

        Response response = target.path("register")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // Check for successful response
        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());

        // Capture average and max response times
        long responseTime = response.readEntity(Long.class);
        System.out.println("Average Response Time: " + responseTime + " ms");
        System.out.println("Max Response Time: " + responseTime + " ms");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testLoginUserPerf() {
        User user = new User("testUser", "password", "a", "a", "111111111", "iser@gmail.com", "user", "1", 0, 0, "as");

        Response response = target.path("login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));

        // Check for successful response
        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());

        // Intentionally fail the test to simulate a failed execution
        assertEquals(Family.SUCCESSFUL, Family.familyOf(401));
    }
}
